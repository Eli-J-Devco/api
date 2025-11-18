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
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

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
		String[] topics = {
			"hvac/+/t/+/NextWave123/telemetry",
			"hvac/+/+/+/+/+"
		};
		
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
			MqttAsyncClient.generateClientId(), 
			mqttClientFactory(), 
			topics
		);
		DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
		converter.setPayloadAsBytes(true);
		adapter.setConverter(converter);
		adapter.setOutputChannel(mqttInputChannel());
		adapter.setErrorChannel(mqttErrorChannel()); 
		
		adapter.setRecoveryInterval(30000);
		adapter.setCompletionTimeout(5000);
		
		return adapter;
    }
	
	@Bean
	MessageChannel mqttErrorChannel() {
		return new DirectChannel();
	}
	
	@Bean
	@ServiceActivator(inputChannel = "mqttErrorChannel")
	MessageHandler errorHandler() {
		return message -> {
			Throwable payload = (Throwable) message.getPayload();
			String errorMsg = payload.getMessage() != null ? payload.getMessage() : "Unknown error";
			System.out.println("[MQTT ERROR] " + payload.getClass().getSimpleName() + ": " + errorMsg);
		};
	}
	
	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	MessageHandler handler() {
		SitesOverviewHVACService service = new SitesOverviewHVACService();
		return message -> {
			try {
				Object topicHeader = message.getHeaders().get("mqtt_receivedTopic");
				if (topicHeader == null) return;
				
				String topicStr = topicHeader.toString();
				String[] topic = topicStr.split("/");
				
				if (topicStr.startsWith("hvac/") && topic.length >= 6 && 
					topic[4].equals("NextWave123") && topic[5].equals("telemetry")) {
					service.saveFieldData(message);
				}
			} catch (Exception e) {
				System.out.println("ERROR handling MQTT message: " + e.getMessage());
			}
		};
	}
	

}
