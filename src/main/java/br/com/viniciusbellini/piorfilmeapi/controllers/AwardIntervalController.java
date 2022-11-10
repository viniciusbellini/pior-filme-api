package br.com.viniciusbellini.piorfilmeapi.controllers;

import br.com.viniciusbellini.piorfilmeapi.services.AwardIntervalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/awardInterval")
public class AwardIntervalController {
    private final AwardIntervalService awardIntervalService;

    public AwardIntervalController(AwardIntervalService awardIntervalService) {
        this.awardIntervalService = awardIntervalService;
    }

    @GetMapping
    public ResponseEntity allAwardsIntervals() {
        return ResponseEntity.ok().body(awardIntervalService.findAll());
    }

}
