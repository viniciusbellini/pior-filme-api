package br.com.desafiotexoit.controllers;

import br.com.desafiotexoit.models.AwardIntervalDTO;
import br.com.desafiotexoit.services.AwardIntervalService;
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
    public ResponseEntity<AwardIntervalDTO> allAwardsIntervals() {
        return awardIntervalService.findAwardsIntervals();
    }

}
