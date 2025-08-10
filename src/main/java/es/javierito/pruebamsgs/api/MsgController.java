package es.javierito.pruebamsgs.api;

import es.javierito.pruebamsgs.producer.AmqProducer;
import es.javierito.pruebamsgs.producer.JmsProducer;
import es.javierito.pruebamsgs.producer.MqttProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/sayHello")
public class MsgController {
    private final AmqProducer amqProducer;
    private final JmsProducer jmsProducer;
    private final MqttProducer mqttProducer;

    public MsgController(final @Autowired AmqProducer amqProducer,
                         final @Autowired JmsProducer jmsProducer,
                         final @Autowired MqttProducer mqttProducer
                         ) {
        this.amqProducer = amqProducer;
        this.jmsProducer = jmsProducer;
        this.mqttProducer = mqttProducer;
    }


    @PostMapping("/amqp")
    public ResponseEntity<Void> sayHelloWithAmqp() {
        try {
            amqProducer.sayHello();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/jms")
    public ResponseEntity<Void> sayHelloWithJms() {
        try {
            jmsProducer.sayHello();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/mqtt")
    public ResponseEntity<Void> sayHelloWithMqtt() {
        try {
            mqttProducer.sayHello();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }
}
