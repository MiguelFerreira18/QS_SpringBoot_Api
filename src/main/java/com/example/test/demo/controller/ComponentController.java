package com.example.test.demo.controller;

import com.example.test.demo.model.Componente;
import com.example.test.demo.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/componente")
public class ComponentController {

    @Autowired
    ComponentService componentService;

    @GetMapping("/all")
    public List<Componente> getComponentes() throws Exception {
        return componentService.getAllComponents();
    }
    @PostMapping("/create/{id}")
    public String createComponent(@RequestBody Componente componente,@PathVariable int id) throws Exception {
        return componentService.createComponent(componente,id);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteComponent(@PathVariable int id) throws Exception {
        return componentService.deleteComponent(id);
    }
    @PutMapping("/update")
    public String updateComponent(@RequestBody Componente componente) throws Exception {
        return componentService.updateComponent(componente);
    }


}
