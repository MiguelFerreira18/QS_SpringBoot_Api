package com.example.test.demo.controller;

import com.example.test.demo.model.Laboratorio;
import com.example.test.demo.service.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LaboratorioController {

    @Autowired
    LaboratorioService laboratorioService;

    public String createLaboratorio(@RequestBody Laboratorio lab) throws Exception {
        return laboratorioService.saveLaboratorio(lab);
    }
    public List<Laboratorio> deleteLaboratorio() throws Exception {
        return laboratorioService.getAllLabs();
    }
    public String updateLaboratorio(@RequestBody Laboratorio lab) throws Exception {
        return laboratorioService.updateLab(lab);
    }
    public String deleteLaboratorio(@RequestBody Laboratorio lab) throws Exception {
        return laboratorioService.deleteLab(lab);
    }

}
