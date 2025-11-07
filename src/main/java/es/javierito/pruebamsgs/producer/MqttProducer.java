package es.javierito.pruebamsgs.producer;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Profile("mqtt")
public class MqttProducer implements SalutatorProducer {
    private final IMqttClient imqttClient;

    public MqttProducer(final @Autowired IMqttClient imqttClient) {
        this.imqttClient = imqttClient;
    }

    @Override
    public void sayHello() {
        System.out.println("Sending MQTT (v3, QoS1) message");
        final MqttMessage mqttMessage = new MqttMessage("Hola soy spring y te saludo mediante MQTT".getBytes(StandardCharsets.UTF_8));
        mqttMessage.setQos(1);
        mqttMessage.setRetained(true);

        try {
            imqttClient.publish("hello-mqtt-spring", mqttMessage);
        } catch (MqttException e) {
            // TODO
            System.err.println("Too lazy to handle this fuckup in the interface rn: " + e.getMessage());
        }
    }
}
