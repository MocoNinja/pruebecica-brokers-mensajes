package es.javierito.pruebamsgs.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.UUID;

@Configuration
public class MessagingConfig {
    @Value("${spring.artemis.url}")
    private String brokerUrl;
    @Value("${spring.artemis.port.jms}")
    private int jmsPort;
    @Value("${spring.artemis.port.amqp}")
    private int amqpPort;
    @Value("${spring.artemis.port.mqtt}")
    private int mqttPort;
    @Value("${spring.artemis.user}")
    private String user;
    @Value("${spring.artemis.password}")
    private String password;

    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        final String fullUrl = "tcp://" + brokerUrl + ":" + jmsPort;
        final ActiveMQJMSConnectionFactory connectionFactory = new ActiveMQJMSConnectionFactory(fullUrl);

        connectionFactory.setUser(user);
        connectionFactory.setPassword(password);

        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public ConnectionFactory amqpConnectionFactory() {
        final String fullUrl = "amqp://" + brokerUrl + ":" + amqpPort;
        return new JmsConnectionFactory(user, password, fullUrl);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory jmsConnectionFactory) {
        final JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

    @Bean
    public IMqttClient mqttClient() throws MqttException {
        final String fullUrl = "tcp://" + brokerUrl + ":" + mqttPort;
        final String clientId = "mqtt-producer-" + UUID.randomUUID();

        final MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(30);
        options.setKeepAliveInterval(60);
        options.setUserName(user);
        options.setPassword(password.toCharArray());

        final IMqttClient client = new MqttClient(fullUrl, clientId, new MemoryPersistence());
        client.connect(options);

        return client;
    }

}
