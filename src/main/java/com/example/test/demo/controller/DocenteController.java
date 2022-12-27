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

//    @GetMapping("/TESTING")
//    public String requestTest(@RequestParam String password) {
//       return docenteService.requestTest(password);
//    }



    @GetMapping("/all")
    public List<Docente> getDocentes() throws Exception {
        return docenteService.getAllDocentes();
    }

    @PostMapping("/create")
    public String createDocente(@RequestBody Docente docente) throws Exception {
        return docenteService.createDocentes(docente);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDocente(@PathVariable int id) throws Exception {
        return docenteService.deleteDocente(id);
    }

    @PutMapping("/update")
    public String updateDocente(@RequestBody Docente docente) throws Exception {
        return docenteService.updateDocente(docente);
    }
    /*CASOS PARTICULARES*/

    @PostMapping("/createUcDocente/{id}")
    public String vreateUcFromDocente(@RequestBody String uc, @PathVariable int id) throws Exception {
        return docenteService.addUcToDocente(id, uc);
    }
    @PutMapping("/updateUcDocente/{id}")
    public String updateUcFromDocente(@RequestBody String uc, @PathVariable int id) throws Exception {
        return docenteService.updateUcFromDocente(id, uc);
    }

    @DeleteMapping("/deleteUcDocente/{id}")
    public String deleteUcFromDocente(@RequestBody String uc, @PathVariable int id) throws Exception {
        return docenteService.deleteUcFromDocente(id, uc);
    }
    @GetMapping("/ucs/{id}")
    public List<String> getDocentesByUcs(@PathVariable int id) throws Exception {
        return docenteService.getAllUcFromDocente(id);
    }

    /*!AUTH REQUEST!*/
    @GetMapping("/auth")
    public Docente authDocente(@RequestBody int numeroDocente, @RequestBody String password) throws Exception {
        return docenteService.loginDocente(numeroDocente, password);
    }


}
