package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.Producer;
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

    public Producer save(String name) {
        Producer producer = findByName(name);
        return producer != null ? producer : save(new Producer(name));
    }

    @Transactional
    public Producer save(Producer producer) {
        return producerRepository.save(producer);
    }

    public List<Producer> findAll() {
        return producerRepository.findAll();
    }

    public Producer findByName(String name) {
        return producerRepository.findByName(name);
    }

    private Set<Producer> getAllProducers(List<Record> allTitles) {
        Set<Producer> producers = new LinkedHashSet<>();

        allTitles.forEach(title -> {
            List<String> splitProducers = Arrays.stream(title.getString("producers").split(",\\s|and\\s")).filter(producer -> !producer.isBlank()).collect(Collectors.toList());
            splitProducers.forEach(producer -> producers.add(new Producer(producer.trim())));
        });

        return producers;
    }
}
