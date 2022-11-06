package br.com.viniciusbellini.piorfilmeapi.controllers;

import br.com.viniciusbellini.piorfilmeapi.models.PremiacaoModel;
import br.com.viniciusbellini.piorfilmeapi.services.PremiacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/premiacao")
public class PremiacaoController {
    private final PremiacaoService premiacaoService;

    public PremiacaoController(PremiacaoService premiacaoService) {
        this.premiacaoService = premiacaoService;
    }

    @GetMapping
    public List<PremiacaoModel> listar() {
        return premiacaoService.findAll();
    }

    @GetMapping(value = "/intervalo")
    public List<PremiacaoModel> intervaloDePremios() {
        return premiacaoService.intervaloDePremios();
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public PremiacaoEntity adicionar(@RequestBody PremiacaoEntity premiacao) {
//        return premiacaoService.save(premiacao);
//    }

//    @PostMapping("/upload")
//    public String uploadData(@RequestParam("file") MultipartFile file) throws Exception{
//        List<PremiacaoEntity> premiacaoList = new ArrayList<>();
//        InputStream inputStream = file.getInputStream();
//
//        CsvParserSettings settings = new CsvParserSettings();
//        settings.setHeaderExtractionEnabled(true);
//        settings.setDelimiterDetectionEnabled(true);
//        settings.setLineSeparatorDetectionEnabled(true);
//
//        CsvParser csvParser = new CsvParser(settings);
//        List<Record> parseAllRecords = csvParser.parseAllRecords(inputStream);
//        parseAllRecords.forEach(record -> {
//            PremiacaoEntity premiacao = new PremiacaoEntity();
//            premiacao.setYear(record.getInt("year"));
//            premiacao.setTitle(record.getString("title"));
//            premiacao.setStudios(record.getString("studios"));
//            premiacao.setProducers(record.getString("producers"));
//            premiacao.setWinner(record.getString("winner"));
//            premiacaoList.add(premiacao);
//        });
//
//        premiacaoService.saveAll(premiacaoList);
//        return "Upload Successfull!";
//    }
}
