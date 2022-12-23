package com.example.test.demo.controller;


import com.example.test.demo.model.Componente;
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
    @DeleteMapping("/delete/{id}")
    public String deleteEtiqueta(@PathVariable int id) throws Exception {
        return etiquetaService.deleteEtiqueta(id);
    }
    @PutMapping("/update")
    public String updateEtiqueta(@RequestBody EtiquetaMaterial etiqueta) throws Exception {
        return etiquetaService.updateEtiqueta(etiqueta);
    }
    /*CASOS PARTICULARES*/

    @PostMapping("/createComponenteEtiqueta/{id}")
    public String createComponentesEtiqueta(@RequestBody int componente, @PathVariable int id) throws Exception {
        return etiquetaService.addComponente(id , componente);
    }
    @DeleteMapping("/deleteComponenteEtiqueta/{id}")
    public String deleteComponentesEtiqueta(@RequestBody int componente, @PathVariable int id) throws Exception {
        return etiquetaService.deleteComponente(id , componente);
    }
    @GetMapping("/getComponentesEtiqueta/{id}")
    public List<Integer> getComponentesEtiqueta(@PathVariable int id) throws Exception {
        return etiquetaService.getAllComponentes(id);
    }
    @PostMapping("/createMaterialEtiqueta/{id}")
    public String createMateriaisEtiqueta(@RequestBody int idMaterial, @PathVariable int idEtiqueta) throws Exception {
        return etiquetaService.addMaterialToEtiqueta(idEtiqueta , idMaterial);
    }
    @DeleteMapping("/deleteMaterialEtiqueta/{id}")
    public String deleteMateriaisEtiqueta(@RequestBody int idMaterial, @PathVariable int idEtiqueta) throws Exception {
        return etiquetaService.deleteMaterialFromEtiqueta(idEtiqueta , idMaterial);
    }
    @GetMapping("/getMateriaisEtiqueta/{id}")
    public List<Integer> getMateriaisEtiqueta(@PathVariable int idEtiqueta) throws Exception {
        return etiquetaService.getAllMateriaisFromEtiqueta(idEtiqueta);
    }


}
