package com.example.test.demo.service;

import com.example.test.demo.model.*;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PedidoServiceTest {
    @Autowired
    private PedidoService myService;
    Firestore db;

    @BeforeEach
    void setUp() {
        db = FirestoreClient.getFirestore();
    }

    /**
     * TESTES DE INSERÇÃO DE PEDIDOS
     **/
    @ParameterizedTest
    @CsvSource(textBlock = """
                0,0,0,0,1,2
                1,1,0,0,1,2
            """)
    @Order(1)
    @DisplayName("Deve testar se o pedidoMaterial foi inserido na base de dadaos")
    void shouldTestIfCreatePedidoMaterialIsSent(int respostaId, int pedidoId, int authorId, int idMat1, int idMat2, int idMat3) throws ExecutionException, InterruptedException {
        ArrayList<Integer> idMateriais = new ArrayList<>();
        idMateriais.add(idMat1);
        idMateriais.add(idMat2);
        idMateriais.add(idMat3);
        PedidoMaterial PedidoMaterial = new PedidoMaterial(respostaId, pedidoId, authorId, idMateriais);
        String result = myService.createPedidoMaterial(PedidoMaterial);
        assertNotNull(result);

    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                2,2,abcasdadsad
                3,3,xyzasdasd
            """)
    @Order(2)
    @DisplayName("Deve testar se o pedidoUtilizador foi inserido na base de dados")
    void shouldTestIfCreatePedidoUtilizadorIsSent(int respostaId, int pedidoId, String descricao) throws ExecutionException, InterruptedException {

        PedidoUtilizador pedidoUtilizador = new PedidoUtilizador(respostaId, pedidoId, descricao);
        String result = myService.createPedidoUtilizador(pedidoUtilizador);
        assertNotNull(result);

    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                4,4,0,0
                5,5,1,1
            """)
    @Order(3)
    @DisplayName("Deve testar se o pedidoLaboratorio foi inserido na base de dadaos")
    void shouldTestIfCreatePedidoLaboratorioIsSent(int respostaId, int pedidoId, int labId, int authorId) throws ExecutionException, InterruptedException {

        PedidoLaboratorio pedidoLaboratorio = new PedidoLaboratorio(respostaId, pedidoId, labId, authorId);
        String result = myService.createPedidoLaboratorio(pedidoLaboratorio);
        assertNotNull(result);

    }

    /**
     * TESTES DE RETORNO DE PEDIDOS
     **/
    @Test
    @Order(4)
    @DisplayName("Deve testar se a lista de pedidos não é nula")
    void shouldTestIfGetAllPedidosIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllPedidos());
    }

    @Test
    @Order(5)
    @DisplayName("Deve testar se a lista de pedidos não é vazia")
    void shouldTestIfGetAllPedidosIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllPedidos().size() > 0);
    }

    @Test
    @Order(6)
    @DisplayName("Deve testar se a lista de pedidos de Utilizador não é nula")
    void shouldTestIfGetAllPedidosUtilizadorIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllPedidosUtilizador());
    }

    @Test
    @Order(7)
    @DisplayName("Deve testar se a lista de pedidos de Utilizador não é nula")
    void shouldTestIfGetAllPedidosUtilizadorIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllPedidosUtilizador().size() > 0);
    }

    @Test
    @Order(8)
    @DisplayName("Deve testar se a lista de pedidos de laboratorio não é nula")
    void shouldTestIfGetAllPedidosLaboratorioIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllPedidosLaboratorio());
    }

    @Test
    @Order(9)
    @DisplayName("Deve testar se a lista de pedidos de laboratorio é maior que 0")
    void shouldTestIfGetAllPedidosLaboratorioIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllPedidosLaboratorio().size() > 0);
    }

    @Test
    @Order(10)
    @DisplayName("Deve testar se a lista de pedidos de material não é nula")
    void shouldTestIfGetAllPedidosMaterialIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllPedidosMaterial());
    }

    @Test
    @Order(11)
    @DisplayName("Deve testar se a lista de pedidos de material é maior que 0")
    void shouldTestIfGetAllPedidosMaterialIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllPedidosMaterial().size() > 0);
    }

    /*TESTES DE ATUALIZAÇÂO DE PEDIDOS*/
    // TODO: 24/12/2022 TESTES DE ATUALIZAÇÂO DE PEDIDOS

    @ParameterizedTest
    @CsvSource(textBlock = """ 
            0,0,pwp
            1,1,dc
            """)
    @Order(12)
    @DisplayName("Deve testar se o pedido de utilizador foi atualizado na base de dadaos")
    void shouldTestIfUpdatePedidoUtilizadorWorks(int respostaId, int pedidoId, String descricao) throws ExecutionException, InterruptedException {
        PedidoUtilizador pedidoUtilizador = new PedidoUtilizador(respostaId, pedidoId, descricao);
        String isUpDated = myService.updatePedidoUtilizador(pedidoUtilizador);
        assertNotNull(isUpDated);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """ 
            2,0,4,2
            3,1,8,3
            """)
    @Order(13)
    @DisplayName("Deve testar se o pedido de laboratorio foi atualizado na base de dadaos")
    void shouldTestIfUpdatePedidoLaboratorioWorks(int respostaId, int pedidoId, int labId, int authorId) throws ExecutionException, InterruptedException {
        PedidoLaboratorio pedidoLaboratorio = new PedidoLaboratorio(respostaId, pedidoId, labId, authorId);
        String isUpDated = myService.updatePedidoLaboratorio(pedidoLaboratorio);
        assertNotNull(isUpDated);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """ 
            4,4,6,3,4,5
            5,5,8,5,6,7
            """)
    @Order(14)
    @DisplayName("Deve testar se o pedido de material foi atualizado na base de dadaos")
    void shouldTestIfUpdatePedidoMaterialWorks(int respostaId, int pedidoId,int author,int mat,int mat2,int mat3) throws ExecutionException, InterruptedException {
        ArrayList<Integer> idMateriais = new ArrayList<>();
        idMateriais.add(mat);
        idMateriais.add(mat2);
        idMateriais.add(mat3);
        PedidoMaterial pedidoMaterial = new PedidoMaterial(respostaId, pedidoId,author, idMateriais);
        String isUpDated = myService.updatePedidoMaterial(pedidoMaterial);
        assertNotNull(isUpDated);
    }


    // TODO: 24/12/2022 TESTES DE REMOÇÃO DE PEDIDOS

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    @Order(15)
    @DisplayName("Deve testar se o pedido de utilizador foi removido da base de dados")
    void shouldDeletePedidoUtilizadorFromDataBase(int idPedidoUtilizador) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deletePedidoUtilizador(idPedidoUtilizador);
        String[] isDeletedArray = isDeleted.split(":");
        System.out.println(isDeletedArray[0]);
        assertEquals(String.valueOf(idPedidoUtilizador), isDeletedArray[1]);
    }

    @ParameterizedTest
    @ValueSource(ints = {9, 299})
    @Order(16)
    @DisplayName("Deve testar se o pedido de utilizador que não existe não foi removido da base de dados")
    void shouldNotDeletePedidoUtilizador(int idPedidoUtilizador) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deletePedidoUtilizador(idPedidoUtilizador);
        String[] isDeletedArray = isDeleted.split(":");
        assertEquals("Pedido não encontrado para ser eliminado ou já foi respondido", isDeletedArray[0]);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            4,0
            5,0
            """)
    @Order(17)
    @DisplayName("Deve testar se o pedido de laboratorio foi removido da base de dados")
    void shouldDeletePedidoLaboratorioFromDataBase(int idPedidoLaboratorio,int idAuthor) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deletePedidoLaboratorio(idPedidoLaboratorio,idAuthor);
        String[] isDeletedArray = isDeleted.split(":");
        System.out.println(Arrays.toString(isDeletedArray));
        assertEquals(String.valueOf(idPedidoLaboratorio), isDeletedArray[1]);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            342,2
            345,3
            """)
    @Order(18)
    @DisplayName("Deve testar se o pedido de laboratorio que não existe não foi removido da base de dados")
    void shouldNotDeletePedidoLaboratorio(int idPedidoLaboratorio,int author) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deletePedidoLaboratorio(idPedidoLaboratorio,author);
        String[] isDeletedArray = isDeleted.split(":");
        assertEquals("Pedido não encontrado para ser eliminado ou já foi respondido", isDeletedArray[0]);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,0
            1,0
            """)
    @Order(19)
    @DisplayName("Deve testar se o pedido de material foi removido da base de dados")
    void shouldDeletePedidoMaterialFromDataBase(int idPedidoMAterial,int idAuthor) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deletePedidoMaterial(idPedidoMAterial,idAuthor);
        String[] isDeletedArray = isDeleted.split(":");
        assertEquals(String.valueOf(idPedidoMAterial), isDeletedArray[1]);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            342,0
            345,0
            """)
    @Order(20)
    @DisplayName("Deve testar se o pedido de material que não existe não foi removido da base de dados")
    void shouldNotDeletePedidoMaterial(int idPedidoMaterial,int idAuthor) throws ExecutionException, InterruptedException {
        String isDeleted = myService.deletePedidoMaterial(idPedidoMaterial,idAuthor);
        String[] isDeletedArray = isDeleted.split(":");
        assertEquals("Pedido não encontrado para ser eliminado ou já foi respondido", isDeletedArray[0]);
    }

    // TODO: 26/12/2022 TESTAR LIMITES DE CRIAÇÃO DE PEDIDOS PARA OS 3
    // TODO: 26/12/2022 TESTAR LIMITES NA ATUALIZAÇÃO DE PEDIDOS PARA OS 3
    // TODO: 26/12/2022 TESTAR SE APAGAR PEDIDOS ACEITA NEGATIVOS

    @ParameterizedTest
    @CsvSource(textBlock = """
            1,1,null
            -12312,-123123,""
            0,0,""        
            """)
    @Order(21)
    @DisplayName("Deve testar se o pedido de utilizador não foi criado na base de dados")
    void shouldCreatePedidoUtilizador(int idPedidoUtilizador, int idResposta, String descricao) throws ExecutionException, InterruptedException {
        ArrayList<Integer> idMateriais = new ArrayList<>();
        PedidoUtilizador pedidoUtilizador = new PedidoUtilizador(idPedidoUtilizador, idResposta, descricao );
        String isCreated = myService.createPedidoUtilizador(pedidoUtilizador);
        assertNull(isCreated);
    }
}