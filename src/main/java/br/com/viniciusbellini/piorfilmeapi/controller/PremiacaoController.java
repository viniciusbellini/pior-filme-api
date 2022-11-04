package br.com.viniciusbellini.piorfilmeapi.controller;

import br.com.viniciusbellini.piorfilmeapi.model.Premiacao;
import br.com.viniciusbellini.piorfilmeapi.repository.PremiacaoRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/premiacao")
public class PremiacaoController {
    @Autowired
    private PremiacaoRepository premiacaoRepository;

    @GetMapping
    public List<Premiacao> listar() {
        return premiacaoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Premiacao adicionar(@RequestBody Premiacao premiacao) {
        return premiacaoRepository.save(premiacao);
    }

    @PostMapping("/upload")
    public String uploadData(@RequestParam("file") MultipartFile file) throws Exception{
        List<Premiacao> premiacaoList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();

        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setDelimiterDetectionEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);

        CsvParser csvParser = new CsvParser(settings);
        List<Record> parseAllRecords = csvParser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            Premiacao premiacao = new Premiacao();
            premiacao.setYear(record.getInt("year"));
            premiacao.setTitle(record.getString("title"));
            premiacao.setStudios(record.getString("studios"));
            premiacao.setProducers(record.getString("producers"));
            premiacao.setWinner(record.getString("winner"));
            premiacaoList.add(premiacao);
        });

        premiacaoRepository.saveAll(premiacaoList);
        return "Upload Successfull!";
    }
}
