package com.example.test.demo.service;

import com.example.test.demo.model.Docente;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DocenteServiceTest {

    @Autowired
    private DocenteService myService;
    Firestore db;
    private static final String COL_NAME = "docente";

    @BeforeEach
    void setUp() {
        db = FirestoreClient.getFirestore();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                Ruan carlosChanged6,asd@upt.pt,SD_@6789F,0,true
                Ruan carlosChanged7,asd@upt.pt,SD_@6789F,1,true
                Ruan carlosChanged8,asd@upt.pt,SD_@6789F,2,false
                Ruan carlosChanged9,asd@upt.pt,SD_@6789F,3,false
                Ruan carlosChanged10,asd@upt.pt,SD_@6789F,4,true
            """)
    @Order(1)
    @DisplayName("Deve testar se armazena um docente na base de dados")
    void shouldTestIfSaveDocenteIsSent(String nome, String email, String password, int id, boolean isAdmin) throws ExecutionException, InterruptedException {
        ArrayList<String> ucs = new ArrayList<>();
        ucs.add("UC1");
        ucs.add("UC2");
        ucs.add("UC3");
        Docente docente = new Docente(nome, email, password, id, ucs, isAdmin);
        String result = myService.createDocentes(docente);
        assertNotNull(result);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @Order(2)
    @DisplayName("Deve testar se o docente está na base de dados")
    void shouldTestSaveDocenteIsInDataBase(int id) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        assertNotEquals(0, future.get().getDocuments().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @Order(3)
    @DisplayName("Deve testar se o docente é unico na base de dados")
    void shouldTestSaveDocenteCannotHaveEquals(int id) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        assertTrue(future.get().getDocuments().size() == 1);
    }

    @Test
    @Order(4)
    @DisplayName("Deve testar se a lista de docentes está nulla")
    void shouldTestIfGetAlDocentesIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllDocentes());
    }

    @Test
    @Order(5)
    @DisplayName("Deve testar se a lista de docentes não é menor que 0")
    void shouldTestIfGetAllDocentesIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllDocentes().size() > 0);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """ 
            0,Ruan carlosChanged0,123.@upt.pt,12345678910,false
            1,Ruan carlosChanged1,1234.@upt.pt,1234567899,false
            """)
    @Order(6)
    @DisplayName("Deve testar se o docente foi atualizado na base de dados")
    void shouldTestIfUpdateDocenteWorks(int id, String nomeDocente, String email, String pass, boolean isAdmin) throws ExecutionException, InterruptedException {
        ArrayList<String> ucs = new ArrayList<>();
        ucs.add("UC1");
        ucs.add("UC2");
        ucs.add("UC3");
        Docente docente = new Docente(nomeDocente, email, pass, id, ucs, isAdmin);
        String isUpDated = myService.updateDocente(docente);
        assertNotNull(isUpDated);
    }

    @ParameterizedTest
    @ValueSource(ints = {19, 12,13,14,99})
    @Order(7)
    @DisplayName("Deve testar se o docente foi apagado da base de dados")
    void shouldDeleteDocenteFromDataBase(int docenteNumber) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deleteDocente(docenteNumber);
        String[] isDeletedArray = isDeleted.split(":");
        assertEquals(String.valueOf(docenteNumber), isDeletedArray[1]);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4,123,123,124,345,2,123,129012490,-3124124,-124123})
    @Order(8)
    @DisplayName("Deve testar se o docente que não existe retorna a mensagem correta")
    void shouldNotdeleteDocente(int docenteNumber) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deleteDocente(docenteNumber);
        assertNull(isDeleted);
    }

    // TODO: 26/12/2022 TESTAR SE LIMITES DE CRIAÇÃO DE DOCENTES ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE LIMITES DE NA ATUALIZAÇÃO DE DOCENTES ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE O ID PODE SER MENOR QUE 0

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,null,null,null,false
            1,"",1234.@upt.pt,1234567899,false
            2,Ruan carlosChanged2,1234.upt.pt,null,false
            3,Ruan carlosChanged3,1234.@uptpt,1234567899,false
            4,Ruan carlosChanged4,1234.@upt.pt,"",false
            5,Ruan carlosChanged5,1234.@upt.pt,askjdbnsdjkbnfadjsnbvcxjknabsdhbjjk,false
            6,Ruan carlosChanged6,@upt.pt,SD_@6789F,true
            """)
    @Order(9)
    @DisplayName("Deve testar se os limites de inserção para a base de dados estão a funcionar")
    void shouldTestIfLimitsAreWorking(int id, String nomeDocente, String email, String pass, boolean isAdmin) throws ExecutionException, InterruptedException {
        ArrayList<String> ucs = new ArrayList<>();
        ucs.add("UC1");
        ucs.add("UC2");
        ucs.add("UC3");
        Docente docente = new Docente(nomeDocente, email, pass, id, ucs, isAdmin);
        String isUpDated = myService.updateDocente(docente);
        assertNull(isUpDated);
    }


    @Test
    void getStatus() {
        myService.getStatus();
    }
}