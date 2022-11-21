package com.example.test.demo.controller;


import com.example.test.demo.model.EtiquetaMaterial;
import com.example.test.demo.service.EtiquetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EtiquetaController {
    @Autowired
    EtiquetaService etiquetaService;

    @GetMapping("/getEtiqueta")
    public List<EtiquetaMaterial> getEtiqueta() throws Exception {
        return etiquetaService.getAllEtiquetas();
    }
    @PostMapping("/createEtiqueta")
    public String createEtiqueta(@RequestBody EtiquetaMaterial etiqueta) throws Exception {
        return etiquetaService.saveEtiqueta(etiqueta);
    }
    @DeleteMapping("/deleteEtiqueta")
    public String deleteEtiqueta(@RequestBody EtiquetaMaterial etiqueta) throws Exception {
        return etiquetaService.deleteEtiqueta(etiqueta);
    }
    @PutMapping("/updateEtiqueta")
    public String updateEtiqueta(@RequestBody EtiquetaMaterial etiqueta) throws Exception {
        return etiquetaService.updateEtiqueta(etiqueta);
    }

}
