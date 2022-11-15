package br.com.viniciusbellini.piorfilmeapi.services;

import br.com.viniciusbellini.piorfilmeapi.models.Producer;
import br.com.viniciusbellini.piorfilmeapi.models.Studio;
import br.com.viniciusbellini.piorfilmeapi.models.Title;
import br.com.viniciusbellini.piorfilmeapi.repositories.TitleRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public void saveAll(List<Title> titles) {
        titleRepository.saveAll(titles);
    }

    public List<Title> findAll() {
        return titleRepository.findAll();
    }

    private List<Title> getAllTitles(List<Record> allTitles) {

        List<Title> titles = new ArrayList<>();
        allTitles.forEach(cada -> {
            Title title = new Title();
            title.setName(cada.getString("title"));
            title.setYear(cada.getString("year"));
            title.setWinner(cada.getString("winner"));

            title.setStudios(createStudiosRelatedTitle(cada));
            title.setProducers(createProducerRelatedTitle(cada));
            titles.add(title);
        });

        return titles;
    }

    private List<Producer> createProducerRelatedTitle(Record cada) {
        List<String> splitProducers = Arrays.stream(cada.getString("producers").split(",\\s|and\\s")).filter(producer -> !producer.isBlank()).collect(Collectors.toList());
        return splitProducers.stream().map(name -> producerService.save(name.trim())).collect(Collectors.toList());
    }

    private List<Studio> createStudiosRelatedTitle(Record cada) {
        List<String> splitStudios = Arrays.stream(cada.getString("studios").split(",\\s|and\\s")).filter(studio -> !studio.isBlank()).collect(Collectors.toList());
        return splitStudios.stream().map(studioService::save).collect(Collectors.toList());
    }
}
