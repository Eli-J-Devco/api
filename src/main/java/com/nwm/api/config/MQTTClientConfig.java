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

@Configuration
public class MQTTClientConfig {
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
	
// MQTT Datalogger config
	@Value("${mqtt.datalogger.protocol}")
	private String protocolDatalogger;
	@Value("${mqtt.datalogger.url}")
	private String urlDatalogger;
	@Value("${mqtt.datalogger.port}")
	private String portDatalogger;
	@Value("${mqtt.datalogger.username}")
	private String usernameDatalogger;
	@Value("${mqtt.datalogger.password}")
	private String passwordDatalogger;
	@Value("${mqtt.datalogger.timeout}")
	private int timeoutDatalogger;
	@Value("${mqtt.datalogger.topic}")
	private String topicDatalogger;

//MQTT Web ssl config
	@Value("${mqtt.web.protocol}")
	private String protocolWeb;
	@Value("${mqtt.web.url}")
	private String urlWeb;
	@Value("${mqtt.web.port}")
	private String portWeb;
	@Value("${mqtt.web.username}")
	private String usernameWeb;
	@Value("${mqtt.web.password}")
	private String passwordWeb;
	@Value("${mqtt.web.timeout}")
	private int timeoutWeb;

	// HVAC MQTT Client Factory
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
	
	// Datalogger MQTT Client Factory
	@Bean
	MqttPahoClientFactory mqttDataloggerClientFactory() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { protocolDatalogger.concat("://").concat(urlDatalogger).concat(":").concat(portDatalogger) });
		options.setUserName(usernameDatalogger);
		options.setPassword(passwordDatalogger.toCharArray());
		options.setCleanSession(false);
		options.setAutomaticReconnect(true);
		options.setConnectionTimeout(timeoutDatalogger);
		
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(options);
		
		return factory;
    }
	
	// Web SSL MQTT Client Factory
	@Bean
	MqttPahoClientFactory mqttWebClientFactory() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { protocolWeb.concat("://").concat(urlWeb).concat(":").concat(portWeb) });
		options.setUserName(usernameWeb);
		options.setPassword(passwordWeb.toCharArray());
		options.setCleanSession(false);
		options.setAutomaticReconnect(true);
		options.setConnectionTimeout(timeoutWeb);
		
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(options);
		
		return factory;
    }
	
    // HVAC Input Channel and Inbound
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
	
	// Datalogger Input Channel and Inbound
	@Bean
	MessageChannel mqttDataloggerInputChannel() {
		return new DirectChannel();
	}
	
	@Bean
	MessageProducer dataloggerInbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(MqttAsyncClient.generateClientId(), mqttDataloggerClientFactory(), topicDatalogger);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setOutputChannel(mqttDataloggerInputChannel());
		
		return adapter;
	}
	
	// Web SSL Input Channel and Inbound
	@Bean
	MessageChannel mqttWebInputChannel() {
		return new DirectChannel();
	}
	
	@Bean
	MessageProducer webInbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(MqttAsyncClient.generateClientId(), mqttWebClientFactory(), "web/+/+/telemetry");
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setOutputChannel(mqttWebInputChannel());
		
		return adapter;
	}
	
	// HVAC Message Handler
	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	MessageHandler handler() {
		return new MessageHandler() {
			SitesOverviewHVACService service = new SitesOverviewHVACService();

			@Override
			public void handleMessage(Message<?> message) {
				Object topicHeader = message.getHeaders().get("mqtt_receivedTopic");
				if (topicHeader != null) {
					String[] topic = topicHeader.toString().split("/");
					if (topic.length >= 4 && topic[2].equals("NextWave123") && topic[3].equals("telemetry")) {
						service.saveFieldData(message);
					}
				}
			}
		};
	}
	
	// Datalogger Message Handler
	@Bean
	@ServiceActivator(inputChannel = "mqttDataloggerInputChannel")
	MessageHandler dataloggerHandler() {
		return new MessageHandler() {
			com.nwm.api.controllers.MQTTJsonController controller = new com.nwm.api.controllers.MQTTJsonController();

			@Override
			public void handleMessage(Message<?> message) {
				Object topicHeader = message.getHeaders().get("mqtt_receivedTopic");
				if (topicHeader != null) {
					System.out.println("Datalogger message received from topic: " + topicHeader.toString());
					System.out.println("Message payload: " + message.getPayload().toString());
					
					// Process the message using MQTTJsonController
					boolean result = controller.handleMQTTMessage(message);
					if (result) {
						System.out.println("Successfully processed MQTT JSON message");
					} else {
						System.out.println("Failed to process MQTT JSON message");
					}
				}
			}
		};
	}
	
	// Web SSL Message Handler
	@Bean
	@ServiceActivator(inputChannel = "mqttWebInputChannel")
	MessageHandler webHandler() {
		return new MessageHandler() {
			com.nwm.api.controllers.MQTTJsonController controller = new com.nwm.api.controllers.MQTTJsonController();

			@Override
			public void handleMessage(Message<?> message) {
				Object topicHeader = message.getHeaders().get("mqtt_receivedTopic");
				if (topicHeader != null) {
					String[] topic = topicHeader.toString().split("/");
					if (topic.length >= 4 && topic[3].equals("telemetry")) {
						System.out.println("Web message received from topic: " + topicHeader.toString());
						System.out.println("Message payload: " + message.getPayload().toString());
						
						// Process the message using MQTTJsonController
						boolean result = controller.handleMQTTMessage(message);
						if (result) {
							System.out.println("Successfully processed MQTT JSON message");
						} else {
							System.out.println("Failed to process MQTT JSON message");
						}
					}
				}
			}
		};
	}
	
	// HVAC Outbound
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
	
	// Datalogger Outbound
	@Bean
    MessageChannel mqttDataloggerOutboundChannel() {
        return new DirectChannel();
    }
	
	@Bean
    @ServiceActivator(inputChannel = "mqttDataloggerOutboundChannel")
    MessageHandler mqttDataloggerOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), mqttDataloggerClientFactory());
        messageHandler.setAsync(true);
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttDataloggerOutboundChannel")
    public interface DataloggerGateway {
        void topicPublish(String data, @Header(MqttHeaders.TOPIC) String topic);
    }
	
	// Web SSL Outbound
	@Bean
    MessageChannel mqttWebOutboundChannel() {
        return new DirectChannel();
    }
	
	@Bean
    @ServiceActivator(inputChannel = "mqttWebOutboundChannel")
    MessageHandler mqttWebOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(MqttAsyncClient.generateClientId(), mqttWebClientFactory());
        messageHandler.setAsync(true);
        return messageHandler;
    }

    @MessagingGateway(defaultRequestChannel = "mqttWebOutboundChannel")
    public interface WebGateway {
        void topicPublish(String data, @Header(MqttHeaders.TOPIC) String topic);
    }
}
