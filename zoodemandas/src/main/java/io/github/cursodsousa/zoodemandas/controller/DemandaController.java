package io.github.cursodsousa.zoodemandas.controller;

import io.github.cursodsousa.zoodemandas.model.Demanda;
import io.github.cursodsousa.zoodemandas.repository.DemandaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

// CrossOrigin: permite que o HTML aberto direto do disco (outra origem) chame a API.
// O "*" serve apenas para desenvolvimento local.
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("demandas")
public class DemandaController {

    private final DemandaRepository demandaRepository;

    public DemandaController(DemandaRepository demandaRepository) {
        this.demandaRepository = demandaRepository;
    }

    @PostMapping
    public Demanda salvar(@RequestBody Demanda demanda) {
        System.out.println("Demanda recebida: " + demanda);

        demanda.setId(UUID.randomUUID().toString());

        demandaRepository.save(demanda);
        return demanda;
    }

    @GetMapping
    public List<Demanda> listar() {
        return demandaRepository.findAll();
    }
}
