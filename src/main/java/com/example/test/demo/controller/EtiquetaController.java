package com.example.test.demo.controller;


import com.example.test.demo.model.EtiquetaMaterial;
import com.example.test.demo.service.EtiquetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestMapping("/etiqueta")
public class EtiquetaController {
    @Autowired
    private EtiquetaService etiquetaService;

    @GetMapping("/all")
    public List<EtiquetaMaterial> getEtiqueta() throws Exception {
        return etiquetaService.getAllEtiquetas();
    }
    @PostMapping("/create")
    public String createEtiqueta(@RequestBody EtiquetaMaterial etiqueta) throws Exception {
        return etiquetaService.saveEtiqueta(etiqueta);
    }
    @DeleteMapping("/{id}")
    public String deleteEtiqueta(@PathVariable int id) throws Exception {
        return etiquetaService.deleteEtiqueta(id);
    }
    @PutMapping("/update")
    public String updateEtiqueta(@RequestBody EtiquetaMaterial etiqueta) throws Exception {
        return etiquetaService.updateEtiqueta(etiqueta);
    }

}
