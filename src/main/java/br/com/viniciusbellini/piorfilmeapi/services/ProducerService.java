package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.ProducerModel;
import br.com.viniciusbellini.piorfilmeapi.repositories.ProducerRepository;
import com.univocity.parsers.common.record.Record;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Transactional
    public ProducerModel save(String name) {
        ProducerModel producer = findByName(name.trim());
        return producer != null ? producer : producerRepository.save(new ProducerModel(name));
    }

    public List<ProducerModel> findAll() {
        return producerRepository.findAll();
    }

    public ProducerModel findByName(String name) {
        return producerRepository.findByName(name);
    }

    private Set<ProducerModel> getAllProducers(List<Record> allTitles) {
        Set<ProducerModel> producers = new LinkedHashSet<>();

        allTitles.forEach(title -> {
            List<String> splitProducers = Arrays.stream(title.getString("producers").split(",\\s|and\\s")).filter(producer -> !producer.isBlank()).collect(Collectors.toList());
            splitProducers.forEach(producer -> producers.add(new ProducerModel(producer.trim())));
        });

        return producers;
    }
}
