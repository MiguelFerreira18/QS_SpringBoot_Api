package com.example.test.demo.service;

import com.example.test.demo.model.Docente;
import com.example.test.demo.model.Pedido;
import com.example.test.demo.model.PedidoMaterial;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    @ParameterizedTest
    @CsvSource(textBlock = """
                0,0,0,0,1,2
                1,1,0,0,1,2
            """)
    void shouldTestIfCreatePedidoMAterialIsSent(int respostaId, int pedidoId, int authorId,int idMat1,int idMat2,int idMat3) throws ExecutionException, InterruptedException {

        ArrayList<Integer> idMateriais = new ArrayList<>();
        idMateriais.add(idMat1);
        idMateriais.add(idMat2);
        idMateriais.add(idMat3);
        PedidoMaterial PedidoMaterial = new PedidoMaterial(respostaId, pedidoId, authorId,idMateriais);
        String result = myService.createPedidoMaterial(PedidoMaterial);
        assertNotNull(result);

    }

    @Test
    void getAllPedidosUtilizador() {
    }

    @Test
    void createPedidoUtilizador() {
    }

    @Test
    void updatePedidoUtilizador() {
    }

    @Test
    void deletePedidoUtilizador() {
    }

    @Test
    void getAllPedidosMaterial() {
    }

    @Test
    void createPedidoMaterial() {
    }

    @Test
    void updatePedidoMaterial() {
    }

    @Test
    void deletePedidoMaterial() {
    }

    @Test
    void getAllPedidosLaboratorio() {
    }

    @Test
    void createPedidoLaboratorio() {
    }

    @Test
    void updatePedidoLaboratorio() {
    }

    @Test
    void deletePedidoLaboratorio() {
    }
}