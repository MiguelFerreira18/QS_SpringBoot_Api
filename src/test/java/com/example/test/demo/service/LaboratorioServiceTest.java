package com.example.test.demo.service;

import com.example.test.demo.model.Laboratorio;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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


    @ParameterizedTest
    @CsvSource({"1,1","2,1"})
    void testSaveLaboratorio() throws ExecutionException, InterruptedException
    {
        Laboratorio l = new Laboratorio(1,1);
        String result = myService.saveLaboratorio(l);
        assertNotNull(result);
    }

    @Test
    void testSaveLaboratorioDb() throws ExecutionException, InterruptedException
    {
        //Alterar o id no futuro
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId",1).get();
        assertNotEquals(0,future.get().size());
    }

    @ParameterizedTest
    @CsvSource({"1,20","1,30"})
    void testSaveLaboratorioSameId(int id,int ref) throws ExecutionException,InterruptedException
    {
        Laboratorio l = new Laboratorio(id,ref);
        myService.saveLaboratorio(l);
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId",1).get();
        //Se future.get().size() for diferente de 1 significa que criou laboratorios com o mesmo id, o que nao pode acontecer
        assertEquals(1,future.get().size());
    }

    @Test
    void testGetAllLAbs() throws ExecutionException, InterruptedException
    {
        assertNotNull(myService.getAllLabs());
    }

    @Test
    void testGetAllLabsSize() throws ExecutionException, InterruptedException
    {
        assertNotEquals(0,myService.getAllLabs().size());
    }

    @ParameterizedTest
    @CsvSource({"400,5","700,20"})
    void testDeleteLab(int id,int ref) throws ExecutionException, InterruptedException
    {
        myService.saveLaboratorio(new Laboratorio(id,ref));
        assertNotEquals("laboratorio não encontrado",myService.deleteLab(id));

    }

    @Test
    void testDeleteLabInexistent() throws ExecutionException, InterruptedException
    {
        assertEquals("laboratorio não encontrado",myService.deleteLab(-1));
    }

    @Test
    void testUpdateLab() throws ExecutionException, InterruptedException
    {

        //Altera o laboratorio de id 1 para ref 5
        Laboratorio h = new Laboratorio(1,5);
        assertNotEquals("laboratorio não encontrado",myService.updateLab(h));

    }

    @Test
    void testUpdateLabInexistent() throws ExecutionException, InterruptedException
    {
        Laboratorio l = new Laboratorio(-1,-1);
        assertEquals("laboratorio não encontrado",myService.updateLab(l));
    }

    //Fim de testes de caso nao particulares

    // TODO: 26/12/2022 TESTAR SE LIMITES DE CRIAÇÃO DE LABORATORIOS ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE LIMITES DE NA ATUALIZAÇÃO DE LABORATORIOS ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE O ID PODE SER MENOR QUE 0
}