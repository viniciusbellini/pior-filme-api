package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.ProducerModel;
import br.com.viniciusbellini.piorfilmeapi.models.StudioModel;
import br.com.viniciusbellini.piorfilmeapi.models.TitleModel;
import br.com.viniciusbellini.piorfilmeapi.repositories.TitleRepository;
import com.univocity.parsers.common.record.Record;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleService {

    private final TitleRepository titleRepository;
    private final ProducerService producerService;
    private final StudioService studioService;

    public TitleService(TitleRepository titleRepository, ProducerService producerService, StudioService studioService) {
        this.titleRepository = titleRepository;
        this.producerService = producerService;
        this.studioService = studioService;
    }

    public void generateTable(List<Record> allTitles) {
        saveAll(getAllTitles(allTitles));
    }

    @Transactional
    public void saveAll(List<TitleModel> titles) {
        titleRepository.saveAll(titles);
    }

    public List<TitleModel> findAll() {
        return titleRepository.findAll();
    }

    private List<TitleModel> getAllTitles(List<Record> allTitles) {

        List<TitleModel> titles = new ArrayList<>();
        allTitles.forEach(cada -> {
            TitleModel title = new TitleModel();
            title.setName(cada.getString("title"));
            title.setYear(cada.getString("year"));
            title.setWinner(cada.getString("winner"));

            List<String> splitStudios = Arrays.stream(cada.getString("studios").split(",\\s|and\\s")).filter(studio -> !studio.isBlank()).collect(Collectors.toList());
            List<StudioModel> studios = splitStudios.stream().map(studioService::save).collect(Collectors.toList());
            title.setStudios(studios);

            List<String> splitProducers = Arrays.stream(cada.getString("producers").split(",\\s|and\\s")).filter(producer -> !producer.isBlank()).collect(Collectors.toList());
            List<ProducerModel> producers = splitProducers.stream().map(producerService::save).collect(Collectors.toList());
            title.setProducers(producers);
            titles.add(title);
        });

        return titles;
    }
}
