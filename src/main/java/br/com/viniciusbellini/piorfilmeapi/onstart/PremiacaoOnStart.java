package br.com.viniciusbellini.piorfilmeapi.onstart;

import br.com.viniciusbellini.piorfilmeapi.services.TitleService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

@Component
public class PremiacaoOnStart {

    private final TitleService titleService;
    @Value("${csv.name}")
    String csvName;

    public PremiacaoOnStart(TitleService titleService) {
        this.titleService = titleService;
    }

    @PostConstruct
    public void init() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);

        List<Record> allTitles;
        try {
            CsvParser parser = new CsvParser(settings);
            URL resource = PremiacaoOnStart.class.getResource("/" + csvName + ".csv");
            if (resource == null)
                throw new FileNotFoundException();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(resource.getPath()));
            allTitles = parser.parseAllRecords(bufferedReader);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        titleService.generateTable(allTitles);
    }
}
