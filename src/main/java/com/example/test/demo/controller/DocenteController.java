package com.example.test.demo.controller;

import com.example.test.demo.model.Docente;
import com.example.test.demo.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DocenteController {

    @Autowired
    DocenteService docenteService;

    @GetMapping("/getDocentes")
    public List<Docente> getDocentes() throws Exception {
        return docenteService.getAllDocentes();
    }
    @PostMapping("/createDocente")
    public String createDocente(@RequestBody Docente docente) throws Exception {
        return docenteService.saveDocente(docente);
    }
    @PostMapping("/deleteDocente")
    public String deleteDocente(@RequestBody Docente docente) throws Exception {
        return docenteService.deleteDocente(docente);
    }
    @PostMapping("/updateDocente")
    public String updateDocente(@RequestBody Docente docente) throws Exception {
        return docenteService.updateDocente(docente);
    }


}
