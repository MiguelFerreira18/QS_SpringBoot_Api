package com.example.test.demo.service;



import com.example.test.demo.model.Componente;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;


import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComponentServiceTest {
    @Autowired
    private ComponentService myService;
    Firestore db;
    private static final String COL_NAME = "component";

    @BeforeEach
    void setUp()  {
        db = FirestoreClient.getFirestore();
    }

    @Test
    public void shouldTestCreateComponentIsSent() throws ExecutionException, InterruptedException {
        Componente componente = new Componente(0,"teste1", 1);
        String result = myService.createComponent(componente);
        componente = new Componente(0,"teste2", 1);
        myService.createComponent(componente);
        assertNotNull(result);
    }
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void shouldTestCreateComponentIsInDataBase(int id) throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("id", id).get();
        assertNotEquals(0, future.get().getDocuments().size());
    }

    @Test
    public void shouldTestIfGetAllComponentsIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllComponents());
    }
    @Test
    public void shouldTestIfGetAllComponentsIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllComponents().size() > 0);
    }

    @ParameterizedTest//DEU ME UMA IPIFANIA ANTES DE ME IR DEITAR ISTO DEVE DAR
    @CsvSource(textBlock = """ 
            0,testeChange1,99
            1,testeChange2,99
            """)
    public void shouldTestIfUpdateComponentWorks(int id,String descricaoChange,int quantidadeChange) throws ExecutionException, InterruptedException {
        Componente componente = new Componente(id,descricaoChange, quantidadeChange);
        String isUpDated = myService.updateComponent(componente);
        assertNotNull(isUpDated);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """ 
            0
            1
            """)
    public void shouldTestIfDeleteComponentIsWorking(int id) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deleteComponent(id);
        assertEquals(id, Integer.parseInt(isDeleted));
    }

}