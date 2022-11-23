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

    @GetMapping("/create")
    public String createLaboratorio(@RequestBody Laboratorio lab) throws Exception {
        return laboratorioService.saveLaboratorio(lab);
    }
    @GetMapping("/all")
    public List<Laboratorio> deleteLaboratorio() throws Exception {
        return laboratorioService.getAllLabs();
    }
    @PutMapping("/update")
    public String updateLaboratorio(@RequestBody Laboratorio lab) throws Exception {
        return laboratorioService.updateLab(lab);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteLaboratorio(@PathVariable int id) throws Exception {
        return laboratorioService.deleteLab(id);
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

}
