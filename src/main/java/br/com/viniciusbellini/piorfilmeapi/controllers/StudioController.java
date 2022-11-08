package br.com.viniciusbellini.piorfilmeapi.controllers;

import br.com.viniciusbellini.piorfilmeapi.services.StudioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/studio")
public class StudioController {
    private final StudioService studioService;

    public StudioController(StudioService studioService) {
        this.studioService = studioService;
    }

    @GetMapping
    public ResponseEntity listar() {
        return ResponseEntity.ok().body(studioService.findAll());
    }

}
