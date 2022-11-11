package br.com.viniciusbellini.piorfilmeapi.onstart;

import br.com.viniciusbellini.piorfilmeapi.services.AwardIntervalService;
import br.com.viniciusbellini.piorfilmeapi.services.TitleService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Component
public class PremiacaoOnStart {

    private final TitleService titleService;
    private final AwardIntervalService awardIntervalService;

    public PremiacaoOnStart(TitleService titleService, AwardIntervalService awardIntervalService) {
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
        List<Record> allTitles;

        try {
            CsvParser parser = new CsvParser(settings);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pasta + nomeArquivo));
            allTitles = parser.parseAllRecords(bufferedReader);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        titleService.generateTable(allTitles);
        awardIntervalService.generateTable();
    }
}
