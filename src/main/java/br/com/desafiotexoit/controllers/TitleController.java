package br.com.desafiotexoit.controllers;

import br.com.desafiotexoit.models.Title;
import br.com.desafiotexoit.services.TitleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping
    public ResponseEntity<List<Title>> uploadFile(@RequestParam("file") MultipartFile titles) {
        return ResponseEntity.status(HttpStatus.CREATED).body(titleService.uploadFile(titles));
    }
}
