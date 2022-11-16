package br.com.desafiotexoit.services;

import br.com.desafiotexoit.repositories.StudioRepository;
import br.com.desafiotexoit.models.Studio;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudioService {

    private final StudioRepository studioRepository;

    public StudioService(StudioRepository studioRepository) {
        this.studioRepository = studioRepository;
    }

    public Studio save(String name) {
        Studio studioFound = findByName(name);
        return studioFound != null ? studioFound : save(new Studio(name));
    }

    @Transactional
    public Studio save(Studio studio) {
        return studioRepository.save(studio);
    }

    public List<Studio> findAll() {
        return studioRepository.findAll();
    }

    public Studio findByName(String name) {
        return studioRepository.findByName(name);
    }

}
