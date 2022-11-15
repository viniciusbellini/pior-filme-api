package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.Studio;
import br.com.viniciusbellini.piorfilmeapi.repositories.StudioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudioService {

    private final StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    @Transactional
    public Studio save(String name) {
        Studio studioFound = findByName(name);
        return studioFound != null ? studioFound : studioRepository.save(new Studio(name.trim()));
    }

    public List<Studio> findAll() {
        return studioRepository.findAll();
    }

    public Studio findByName(String name) {
        return studioRepository.findByName(name);
    }

}
