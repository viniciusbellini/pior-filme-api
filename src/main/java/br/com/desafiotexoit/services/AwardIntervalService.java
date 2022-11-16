package br.com.desafiotexoit.services;

import br.com.desafiotexoit.models.AwardIntervalDTO;
import br.com.desafiotexoit.models.AwardInterval;
import br.com.desafiotexoit.models.Producer;
import br.com.desafiotexoit.models.Title;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AwardIntervalService {

    private final TitleService titleService;

    public AwardIntervalService(TitleService titleService) {
        this.titleService = titleService;
    }

    public ResponseEntity<AwardIntervalDTO> findAwardsIntervals() {
        Map<Integer, List<AwardInterval>> groupingByInterval = getAllInvervals().stream().sorted().collect(Collectors.groupingBy(AwardInterval::getInterval));

        if (groupingByInterval.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<AwardInterval> fastestAwards = groupingByInterval.entrySet().stream().min(Map.Entry.comparingByKey()).get().getValue();
        List<AwardInterval> longestAwardsInterval = groupingByInterval.entrySet().stream().max(Map.Entry.comparingByKey()).get().getValue();

        return ResponseEntity.ok().body(AwardIntervalDTO.transformToDTO(fastestAwards, longestAwardsInterval));
    }

    private List<AwardInterval> getAllInvervals() {
        List<Title> winnersTitles = titleService.findAll().stream().sorted().filter(Title::isWinner).collect(Collectors.toList());
        HashMap<Producer, List<Integer>> producerWinner = new HashMap<>();

        winnersTitles.forEach(title ->
                title.getProducers().forEach(producer -> {
                    if (!producerWinner.containsKey(producer)) {
                        List<Integer> years = new ArrayList<>();
                        years.add(title.getYear());
                        producerWinner.put(producer, years);
                    } else {
                        List<Integer> winnerYears = producerWinner.get(producer);
                        winnerYears.add(title.getYear());
                        producerWinner.put(producer, winnerYears);
                    }
                })
        );

        List<AwardInterval> intervaloDePremios = new ArrayList<>();
        producerWinner.forEach((producer, years) -> {
            var producerName = producer.getName();
            Iterator<Integer> iterator = years.iterator();
            var previewsWin = iterator.next();
            while (iterator.hasNext()) {
                AwardInterval interval = new AwardInterval();
                interval.setProducer(producerName);
                interval.setPreviousWin(previewsWin);
                var followingWin = iterator.next();
                interval.setFollowingWin(followingWin);
                interval.setInterval(followingWin - previewsWin);
                intervaloDePremios.add(interval);
                previewsWin = followingWin;
            }
        });
        return intervaloDePremios;
    }
}
