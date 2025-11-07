package es.javierito.pruebamsgs.api;


import es.javierito.pruebamsgs.producer.SalutatorProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class MsgController {

    private SalutatorProducer salutatorProducer;

    protected MsgController(SalutatorProducer salutatorProducer) {
        this.salutatorProducer = salutatorProducer;
    }

    protected ResponseEntity<Void> sayHello(final SalutatorProducer  salutatorProducer) {
        try {
            salutatorProducer.sayHello();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/hello")
    public ResponseEntity<Void> sayHelloAmqp() {
        return sayHello(salutatorProducer);
    }
}
