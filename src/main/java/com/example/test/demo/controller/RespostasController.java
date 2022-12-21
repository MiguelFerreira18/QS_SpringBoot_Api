package com.example.test.demo.controller;

import com.example.test.demo.model.Resposta;
import com.example.test.demo.model.RespostaLaboratorio;
import com.example.test.demo.model.RespostaMaterial;
import com.example.test.demo.model.RespostaUtilizador;
import com.example.test.demo.service.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resposta")
public class RespostasController {

    @Autowired
    private RespostaService respostaService;

    /*RESPOSTA LABORATORIO*/


    /*@GetMapping("/all")
    public String getRespostasLaboratorio() throws Exception {
        return respostaService.getAllRespostasLaboratorio().toString();
    }*/
    @PostMapping("/createRespostaLaboratorio")
    public String createRespostaLaboratorio(@RequestBody RespostaLaboratorio resposta) throws Exception {
        return respostaService.createRespostaLaboratorio(resposta);
    }
    @PutMapping("/updateRespostaLaboratorio")
    public String updateRespostaLaboratorio(@RequestBody RespostaLaboratorio resposta) throws Exception {
        return respostaService.updateRespostaLaboratorio(resposta);
    }

    /*RESPOSTA MATERIAL*/
   /* @GetMapping("/all")
    public String getRespostasMaterial() throws Exception {
        return respostaService.getAllRespostasMaterial().toString();
    }*/
    @PostMapping("/createRespostaMaterial")
    public String createRespostaMaterial(@RequestBody RespostaMaterial resposta) throws Exception {
        return respostaService.createRespostaMaterial(resposta);
    }
    @PutMapping("/updateRespostaMaterial")
    public String updateRespostaMaterial(@RequestBody RespostaMaterial resposta) throws Exception {
        return respostaService.updateRespostaMaterial(resposta);
    }

    /*RESPOSTA UTILIZADOR*/

  /*  @GetMapping("/allRespostasUtilizador")
    public String getRespostasUtilizador() throws Exception {
        return respostaService.getAllRespostasUtilizador().toString();
    }*/
    @PostMapping("/createRespostaUtilizador")
    public String createRespostaUtilizador(@RequestBody RespostaUtilizador resposta) throws Exception {
        return respostaService.createRespostaUtilizador(resposta);
    }
    @PutMapping("/updateRespostaUtilizador")
    public String updateRespostaUtilizador(@RequestBody RespostaUtilizador resposta) throws Exception {
        return respostaService.updateRespostaUtilizador(resposta);
    }

    /*GLOBALS*/

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
