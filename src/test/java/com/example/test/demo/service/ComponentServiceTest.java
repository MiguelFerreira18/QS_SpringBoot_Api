package com.example.test.demo.service;


import com.example.test.demo.controller.MaterialControler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;


import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class ComponentServiceTest {
    @Autowired
    private ComponentService myService;

    @Test
    void test() {
        assertEquals(1, 1);

    }

}