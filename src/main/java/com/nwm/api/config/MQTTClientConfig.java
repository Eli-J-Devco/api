package com.nwm.api.config;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

import com.nwm.api.services.building.SitesOverviewHVACService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwm.api.controllers.UploadJsonDataController;
import com.nwm.api.services.UploadJsonIngestService;

@Configuration
public class MQTTClientConfig {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MQTTClientConfig.class);
    @org.springframework.beans.factory.annotation.Autowired
    private UploadJsonIngestService uploadJsonIngestService;
	@Value("${mqtt.hvac.protocol}")
	private String protocol;
	@Value("${mqtt.hvac.url}")
	private String url;
	@Value("${mqtt.hvac.port}")
	private String port;
	@Value("${mqtt.hvac.username}")
	private String username;
	@Value("${mqtt.hvac.password}")
	private String password;
	@Value("${mqtt.hvac.timeout}")
	private int timeout;
	@Value("${mqtt.datalogger.protocol}")
	private String dataloggerProtocol;
	@Value("${mqtt.datalogger.url}")
	private String dataloggerUrl;
	@Value("${mqtt.datalogger.port}")
	private String dataloggerPort;
	@Value("${mqtt.datalogger.username}")
	private String dataloggerUsername;
	@Value("${mqtt.datalogger.password}")
	private String dataloggerPassword;
	@Value("${mqtt.datalogger.timeout}")
	private int dataloggerTimeout;
	@Value("${mqtt.datalogger.topic}")
	private String dataloggerTopic;
	@Value("${mqtt.datalogger.ssl.protocol}")
	private String dataloggerSslProtocol;
	@Value("${mqtt.datalogger.ssl.port}")
	private String dataloggerSslPort;
	@Bean
	MqttPahoClientFactory mqttClientFactory() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { protocol.concat("://").concat(url).concat(":").concat(port) });
		options.setUserName(username);
		options.setPassword(password.toCharArray());
		options.setCleanSession(false);
		options.setAutomaticReconnect(true);
		options.setConnectionTimeout(timeout);
		
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(options);
		
		return factory;
    }

	/**
	 * Datalogger MQTT client factory configured from application properties.
	 */
	@Bean
	MqttPahoClientFactory dataloggerMqttClientFactory() {
		MqttConnectOptions options = new MqttConnectOptions();
		String selectedProtocol = dataloggerProtocol != null && !dataloggerProtocol.isEmpty() ? dataloggerProtocol : "tcp";
		String selectedPort = selectedProtocol.equalsIgnoreCase("ssl") || selectedProtocol.equalsIgnoreCase("wss")
				? (dataloggerSslPort != null && !dataloggerSslPort.isEmpty() ? dataloggerSslPort : dataloggerPort)
				: dataloggerPort;
		options.setServerURIs(new String[] { selectedProtocol.concat("://").concat(dataloggerUrl).concat(":").concat(selectedPort) });
		options.setUserName(dataloggerUsername);
		options.setPassword(dataloggerPassword.toCharArray());
		options.setCleanSession(false);
		options.setAutomaticReconnect(true);
		options.setConnectionTimeout(dataloggerTimeout);

		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(options);

		return factory;
	}
	
    @Bean
	MessageChannel mqttInputChannel() {
    	return new DirectChannel();
	}
	
	@Bean
	MessageProducer inbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(MqttAsyncClient.generateClientId(), mqttClientFactory(), "t/+/NextWave123/telemetry");
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setOutputChannel(mqttInputChannel());
		
		return adapter;
    }

	// =====================
	// Datalogger inbound
	// =====================
	@Bean
	MessageChannel mqttDataloggerInputChannel() {
		return new DirectChannel();
	}

	@Bean
	MessageProducer dataloggerInbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
				MqttAsyncClient.generateClientId(), dataloggerMqttClientFactory(), dataloggerTopic);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setOutputChannel(mqttDataloggerInputChannel());
		return adapter;
	}
	
	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	MessageHandler handler() {
		return new MessageHandler() {
			SitesOverviewHVACService service = new SitesOverviewHVACService();

			@Override
			public void handleMessage(Message<?> message) {
				String[] topic = message.getHeaders().get("mqtt_receivedTopic").toString().split("/");
				if (topic[2].equals("NextWave123") && topic[3].equals("telemetry")) service.saveFieldData(message);
			}
		};
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttDataloggerInputChannel")
	MessageHandler dataloggerHandler() {
		return new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) {
				String topic = String.valueOf(message.getHeaders().get("mqtt_receivedTopic"));
				String payload = String.valueOf(message.getPayload());
				logger.info("[MQTT Datalogger] Received - topic: {} payload: {}", topic, payload);
                try {
					ObjectMapper mapper = new ObjectMapper();
					UploadJsonDataController.UploadJsonRequest req = mapper.readValue(payload, UploadJsonDataController.UploadJsonRequest.class);
                    String result = uploadJsonIngestService.ingest(req);
					logger.info("[MQTT Datalogger] Ingest result: {}", result != null ? result.trim() : "null");
				} catch (Exception ex) {
					logger.error("[MQTT Datalogger] Failed to parse/process payload: {}", ex.getMessage(), ex);
				}
			}
		};
	}
	
	@Bean
    MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
	
	@Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), mqttClientFactory());
        messageHandler.setAsync(true);
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface HVACGateway {
        void topicPublish(String data, @Header(MqttHeaders.TOPIC) String topic);
    }

	// =====================
	// Datalogger outbound
	// =====================
	@Bean
	MessageChannel mqttDataloggerOutboundChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttDataloggerOutboundChannel")
	MessageHandler mqttDataloggerOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
				MqttAsyncClient.generateClientId(), dataloggerMqttClientFactory());
		messageHandler.setAsync(true);
		return messageHandler;
	}

	@MessagingGateway(defaultRequestChannel = "mqttDataloggerOutboundChannel")
	public interface DataloggerGateway {
		void topicPublish(String data, @Header(MqttHeaders.TOPIC) String topic);
	}
}
