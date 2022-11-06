package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.PremiacaoModel;
import br.com.viniciusbellini.piorfilmeapi.repositories.PremiacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class PremiacaoService {

    private final PremiacaoRepository premiacaoRepository;

    public PremiacaoService(PremiacaoRepository premiacaoRepository) {
        this.premiacaoRepository = premiacaoRepository;
    }

    public void saveAll(List<PremiacaoModel> premiacoes) {
        premiacaoRepository.saveAll(premiacoes);
    }

    public List<PremiacaoModel> findAll() {
        return premiacaoRepository.findAll();
    }

    public PremiacaoModel save(PremiacaoModel premiacao) {
        return premiacaoRepository.save(premiacao);
    }

    public List<PremiacaoModel> intervaloDePremios() {
        List<PremiacaoModel> all = findAll();

        HashSet<String> allProducers = getAllProducers(all);

        return null;
    }

    private HashSet<String> getAllProducers(List<PremiacaoModel> all) {
        HashSet<String> strings = new HashSet<>();

        all.forEach(cada -> {
            String[] split = cada.getProducers().split(",\\s|and\\s");
            for (String s : split) {
                strings.add(s.trim());
            }
        });

        return strings;
    }
}
