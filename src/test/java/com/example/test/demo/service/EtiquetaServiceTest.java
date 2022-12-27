package com.example.test.demo.service;

import com.example.test.demo.model.EtiquetaMaterial;
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

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EtiquetaServiceTest {
    @Autowired
    private EtiquetaService myService;
    Firestore db;
    private static final String COL_NAME = "etiquetaMaterial";

    @BeforeEach
    void setUp()  {
        db = FirestoreClient.getFirestore();
    }


    @ParameterizedTest
    @DisplayName("Teste para inserir uma etiqueta")
    @CsvSource(value = {"nao consumivel,drones,5,0,drone", "nao consumivel, impressoras, 5, 1, impressora", "consumivel, canetas, 5, 2, caneta" })
    @Order(1)
    public void shouldTestCreateEtiquetaIsSent(String etiqueta, String descricaoMaterial, int quantidade, int etiquetaId, String subEtiqueta) throws Exception {
        ArrayList<Integer> componentes = new ArrayList<>();
        componentes.add(1);
        componentes.add(2);
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        EtiquetaMaterial etiquetaMaterial = new EtiquetaMaterial(componentes,etiqueta, descricaoMaterial, quantidade, etiquetaId, subEtiqueta, materiaisId);
        String result = myService.createEtiqueta(etiquetaMaterial);
        assertNotNull(result, result);
    }


    @ParameterizedTest
    @DisplayName("Testa se existe uma Etiquet com o id especificado")
    @ValueSource(ints = {0, 1, 2})
    @Order(2)
    public void shouldTestCreateEtiquetaIsInDataBase(int id) throws Exception {
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", id).get();
        int size = future.get().getDocuments().size();
        assertNotEquals(0, size, "Tamanho é:" + size);
    }


    @Test
    @DisplayName("Testa se vem null ao procurar todas as etiquetas, presumindo que existem etiquetas")
    @Order(3)
    public void shouldTestIfGetAllEtiquetasIsNotNull() throws Exception {
        assertNotNull(myService.getAllEtiquetas());
    }

    @Test
    @DisplayName("Testa se getAllEtiquetas não tem resultado menor que zero")
    @Order(4)
    public void shouldTestIfGetAllEtiquetasHasNotLessThanZero() throws Exception {
        assertTrue(myService.getAllEtiquetas().size() >= 0);
    }

    @ParameterizedTest
    @DisplayName("Testa se é realizado um update na Etiqueta ao receber uma Etiqueta com o mesmo id")
    @CsvSource(value = {"nao consumivelUPD,dronesUPD,9,0,droneUPD", "nao consumivelUPD, impressorasUPD, 7, 1, impressoraUPD", "consumivelUPD, canetasUPD, 2, 2, canetaUPD" })
    @Order(5)
    public void shouldTestIfUpdateEtiquetaWorks(String etiqueta, String descricaoMaterial, int quantidade, int etiquetaId, String subEtiqueta) throws Exception {
        ArrayList<Integer> componentes = new ArrayList<>();
        componentes.add(1);
        componentes.add(2);
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
       EtiquetaMaterial etiquetaMaterial = new EtiquetaMaterial(componentes,etiqueta, descricaoMaterial, quantidade, etiquetaId, subEtiqueta, materiaisId);
        String result = myService.updateEtiqueta(etiquetaMaterial);
        assertEquals(result, "updated",result);
    }


    @ParameterizedTest
    @DisplayName("Testa se é possível apagar uma Etiqueta, presumindo a sua existência com o id")
    @ValueSource(ints = {0, 1, 2})
    @Order(6)
    public void shouldTestIfDeleteEtiquetaWorks(int id) throws Exception {
        String result = myService.deleteEtiqueta(id);
        assertNotNull(result, result);
    }
    // TODO: 26/12/2022 TESTAR SE LIMITES DE CRIAÇÃO DE ETIQUETAS ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE LIMITES DE NA ATUALIZAÇÃO DE ETIQUETAS ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE O ID PODE SER MENOR QUE 0
}
