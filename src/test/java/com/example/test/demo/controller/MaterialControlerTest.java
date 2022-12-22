package com.example.test.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.test.demo.model.Material;
import com.example.test.demo.service.MaterialService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import com.example.test.demo.service.MaterialService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


@ExtendWith(SpringExtension.class)
@WebMvcTest(MaterialControler.class)
class MaterialControlerTest {
    @MockBean
    MaterialService materialService;
    @Autowired
    MockMvc mockitoMvc;


    @Test
    void getMaterial() throws Exception {
        //test if the method getMaterial is working
        Material mat=new Material();
        mat.setMaterialId(1);
        mat.setDescricao("cenas");
        List<Material> allMaterial= Arrays.asList(mat);
        Mockito.when(materialService.getMat()).thenReturn(allMaterial);
        mockitoMvc.perform(get("/material/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("cenas"));
    }
}