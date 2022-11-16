
package br.com.desafiotexoit.controllers;

import br.com.desafiotexoit.models.Producer;
import br.com.desafiotexoit.services.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/producer")
public class ProducerController {
    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping
    public ResponseEntity<List<Producer>> allProducers() {
        return new ResponseEntity<>(producerService.findAll(), HttpStatus.OK);//ResponseEntity.ok().body(producerService.findAll());
    }

    @PostMapping
    public Producer create(@Valid @RequestBody Producer producer) {
        return producerService.save(producer);
    }

}
