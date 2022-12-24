package com.example.test.demo.service;

import com.example.test.demo.model.*;
import com.google.cloud.firestore.Firestore;
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
class PedidoServiceTest {
    @Autowired
    private PedidoService myService;
    Firestore db;
    private static final String COL_NAME = "pedido";

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
                0,0,abc
                1,1,xyz
            """)
    void shouldTestIfCreatePedidoUtilizadorIsSent(int respostaId, int pedidoId, String descricao) throws ExecutionException, InterruptedException {

        PedidoUtilizador pedidoUtilizador = new PedidoUtilizador(respostaId, pedidoId, descricao);
        String result = myService.createPedidoUtilizador(pedidoUtilizador);
        assertNotNull(result);

    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                0,0,0,0
                1,1,1,0
            """)
    void shouldTestIfCreatePedidoLaboratorioIsSent(int respostaId, int pedidoId, int labId, int authorId) throws ExecutionException, InterruptedException {

        PedidoLaboratorio pedidoLaboratorio = new PedidoLaboratorio(respostaId, pedidoId, labId, authorId);
        String result = myService.createPedidoLaboratorio(pedidoLaboratorio);
        assertNotNull(result);

    }

    /**
     * TESTES DE RETORNO DE PEDIDOS
     **/
    @Test
    void shouldTestIfGetAllPedidosIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllPedidos());
    }

    @Test
    void shouldTestIfGetAllPedidosIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllPedidos().size() > 0);
    }

    @Test
    void shouldTestIfGetAllPedidosUtilizadorIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllPedidosUtilizador());
    }

    @Test
    void shouldTestIfGetAllPedidosUtilizadorIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllPedidosUtilizador().size() > 0);
    }

    @Test
    void shouldTestIfGetAllPedidosLaboratorioIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllPedidosLaboratorio());
    }
    @Test
    void shouldTestIfGetAllPedidosLaboratorioIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllPedidosLaboratorio().size() > 0);
    }

    @Test
    void shouldTestIfGetAllPedidosMaterialIsNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllPedidosMaterial());
    }

    @Test
    void shouldTestIfGetAllPedidosMaterialIsNotLessThanZero() throws ExecutionException, InterruptedException {
        assertTrue(myService.getAllPedidosMaterial().size() > 0);
    }

    /*TESTES DE ATUALIZAÇÂO DE PEDIDOS*/
    // TODO: 24/12/2022 TESTES DE ATUALIZAÇÂO DE PEDIDOS

    // TODO: 24/12/2022 TESTES DE REMOÇÃO DE PEDIDOS

    @Test
    void updatePedidoUtilizador() {
    }

    @Test
    void deletePedidoUtilizador() {
    }


    @Test
    void updatePedidoMaterial() {
    }

    @Test
    void deletePedidoMaterial() {
    }


    @Test
    void updatePedidoLaboratorio() {
    }

    @Test
    void deletePedidoLaboratorio() {
    }
}