package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.ProducerModel;
import br.com.viniciusbellini.piorfilmeapi.repositories.ProducerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Transactional
    public void saveAll(List<ProducerModel> producers) {
        producerRepository.saveAll(producers);
    }

    public List<ProducerModel> findAll() {
        return producerRepository.findAll();
    }

    public ProducerModel findByName(String name) {
        return producerRepository.findByName(name);
    }
}
