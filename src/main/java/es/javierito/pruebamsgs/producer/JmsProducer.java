package es.javierito.pruebamsgs.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("jms")
public class JmsProducer implements SalutatorProducer {
    private final JmsTemplate jmsTemplate;

    public JmsProducer(final @Autowired JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sayHello() {
        System.out.println("Sending JMS message");
        jmsTemplate.convertAndSend("hello-jms-spring", "Hola soy spring y te saludo mediante JMS");
    }
}
