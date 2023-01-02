package com.example.test.demo.controller;

import com.example.test.demo.model.Laboratorio;
import com.example.test.demo.service.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratorio")
public class LaboratorioController {

    @Autowired
    LaboratorioService laboratorioService;

    @PostMapping("/create")
    public String createLaboratorio(@RequestBody Laboratorio lab) throws Exception {
        return laboratorioService.createLaboratorio(lab);
    }
    @GetMapping("/all")
    public List<Laboratorio> getLaboratorio() throws Exception {
        return laboratorioService.getAllLaboratorios();
    }
    @PutMapping("/update")
    public String updateLaboratorio(@RequestBody Laboratorio lab) throws Exception {
        return laboratorioService.updateLaboratorio(lab);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteLaboratorio(@PathVariable int id) throws Exception {
        return laboratorioService.deleteLaboratorio(id);
    }
    /*CASOS PARTICULARES*/

    @PostMapping("/createMaterialLab/{id}")
    public String createMaterialLab(@PathVariable int id, @RequestBody int lab) throws Exception {
        return laboratorioService.addMaterialToLab(id, lab);
    }
    @DeleteMapping("/deleteMaterialLab/{id}")
    public String deleteMaterialLab(@PathVariable int id, @RequestBody int lab) throws Exception {
        return laboratorioService.deleteMaterialFromLab(id, lab);
    }
    @GetMapping("/getMateriaisLab/{id}")
    public List<Integer> getMateriaisLab(@PathVariable int id) throws Exception {
        return laboratorioService.getMateriaisFromLab(id);
    }


    /*ADICIONAR OUTRO CASO*/

    @PostMapping("/createRespostaLaboratorio/{idLab}")
    public String createRespostaLaboratorio(@PathVariable int idLab, @RequestBody int idMaterial) throws Exception {
        return laboratorioService.addRespostaLaboratorio(idLab, idMaterial);
    }
    @PutMapping("/updateRespostaLaboratorio/{idLab}")
    public String updateRespostaLaboratorio(@PathVariable int idLab, @RequestBody int idMaterial) throws Exception {
        return laboratorioService.updateRespostaLaboratório(idLab, idMaterial);
    }


}
