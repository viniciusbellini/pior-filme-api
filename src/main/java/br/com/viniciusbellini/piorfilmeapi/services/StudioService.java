package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.StudioModel;
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
    public StudioModel save(String name) {
        StudioModel studioFound = findByName(name);
        return studioFound != null ? studioFound : studioRepository.save(new StudioModel(name.trim()));
    }

    public List<StudioModel> findAll() {
        return studioRepository.findAll();
    }

    public StudioModel findByName(String name) {
        return studioRepository.findByName(name);
    }

}
