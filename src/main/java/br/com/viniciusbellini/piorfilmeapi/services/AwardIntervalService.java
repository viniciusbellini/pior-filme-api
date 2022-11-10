package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalModel;
import br.com.viniciusbellini.piorfilmeapi.repositories.AwardIntervalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public List<AwardIntervalModel> findAll() {
        return awardIntervalRepository.findAll();
    }

}
