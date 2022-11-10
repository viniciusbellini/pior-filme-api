package br.com.viniciusbellini.piorfilmeapi.onstart;

import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalModel;
import br.com.viniciusbellini.piorfilmeapi.models.ProducerModel;
import br.com.viniciusbellini.piorfilmeapi.models.StudioModel;
import br.com.viniciusbellini.piorfilmeapi.models.TitleModel;
import br.com.viniciusbellini.piorfilmeapi.services.AwardIntervalService;
import br.com.viniciusbellini.piorfilmeapi.services.ProducerService;
import br.com.viniciusbellini.piorfilmeapi.services.StudioService;
import br.com.viniciusbellini.piorfilmeapi.services.TitleService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PremiacaoOnStart {

    private final StudioService studioService;
    private final ProducerService producerService;
    private final TitleService titleService;
    private final AwardIntervalService awardIntervalService;

    public PremiacaoOnStart(StudioService studioService, ProducerService producerService, TitleService titleService, AwardIntervalService awardIntervalService) {
        this.studioService = studioService;
        this.producerService = producerService;
        this.titleService = titleService;
        this.awardIntervalService = awardIntervalService;
    }

    @PostConstruct
    public void init() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);

        String pasta = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        String nomeArquivo = "movielist.csv";
        List<Record> allRecords;

        try {
            CsvParser parser = new CsvParser(settings);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pasta + nomeArquivo));
            allRecords = parser.parseAllRecords(bufferedReader);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        producerService.saveAll(getAllProducers(allRecords));
        studioService.saveAll(getAllStudios(allRecords));
        titleService.saveAll(getAllTitles(allRecords));
        awardIntervalService.saveAll(getAllInvervals());
    }

    private List<AwardIntervalModel> getAllInvervals() {
        List<TitleModel> winnersTitles = titleService.findAll().stream().sorted().filter(TitleModel::isWinner).collect(Collectors.toList());
        HashMap<ProducerModel, List<Integer>> stringListHashMap = new HashMap<>();

        winnersTitles.forEach(title ->
                title.getProducers().forEach(producer -> {
                    if (!stringListHashMap.containsKey(producer)) {
                        List<Integer> years = new ArrayList<>();
                        years.add(Integer.valueOf(title.getYear()));
                        stringListHashMap.put(producer, years);
                    } else {
                        List<Integer> winnerYears = stringListHashMap.get(producer);
                        winnerYears.add(Integer.valueOf(title.getYear()));
                        stringListHashMap.put(producer, winnerYears);
                    }
                })
        );

        List<AwardIntervalModel> intervaloDePremios = new ArrayList<>();

        for (Map.Entry<ProducerModel, List<Integer>> producerModelListEntry : stringListHashMap.entrySet()) {
            List<Integer> value = producerModelListEntry.getValue();

            if (value.size() > 1) {

                for (int i = 1; i < value.size(); i++) {
                    AwardIntervalModel interval = new AwardIntervalModel();
                    interval.setProducer(producerModelListEntry.getKey().getName());
                    Integer previousWing = value.get(i-1);
                    interval.setPreviousWin(previousWing);
                    Integer followingWin = value.get(i);
                    interval.setFollowingWin(followingWin);
                    interval.setInterval(followingWin - previousWing);
                    intervaloDePremios.add(interval);
                }
            }
        }
        return intervaloDePremios;
    }

    private List<ProducerModel> getAllProducers(List<Record> all) {
        HashSet<String> producers = new HashSet<>();

        all.forEach(cada -> {
            String[] splitProducers = cada.getString("producers").split(",\\s|and\\s");
            for (String producer : splitProducers) {
                producers.add(producer.trim());
            }
        });

        List<ProducerModel> producersList = new ArrayList<>();
        producers.forEach(cada -> {
            if (!cada.isEmpty()) {
                ProducerModel producer = new ProducerModel();
                producer.setName(cada);
                producersList.add(producer);
            }
        });

        return producersList;
    }

    private List<StudioModel> getAllStudios(List<Record> all) {
        HashSet<String> studios = new HashSet<>();

        all.forEach(cada -> {
            String[] splitStudios = cada.getString("studios").split(",\\s|and\\s");
            for (String studio : splitStudios) {
                studios.add(studio.trim());
            }
        });

        List<StudioModel> studiosList = new ArrayList<>();
        studios.forEach(cada -> {
            if (!cada.isEmpty()) {
                StudioModel studio = new StudioModel();
                studio.setName(cada);
                studiosList.add(studio);
            }
        });

        return studiosList;
    }

    private List<TitleModel> getAllTitles(List<Record> allRecords) {

        List<TitleModel> titles = new ArrayList<>();
        allRecords.forEach(cada -> {
            TitleModel title = new TitleModel();
            title.setName(cada.getString("title"));
            title.setYear(cada.getString("year"));
            title.setWinner(cada.getString("winner"));

            List<StudioModel> studios = new ArrayList<>();
            for (String studio : cada.getString("studios").split(",\\s|and\\s")) {
                studios.add(studioService.findByName(studio));
            }
            title.setStudios(studios);

            List<ProducerModel> producers = new ArrayList<>();
            for (String producer : cada.getString("producers").split(",\\s|and\\s")) {
                producers.add(producerService.findByName(producer));
            }
            title.setProducers(producers);
            titles.add(title);
        });

        return titles;
    }
}
