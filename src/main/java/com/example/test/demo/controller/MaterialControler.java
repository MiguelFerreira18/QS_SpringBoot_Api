package com.example.test.demo.controller;

import com.example.test.demo.model.Material;
import com.example.test.demo.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class MaterialControler {

    @Autowired
    MaterialService materialService;

    @PostMapping("/createMaterial")
    public String createMaterial(@RequestBody Material mat) throws InterruptedException, ExecutionException {
        return materialService.saveMat(mat);
    }


}
