package es.javierito.pruebamsgs.api;

import es.javierito.pruebamsgs.producer.SalutatorProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/amqp")
@Profile("amqp")
public class AmqpMsgController extends MsgController{

    public AmqpMsgController(final @Qualifier("amqProducer") SalutatorProducer salutatorProducer) {
        super(salutatorProducer);
    }

}
