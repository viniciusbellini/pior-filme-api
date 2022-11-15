
package br.com.viniciusbellini.piorfilmeapi.controllers;

import br.com.viniciusbellini.piorfilmeapi.models.Producer;
import br.com.viniciusbellini.piorfilmeapi.services.ProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok().body(producerService.findAll());
    }

}
