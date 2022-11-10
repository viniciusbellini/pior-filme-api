package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalModel;
import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalDTO;
import br.com.viniciusbellini.piorfilmeapi.repositories.AwardIntervalRepository;
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

    public AwardIntervalDTO findAwardsIntervals() {
        List<AwardIntervalModel> all = awardIntervalRepository.findAll();
        Map<Integer, List<AwardIntervalModel>> groupingByInterval = all.stream().sorted().collect(Collectors.groupingBy(AwardIntervalModel::getInterval));
        List<AwardIntervalModel> fastestAwards = groupingByInterval.entrySet().stream().min(Map.Entry.comparingByKey()).get().getValue();
        List<AwardIntervalModel> longestAwardsInterval = groupingByInterval.entrySet().stream().max(Map.Entry.comparingByKey()).get().getValue();

        return AwardIntervalDTO.transformToDTO(fastestAwards, longestAwardsInterval);
    }

}
