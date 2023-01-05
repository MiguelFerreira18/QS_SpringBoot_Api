package com.example.test.demo.service;

import com.example.test.demo.model.Laboratorio;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @CsvSource({"0,10","1,10","2,10"})
    @Order(1)
    @DisplayName("Testa se e possivel criar um laboratorio")
    void testCreateNormalLaboratorio(int id,int ref) throws ExecutionException, InterruptedException
    {
        Laboratorio l = new Laboratorio(id,ref);
        String result = myService.createLaboratorio(l);
        assertEquals("laboratorio created",result);
    }

    @ParameterizedTest
    @CsvSource({"0,-1","1,99999"})
    @Order(2)
    @DisplayName("Testa se e possivel criar um laboratorio com refAdmin inexistente na base de dados")
    void testCreateLaboratorioRefAdminInexistent(int id,int ref) throws ExecutionException, InterruptedException
    {
        Laboratorio l = new Laboratorio(id,ref);
        String result = myService.createLaboratorio(l);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"-1,0","-3,2"})
    @Order(3)
    @DisplayName("Testa se e possivel criar um laboratorio com id abaixo de zero")
    void testCreateLaboratorioIdLowerThanZero(int id,int ref) throws ExecutionException, InterruptedException
    {
        Laboratorio l = new Laboratorio(id,ref);
        String result = myService.createLaboratorio(l);
        assertNull(result);
    }


    @ParameterizedTest
    @CsvSource({"0","1","2"})
    @Order(4)
    @DisplayName("Verifica se os laboratorios foram guardados na base de dados")
    void testCreateLaboratorioSavedInDb(int id) throws ExecutionException, InterruptedException
    {
        //Alterar o id no futuro
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId",id).get();
        assertNotEquals(0,future.get().size());
    }

    @ParameterizedTest
    @CsvSource({"1,0","1,1"})
    @Order(5)
    @DisplayName("Verifica se e possivel criar laboratorios com o mesmo id")
    void testCreateLaboratorioSameId(int id,int ref) throws ExecutionException,InterruptedException
    {
        Laboratorio l = new Laboratorio(id,ref);
        myService.createLaboratorio(l);
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId",id).get();
        //Se future.get().size() for diferente de 1 significa que criou laboratorios com o mesmo id, o que nao pode acontecer
        assertEquals(1,future.get().size());
    }

    @Test
    @Order(6)
    @DisplayName("Testa se existe laboratorios, se nao existir laboratorios retorna null")
    void testGetAllLAbs() throws ExecutionException, InterruptedException
    {
        assertNotNull(myService.getAllLaboratorios());
    }

    @Test
    @Order(7)
    @DisplayName("Verifica se o tamanho da lista e superior a 0,presumindo que os valores ja foram inseridos")
    void testGetAllLabsSize() throws ExecutionException, InterruptedException
    {
        assertNotEquals(0,myService.getAllLaboratorios().size());
    }

    @ParameterizedTest
    @CsvSource({"0,10","1,10","2,10"})
    @Order(8)
    @DisplayName("Testa se e possivel atualizar um laboratorio existente na base de dados")
    void testUpdateLabExistent(int id,int ref) throws ExecutionException, InterruptedException
    {
        Laboratorio h = new Laboratorio(id,ref);
        assertEquals("laboratorio updated with:" + id,myService.updateLaboratorio(h));
    }

    @ParameterizedTest
    @CsvSource({"-1,1","-2,2","9999999,3"})
    @Order(9)
    @DisplayName("Testa se e possivel atualizar um laboratorio inexistente na base de dados")
    void testUpdateLabInexistent(int id,int ref) throws ExecutionException, InterruptedException
    {
        Laboratorio l = new Laboratorio(id,ref);
        assertNull(myService.updateLaboratorio(l));
    }

    @ParameterizedTest
    @CsvSource({"0,-1","1,-2","2,99999999"})
    @Order(10)
    @DisplayName("Testa se e possivel atualizar um laboratorio existente na base de dados mas com refAdmin inexistente na bd")
    void testUpdateLabRefAdminInexistent(int id,int ref) throws ExecutionException, InterruptedException
    {
        Laboratorio h = new Laboratorio(id,ref);
        assertNull(myService.updateLaboratorio(h));
    }

    @ParameterizedTest
    @CsvSource({"0","1","2"})
    @Order(11)
    @DisplayName("Testa se e possivel eliminar um laboratorio existente na base de dados")
    void testDeleteLabExistent(int id) throws ExecutionException, InterruptedException
    {
        assertEquals("laboratorio deleted with:" + id,myService.deleteLaboratorio(id));
    }

    @ParameterizedTest
    @CsvSource({"-1","-2","999999"})
    @Order(12)
    @DisplayName("Testa se e possivel eliminar um laboratorio inexistente na base de dados")
    void testDeleteLabInexistent(int id) throws ExecutionException, InterruptedException
    {
        assertNull(myService.deleteLaboratorio(id));
    }



    //Fim de testes de caso nao particulares

    // TODO: 26/12/2022 TESTAR SE LIMITES DE CRIAÇÃO DE LABORATORIOS ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE LIMITES DE NA ATUALIZAÇÃO DE LABORATORIOS ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE O ID PODE SER MENOR QUE 0
}