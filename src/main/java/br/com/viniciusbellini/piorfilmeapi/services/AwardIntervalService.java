package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalModel;
import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalDTO;
import br.com.viniciusbellini.piorfilmeapi.repositories.AwardIntervalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AwardIntervalService {

    private final AwardIntervalRepository awardIntervalRepository;

    public AwardIntervalService(AwardIntervalRepository awardIntervalRepository) {
        this.awardIntervalRepository = awardIntervalRepository;
    }

    @Transactional
    public void saveAll(List<AwardIntervalModel> awardsIntervals) {
        awardIntervalRepository.saveAll(awardsIntervals);
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

}
