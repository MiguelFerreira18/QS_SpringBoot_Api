package com.example.test.demo.service;

import com.example.test.demo.model.Laboratorio;
import com.example.test.demo.model.Material;
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
class MaterialServiceTest
{

    private Firestore db;
    private String COL_NAME = "material";

    @Autowired
    private MaterialService myService;

    @BeforeEach
    void startDB()
    {
        db = FirestoreClient.getFirestore();
    }
    @ParameterizedTest
    @CsvSource({"25/12/22,carrinho,true,false,1,1","26/12/22,helicoptero,false,true,2,2"})
    void testSaveMat(String dataEntrega, String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId) throws ExecutionException, InterruptedException
    {
        //Criei 2 materiais com id 1
        Material m = new Material(dataEntrega,descricao,isDisponivel,isAvariado,materialId,etiquetaId);
        String result = myService.saveMat(m);
        assertNotNull(result);
    }

    @Test
    void testSaveMatDb() throws ExecutionException, InterruptedException
    {

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId",1).get();
        assertNotEquals(0,future.get().size());
    }

    @ParameterizedTest
    @CsvSource({"28/12/22,carrinho,false,false,0,6","29/12/22,carrinho,true,false,0,6"})
    void testSaveMatSameId(String dataEntrega, String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId) throws ExecutionException,InterruptedException
    {
        Material m = new Material(dataEntrega,descricao,isDisponivel,isAvariado,materialId,etiquetaId);
        myService.saveMat(m);

        //A base de dados ja contem 2 objetos do tipo material inseridos anteriormente
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId",0).get();
        //Se future.get().size() for diferente de 1 significa que criou materiais com o mesmo id, o que nao pode acontecer
        assertEquals(1,future.get().size());
    }

    @Test
    void testDeleteMat() throws ExecutionException, InterruptedException
    {
        Material m = new Material("10/10/22","drone",true,false,0,6);
        myService.saveMat(m);
        assertNotEquals("Material não encontrado para ser eleminado", myService.deleteMat(0));
    }

    @Test
    void testDeleteMatInexistent() throws ExecutionException, InterruptedException
    {
        assertEquals("Material não encontrado para ser eleminado", myService.deleteMat(-1));
    }

    @Test
    void testUpdateMat() throws ExecutionException, InterruptedException
    {
        Material m = new Material("10/10/22","drone",true,false,0,6);
        myService.saveMat(m);

        Material h = new Material("10/10/22","droneA",false,true,0,6);
        assertNotEquals("No elements to be queried",myService.updateMat(h));
    }

    @Test
    void testUpdateMatInexistent() throws ExecutionException, InterruptedException
    {
        Material m = new Material("10/10/22","drone",true,false,-20,6);
        assertEquals("No elements to be queried",myService.updateMat(m));
    }

    @Test
    void testGetAllMats() throws ExecutionException, InterruptedException
    {
        assertNotNull(myService.getAllMAts());
    }

    @Test
    void testGetAllMatsSize() throws ExecutionException, InterruptedException
    {
        assertNotEquals(0,myService.getAllMAts());
    }


}