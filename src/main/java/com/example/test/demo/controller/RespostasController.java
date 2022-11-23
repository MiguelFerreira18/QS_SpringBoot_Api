package com.example.test.demo.controller;

import com.example.test.demo.model.Resposta;
import com.example.test.demo.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resposta")
public class RespostasController {

    @Autowired
    private RespostaService respostaService;

    @GetMapping("/all")
    public String getRespostas() throws Exception {
        return respostaService.getAllRespostas().toString();
    }
    @PostMapping("/create")
    public String createResposta(@RequestBody Resposta resposta) throws Exception {
        return respostaService.createResposta(resposta);
    }
    @PutMapping("/update")
    public String updateResposta(@RequestBody Resposta resposta) throws Exception {
        return respostaService.updateResposta(resposta);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteResposta(@PathVariable int id) throws Exception {
        return respostaService.deleteResposta(id);
    }
    /*CASOS PARTICULARES*/
    @PostMapping("/createMaterialResposta/{id}")
    public String createQuestaoResposta(@PathVariable int id, @RequestBody int resposta) throws Exception {
        return respostaService.addMaterialToResposta(id, resposta);
    }
    @DeleteMapping("/deleteMaterialResposta/{id}")
    public String deleteQuestaoResposta(@PathVariable int id, @RequestBody int resposta) throws Exception {
        return respostaService.deleteMaterialFromResposta(id, resposta);
    }
    @GetMapping("/getMateriaisResposta/{id}")
    public List<Integer> getMateriaisResposta(@PathVariable int id) throws Exception {
        return respostaService.getMateriaisFromResposta(id);
    }



}
