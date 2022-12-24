package com.example.test.demo.service;



import com.example.test.demo.model.Componente;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
class ComponentServiceTest {
    @Autowired
    private ComponentService myService;
    Firestore db;
    private static final String COL_NAME = "component";

    @BeforeEach
    void setUp()/* throws ExecutionException, InterruptedException*/ {
//        Componente componente = new Componente(0,"teste", 1);
//        myService.createComponent(componente);
//        componente = new Componente(1,"teste", 1);
//        myService.createComponent(componente);
        db = FirestoreClient.getFirestore();

    }
    @Test
    @Disabled
    public void shouldTestCreateComponentIsSent() throws ExecutionException, InterruptedException {
        Componente componente = new Componente(0,"teste", 1);
        String result = myService.createComponent(componente);
        assertNotNull(result);
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    @Disabled
    public void shouldTestCreateComponentIsInDataBase(int id) throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("id", id).get();

        assertNotEquals(0, future.get().getDocuments().size());
    }

    @Test
    @Disabled
    public void shouldTestIfGetAllComponentsIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllComponents());
    }
    @Test
    @Disabled
    public void shouldTestIfGetAllComponentsIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllComponents().size() > 0);
    }

    @ParameterizedTest//DEU ME UMA IPIFANIA ANTES DE ME IR DEITAR ISTO DEVE DAR
    @CsvSource(textBlock = """ 
            0,testeChange1,99
            1,testeChange2,99
            """)
    public void updateComponent(int id,String descricaoChange,int quantidadeChange) throws ExecutionException, InterruptedException {
        Componente componente = new Componente(id,descricaoChange, quantidadeChange);
        String isUpDated = myService.updateComponent(componente);
        assertNotNull(isUpDated);
    }


    @ParameterizedTest
    @CsvSource(textBlock = """ 
            0
            1
            """)
    public void deleteComponent(int id) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deleteComponent(id);
        assertNotEquals();
    }

}