package com.example.test.demo.controller;

import com.example.test.demo.model.Material;
import com.example.test.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class MaterialControler {

    @Autowired
    MaterialService materialService;

    @PostMapping("/createMaterial")
    public String createMaterial(@RequestBody Material mat) throws InterruptedException, ExecutionException {
        return materialService.saveMat(mat);
    }
    @GetMapping("/getMaterial")
    public List<Material> getMaterial() throws ExecutionException, InterruptedException {
        return materialService.getMat();
    }
    @PutMapping("/updateMaterial")
    public String updateMaterial(@RequestBody Material mat)throws ExecutionException, InterruptedException{
        return materialService.updateMat(mat);
    }
    @DeleteMapping("/deleteMaterial")
    public String deleteMaterial(@RequestBody Material mat)throws ExecutionException, InterruptedException{
        return materialService.deleteMat(mat);
    }


}
