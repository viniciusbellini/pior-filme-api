package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.error.ApplicationExceptions;
import br.com.viniciusbellini.piorfilmeapi.models.Producer;
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
    public Producer save(String name) {
        Producer producer = findByName(name);
        return producer != null ? producer : save(new Producer(name));
    }

    @Transactional
    public Producer save(Producer producer) {
        return producerRepository.save(producer);
    }

    public List<Producer> findAll() {
        List<Producer> producers = producerRepository.findAll();
        if (producers.isEmpty())
          throw new ApplicationExceptions("There are no registered producers!");
        return producerRepository.findAll();
    }

    public Producer findByName(String name) {
        return producerRepository.findByName(name);
    }
}
