package br.com.viniciusbellini.piorfilmeapi.onstart;

import br.com.viniciusbellini.piorfilmeapi.config.AppProperties;
import br.com.viniciusbellini.piorfilmeapi.services.TitleService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

@Profile("dev")
@Component
public class PremiacaoOnStart {

    private final TitleService titleService;
    private final AppProperties appProperties;

    public PremiacaoOnStart(TitleService titleService, AppProperties appProperties) {
        this.titleService = titleService;
        this.appProperties = appProperties;
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
            URL resource = getClass().getResource("/" + appProperties.getCsvName() + ".csv");
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
