package br.com.desafiotexoit.services;

import br.com.desafiotexoit.repositories.TitleRepository;
import br.com.desafiotexoit.error.TitleAlreadyExistsException;
import br.com.desafiotexoit.models.Producer;
import br.com.desafiotexoit.models.Studio;
import br.com.desafiotexoit.models.Title;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.dao.DataIntegrityViolationException;
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

    public List<Title> generateTable(List<Record> allTitles) {
        return getAllTitles(allTitles).stream().map(this::save).collect(Collectors.toList());
    }

    @Transactional
    public Title save(Title title) {
        try {
           return titleRepository.save(title);
        } catch (DataIntegrityViolationException e) {
            throw new TitleAlreadyExistsException(title.toString());
        }
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

            title.setStudios(registerStudiosFromTitle(cada));
            title.setProducers(registerProducerFromTitle(cada));
            titles.add(title);
        });

        return titles;
    }

    private List<Producer> registerProducerFromTitle(Record cada) {
        List<String> splitProducers = Arrays.stream(cada.getString("producers").split(",\\s|and\\s")).filter(producer -> !producer.isBlank()).collect(Collectors.toList());
        return splitProducers.stream().map(name -> producerService.save(name.trim())).collect(Collectors.toList());
    }

    private List<Studio> registerStudiosFromTitle(Record cada) {
        List<String> splitStudios = Arrays.stream(cada.getString("studios").split(",\\s|and\\s")).filter(studio -> !studio.isBlank()).collect(Collectors.toList());
        return splitStudios.stream().map(name -> studioService.save(name.trim())).collect(Collectors.toList());
    }

    public List<Title> uploadFile(MultipartFile titles) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);

        List<Record> allTitles;
        try {
            CsvParser parser = new CsvParser(settings);
            InputStream inputStream = titles.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            allTitles = parser.parseAllRecords(bufferedReader);
            return generateTable(allTitles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Title findByName(String name) {
        return titleRepository.findByName(name);
    }
}
