package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.TitleModel;
import br.com.viniciusbellini.piorfilmeapi.repositories.TitleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TitleService {

    private final TitleRepository titleRepository;

    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public void saveAll(List<TitleModel> titles) {
        titleRepository.saveAll(titles);
    }

    public List<TitleModel> findAll() {
        return titleRepository.findAll();
    }

    @Transactional
    public void save(TitleModel title) {
        titleRepository.save(title);
    }
}
