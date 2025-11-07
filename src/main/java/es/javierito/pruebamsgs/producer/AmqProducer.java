package es.javierito.pruebamsgs.producer;

import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("amqp")
public class AmqProducer implements SalutatorProducer {


    private final ConnectionFactory connectionFactory;

    public AmqProducer(final @Autowired @Qualifier("amqpConnectionFactory") ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void sayHello() {
        System.out.println("Sending AMQP message");
        try (final Connection connection = connectionFactory.createConnection()) {
            connection.start();
            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final Destination dest = session.createQueue("hello-amqp-spring");
            final MessageProducer producer = session.createProducer(dest);
            final TextMessage textMessage = session.createTextMessage("Hola soy spring y te saludo mediante AMQP");
            producer.send(textMessage);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
