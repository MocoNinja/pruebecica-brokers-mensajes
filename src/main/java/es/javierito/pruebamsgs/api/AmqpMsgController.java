package es.javierito.pruebamsgs.api;

import es.javierito.pruebamsgs.producer.SalutatorProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/amqp")
@Profile("amqp")
public class AmqpMsgController extends MsgController{
    private SalutatorProducer salutatorProducer;

    public AmqpMsgController(final @Qualifier("amqProducer") SalutatorProducer salutatorProducer) {
        this.salutatorProducer = salutatorProducer;
    }

    @PostMapping("/hello")
    public ResponseEntity<Void> sayHelloAmqp() {
        return sayHello(salutatorProducer);
    }

}
