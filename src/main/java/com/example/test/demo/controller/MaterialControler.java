package com.example.test.demo.controller;

import com.example.test.demo.model.Material;
import com.example.test.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/material")
public class MaterialControler {
//EXEMPLO DE REQUEST USANDO O LOCAL HOST 'http://localhost:8080/material/all'
    @Autowired
   private MaterialService materialService;

    @PostMapping("/create/{idLab}")
    public String createMaterial(@RequestBody Material mat,@PathVariable int idLab) throws InterruptedException, ExecutionException {
        return materialService.createMaterial(mat,idLab);
    }
    @GetMapping("/all")
    public List<Material> getMaterial() throws ExecutionException, InterruptedException {
        return materialService.getAllMateriais();
    }
    @PutMapping("/update")
    public String updateMaterial(@RequestBody Material mat)throws ExecutionException, InterruptedException{
        return materialService.updateMat(mat);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteMaterial(@PathVariable int id)throws ExecutionException, InterruptedException{
        return materialService.deleteMateriais(id);
    }
    /*CASOS PARTICULARES*/

    @PostMapping("/createRespostaMaterial/{id}")
    public String createRespostaMaterial(@PathVariable int id,@RequestBody int matId) throws InterruptedException, ExecutionException {
        return materialService.createRespostaToMaterial(id,matId);
    }
    @PostMapping("/updateRespostaMaterial/{id}")
    public String updateRespostaMaterial(@PathVariable int id,@RequestBody int matId) throws InterruptedException, ExecutionException {
        return materialService.updateRespostaToMaterial(id,matId);
    }
    @PostMapping("/createUcNoMaterial/{uc}")
    public String createUcNoMaterial(@PathVariable int matId, @RequestBody String ucNome) throws InterruptedException, ExecutionException {
        return materialService.createUcToMaterial(ucNome,matId);
    }
    @PostMapping("/updateUcNoMaterial/{uc}")
    public String updateUcNoMaterial(@PathVariable int matId, @RequestBody String ucNome) throws InterruptedException, ExecutionException {
        return materialService.updateUcToMaterial(ucNome,matId);
    }
    @PostMapping("/deleteUcNoMAterial/{tipo}")
    public String deleteUcNoMaterial(@PathVariable int matId, @RequestBody String ucNome) throws InterruptedException, ExecutionException {
        return materialService.deleteUcToMaterial(ucNome,matId);
    }

}
