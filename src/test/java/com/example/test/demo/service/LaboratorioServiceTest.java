package com.example.test.demo.service;

import com.example.test.demo.model.Laboratorio;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LaboratorioServiceTest {
    private Firestore db;
    private String COL_NAME = "laboratorio";
    @Autowired
    private LaboratorioService myService;


    @BeforeEach
    void startDB()
    {
        db = FirestoreClient.getFirestore();
    }

    /**
     * Teste para verificar se um laboratorio esta a ser criado
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    void testSaveLaboratorio() throws ExecutionException, InterruptedException
    {
        Laboratorio l = new Laboratorio(1,1);
        String result = myService.saveLaboratorio(l);
        assertNotNull(result);
    }

    @Test
    void testSaveLaboratorioDb() throws ExecutionException, InterruptedException
    {
        int id = 1;
        Laboratorio l = new Laboratorio(id,1);
        myService.saveLaboratorio(l);
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioID",id).get();
        assertNotEquals(0,future.get().size());
    }

}