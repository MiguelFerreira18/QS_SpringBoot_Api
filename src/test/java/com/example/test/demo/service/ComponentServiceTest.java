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
    void setUp() {
        db = FirestoreClient.getFirestore();
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,teste1,1
            1,teste2,2
            2,teste3,3
            3,teste4,4
            4,teste5,5
            """)
    @Order(1)
    @DisplayName("Deve testar se um componente é criado na base de dados")
    public void shouldTestCreateComponentIsSent(int id,String descricao,int quantidade) throws ExecutionException, InterruptedException {
        Componente componente = new Componente(id, descricao, quantidade);
        String result = myService.createComponent(componente);
        assertNotNull(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @Order(2)
    @DisplayName("Deve testar se um componente é encontrado na base de dados")
    public void shouldTestCreateComponentIsInDataBase(int id) throws ExecutionException, InterruptedException {

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("id", id).get();
        assertNotEquals(0, future.get().getDocuments().size());
    }

    @Test
    @Order(3)
    @DisplayName("Deve testar se a lista de componentes não é nula na base de dados")
    public void shouldTestIfGetAllComponentsIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllComponents());
    }

    @Test
    @Order(4)
    @DisplayName("Deve testar se a lista de componentes não é menor que 0")
    public void shouldTestIfGetAllComponentsIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllComponents().size() > 0);
    }

    @ParameterizedTest//DEU ME UMA IPIFANIA ANTES DE ME IR DEITAR ISTO DEVE DAR
    @CsvSource(textBlock = """ 
            0,testeChange1,99
            1,testeChange2,99
            """)
    @Order(5)
    @DisplayName("Deve testar se um componente é alterado na base de dados")
    public void shouldTestIfUpdateComponentWorks(int id, String descricaoChange, int quantidadeChange) throws ExecutionException, InterruptedException {
        Componente componente = new Componente(id, descricaoChange, quantidadeChange);
        String isUpDated = myService.updateComponent(componente);
        assertNotNull(isUpDated);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """ 
            0
            1
            """)
    @Order(6)
    @DisplayName("Deve testar se um componente é apagado na base de dados")
    public void shouldTestIfDeleteComponentIsWorking(int id) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deleteComponent(id);
        assertEquals("deleted component: " + id, isDeleted);
    }

    /*TESTES PARA REBENTAR COM ISTO TUDO*/


    @ParameterizedTest
    @DisplayName("Deve testar todos os limites da inserção de componentes estão a funcionar")
    @CsvSource(textBlock = """ 
            -1,tasdfkjadfjkabdjknajnkdfanjkdajkndnjkadjknadjnkajndkasdasdasdasdasdadjknfjkdsnfjknajksfnjkngfbhdvmn cxvjhbashjbdszxcjknjkcjknawshbndbhadjnajkdnajkndajkndajkndnjkadnjkadnjkadnkjdjnkadjkna,0
            -12414,null,101
            0,teste,-123
            0,null,999
            """)
    @Order(7)
    void shouldTestAllLimitsOfCreateComponent(int id, String descricao, int quantidade) throws ExecutionException, InterruptedException {
        Componente componente = new Componente(id, descricao, quantidade);
        assertNull(myService.createComponent(componente));
    }

    @ParameterizedTest
    @DisplayName("Deve testar se todos os limites do update de componentes estão a funcionar")
    @CsvSource(textBlock = """ 
            -1,kdajkndnjkadjknadjnkajndkasdasdasdasdasdadjknfjkdsnfjknajksfnjkngfbhdvmn cxvjhbashjbdszxcjknjkcjknawshbndbhadjnajkdnajkndajkndajkndnjkadnjkadnjkadnkjdjnkadjkna,0
            -12414,null,101
            0,teste,-123
            0,null,999
            """)
    @Order(8)
    void shouldTestAllLimitsOfUpdateComponent(int id, String descricao, int quantidade) throws ExecutionException, InterruptedException {
        Componente componente = new Componente(id, descricao, quantidade);
        assertNull(myService.updateComponent(componente));
    }

    @ParameterizedTest
    @DisplayName("Deve testar se todos os limites do delete de componentes estão a funcionar")
    @ValueSource(ints = {-1, -12414, 2345123, 999999999, -999999999,})
    @Order(9)
    void shouldTestAllLimitsOfDeleteComponent(int id) throws ExecutionException, InterruptedException {
        assertNull(myService.deleteComponent(id));
    }


}