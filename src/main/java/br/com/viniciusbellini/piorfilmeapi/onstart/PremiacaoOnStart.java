package br.com.viniciusbellini.piorfilmeapi.onstart;

import br.com.viniciusbellini.piorfilmeapi.models.PremiacaoModel;
import br.com.viniciusbellini.piorfilmeapi.services.PremiacaoService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class PremiacaoOnStart {

    private final PremiacaoService premiacaoService;

    public PremiacaoOnStart(PremiacaoService premiacaoService) {
        this.premiacaoService = premiacaoService;
    }

    @PostConstruct
    public void init() {
        List<PremiacaoModel> premiacaoList = new ArrayList<>();

        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);

        String pasta = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        String nomeArquivo = "movielist.csv";

        try {
            CsvParser parser = new CsvParser(settings);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pasta + nomeArquivo));
            List<Record> parseAllRecords = parser.parseAllRecords(bufferedReader);
            parseAllRecords.forEach(record -> {
                PremiacaoModel premiacao = new PremiacaoModel();
                premiacao.setYear(record.getString("year"));
                premiacao.setTitle(record.getString("title"));
                premiacao.setStudios(record.getString("studios"));
                premiacao.setProducers(record.getString("producers"));
                premiacao.setWinner(record.getString("winner"));
                premiacaoList.add(premiacao);
            });

            premiacaoService.saveAll(premiacaoList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
