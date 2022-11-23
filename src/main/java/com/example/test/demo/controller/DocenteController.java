package com.example.test.demo.controller;

import com.example.test.demo.model.Docente;
import com.example.test.demo.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    DocenteService docenteService;

    @GetMapping("/all")
    public List<Docente> getDocentes() throws Exception {
        return docenteService.getAllDocentes();
    }
    @PostMapping("/create")
    public String createDocente(@RequestBody Docente docente) throws Exception {
        return docenteService.saveDocente(docente);
    }
    @DeleteMapping("/{id}")
    public String deleteDocente(@PathVariable int id) throws Exception {
        return docenteService.deleteDocente(id);
    }
    @PutMapping("/update")
    public String updateDocente(@RequestBody Docente docente) throws Exception {
        return docenteService.updateDocente(docente);
    }


}
