package com.example.test.demo.service;

import com.example.test.demo.model.Componente;
import com.example.test.demo.model.Docente;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.DependsOn;
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
                Ruan carlos,123.@upt.pt,123456789,0,false
                Ruan carlos2,123.@upt.pt,123456789,0,false
                Ruan carlos3,123.@upt.pt,123456789,1,false
            """)
   @Disabled
    void shouldTestIfSaveDocenteIsSent(String nome, String email, String password, int id, boolean isAdmin) throws ExecutionException, InterruptedException {
        ArrayList<String> ucs = new ArrayList<>();
        ucs.add("UC1");
        ucs.add("UC2");
        ucs.add("UC3");
        Docente docente = new Docente(nome, email, password, id,ucs ,isAdmin);
        String result = myService.saveDocente(docente);
        assertNotNull(result);

    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DependsOn("shouldTestIfSaveDocenteIsSent")
    void shouldTestSaveDocenteIsInDataBase(int id) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        assertNotEquals(0, future.get().getDocuments().size());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DependsOn("shouldTestIfSaveDocenteIsSent")
    void shouldTestSaveDocenteCannotHaveEquals(int id) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        assertTrue(future.get().getDocuments().size() == 1);
    }

    @Test
    @DependsOn("shouldTestIfSaveDocenteIsSent")
    void shouldTestIfGetAlDocentesIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllDocentes());
    }

    @Test
    @DependsOn("shouldTestIfSaveDocenteIsSent")
    void shouldTestIfGetAllDocentesIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllDocentes().size() > 0);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """ 
            0,Ruan carlosChanged0,123.@upt.pt,12345678910,false
            1,Ruan carlosChanged1,1234.@upt.pt,1234567899,false
            """)
    @DependsOn("shouldTestSaveDocenteIsInDataBase")
    void shouldTestIfUpdateDocenteWorks(int id, String nomeDocente, String email, String pass, boolean isAdmin) throws ExecutionException, InterruptedException {
        ArrayList<String> ucs = new ArrayList<>();
        ucs.add("UC1");
        ucs.add("UC2");
        ucs.add("UC3");
        Docente docente = new Docente(nomeDocente, email, pass, id,ucs,isAdmin);
        String isUpDated = myService.updateDocente(docente);
        assertNotNull(isUpDated);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DependsOn("shouldTestSaveDocenteIsInDataBase")
    void shouldDeleteDocenteFromDataBase(int docenteNumber) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deleteDocente(docenteNumber);
        String[] isDeletedArray = isDeleted.split(":");
        assertEquals(String.valueOf(docenteNumber), isDeletedArray[1]);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4})
    void shouldNotdeleteDocente(int docenteNumber) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deleteDocente(docenteNumber);
        String[] isDeletedArray = isDeleted.split(":");
        assertEquals("docente not found", isDeletedArray[0]);
    }
}