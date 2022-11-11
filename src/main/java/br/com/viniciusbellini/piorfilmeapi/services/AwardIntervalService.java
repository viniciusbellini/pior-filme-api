package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalModel;
import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalDTO;
import br.com.viniciusbellini.piorfilmeapi.models.ProducerModel;
import br.com.viniciusbellini.piorfilmeapi.models.TitleModel;
import br.com.viniciusbellini.piorfilmeapi.repositories.AwardIntervalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AwardIntervalService {

    private final AwardIntervalRepository awardIntervalRepository;
    private final TitleService titleService;

    public AwardIntervalService(AwardIntervalRepository awardIntervalRepository, TitleService titleService) {
        this.awardIntervalRepository = awardIntervalRepository;
        this.titleService = titleService;
    }

    @Transactional
    public void generateTable() {
        awardIntervalRepository.saveAll(getAllInvervals());
    }

    public ResponseEntity<AwardIntervalDTO> findAwardsIntervals() {
        Map<Integer, List<AwardIntervalModel>> groupingByInterval = awardIntervalRepository.findAll().stream().sorted().collect(Collectors.groupingBy(AwardIntervalModel::getInterval));

        if (groupingByInterval.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<AwardIntervalModel> fastestAwards = groupingByInterval.entrySet().stream().min(Map.Entry.comparingByKey()).get().getValue();
        List<AwardIntervalModel> longestAwardsInterval = groupingByInterval.entrySet().stream().max(Map.Entry.comparingByKey()).get().getValue();

        return ResponseEntity.ok().body(AwardIntervalDTO.transformToDTO(fastestAwards, longestAwardsInterval));
    }

    private List<AwardIntervalModel> getAllInvervals() {
        List<TitleModel> winnersTitles = titleService.findAll().stream().sorted().filter(TitleModel::isWinner).collect(Collectors.toList());
        HashMap<ProducerModel, List<Integer>> producerWinner = new HashMap<>();

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

        List<AwardIntervalModel> intervaloDePremios = new ArrayList<>();

        for (Map.Entry<ProducerModel, List<Integer>> producerModelListEntry : producerWinner.entrySet()) {
            List<Integer> value = producerModelListEntry.getValue();

            if (value.size() > 1) {
                for (int i = 1; i < value.size(); i++) {
                    AwardIntervalModel interval = new AwardIntervalModel();
                    interval.setProducer(producerModelListEntry.getKey().getName());
                    Integer previousWing = value.get(i-1);
                    interval.setPreviousWin(previousWing);
                    Integer followingWin = value.get(i);
                    interval.setFollowingWin(followingWin);
                    interval.setInterval(followingWin - previousWing);
                    intervaloDePremios.add(interval);
                }
            }
        }
        return intervaloDePremios;
    }

}
