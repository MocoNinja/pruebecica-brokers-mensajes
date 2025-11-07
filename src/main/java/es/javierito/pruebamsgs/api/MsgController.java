package es.javierito.pruebamsgs.api;


import es.javierito.pruebamsgs.producer.SalutatorProducer;
import org.springframework.http.ResponseEntity;

public abstract class MsgController {

    protected ResponseEntity<Void> sayHello(final SalutatorProducer  salutatorProducer) {
        try {
            salutatorProducer.sayHello();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }
}
