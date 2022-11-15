package br.com.viniciusbellini.piorfilmeapi.controllers;

import br.com.viniciusbellini.piorfilmeapi.models.Title;
import br.com.viniciusbellini.piorfilmeapi.services.TitleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/title")
public class TitleController {
    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping
    public ResponseEntity<List<Title>> allTitles() {
        return ResponseEntity.ok().body(titleService.findAll());
    }

}
