package com.nwm.api.config;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.context.event.EventListener;
import org.springframework.integration.mqtt.event.MqttIntegrationEvent;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;

import com.nwm.api.services.building.SitesOverviewHVACService;

@Configuration
@ConditionalOnProperty(name = "mqtt.hvac.enabled", havingValue = "true", matchIfMissing = true)
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
	
	@Bean
	MqttPahoClientFactory mqttClientFactory() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] { protocol.concat("://").concat(url).concat(":").concat(port) });
		options.setUserName(username);
		options.setPassword(password.toCharArray());
		options.setCleanSession(false);
		options.setAutomaticReconnect(true);
		options.setConnectionTimeout(timeout);
		
		options.setKeepAliveInterval(60);
		options.setMaxInflight(10);
		options.setExecutorServiceTimeout(1);
		
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
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(MqttAsyncClient.generateClientId(), mqttClientFactory(), "hvac/+/t/+/NextWave123/telemetry");
		DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
		converter.setPayloadAsBytes(true);
		adapter.setConverter(converter);
		adapter.setOutputChannel(mqttInputChannel());
		adapter.setErrorChannel(mqttErrorChannel()); 
		
		adapter.setRecoveryInterval(30000); // 30 giây
		adapter.setCompletionTimeout(5000);  // 5 giây
		
		System.out.println("MQTT Adapter configured for: " + protocol + "://" + url + ":" + port);
		
		return adapter;
    }
	
	@Bean
	MessageChannel mqttErrorChannel() {
		return new DirectChannel();
	}
	
	@Bean
	@ServiceActivator(inputChannel = "mqttErrorChannel")
	MessageHandler errorHandler() {
		return new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) {
				Throwable payload = (Throwable) message.getPayload();
				String errorMsg = payload.getMessage() != null ? payload.getMessage() : "Unknown error";
				System.out.println("[MQTT ERROR] " + payload.getClass().getSimpleName() + ": " + errorMsg);
				
				if (errorMsg.contains("Connection refused") || 
					errorMsg.contains("Unable to connect") ||
					errorMsg.contains("Connection lost")) {
					System.out.println("[MQTT] Broker connection failed - will retry automatically in 30s");
				}
			}
		};
	}
	
	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	MessageHandler handler() {
		return new MessageHandler() {
			SitesOverviewHVACService service = new SitesOverviewHVACService();

			@Override
			public void handleMessage(Message<?> message) {
				String[] topic = message.getHeaders().get("mqtt_receivedTopic").toString().split("/");
				if (topic[4].equals("NextWave123") && topic[5].equals("telemetry")) {
					service.saveFieldData(message);
				}
			}
		};
	}
	
	// Event listeners để lắng nghe sự kiện MQTT thực sự
	@EventListener
	public void handleMqttConnectionFailed(MqttConnectionFailedEvent event) {
		System.out.println("[MQTT EVENT] Connection failed: " + event.getCause().getMessage());
		System.out.println("[MQTT EVENT] Will retry automatically...");
	}
	
	@EventListener
	public void handleMqttSubscribed(MqttSubscribedEvent event) {
		System.out.println("[MQTT EVENT] Successfully subscribed to: " + event.getMessage());
		System.out.println("[MQTT EVENT] Connection established and ready to receive messages");
	}
	
	@EventListener
	public void handleMqttIntegrationEvent(MqttIntegrationEvent event) {
		System.out.println("[MQTT EVENT] " + event.getClass().getSimpleName());
	}
}
