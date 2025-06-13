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
}
