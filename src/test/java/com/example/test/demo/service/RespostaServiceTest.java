package com.example.test.demo.service;

import com.example.test.demo.model.Resposta;
import com.example.test.demo.model.RespostaLaboratorio;
import com.example.test.demo.model.RespostaMaterial;
import com.example.test.demo.model.RespostaUtilizador;
import com.google.cloud.firestore.Firestore;
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
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RespostaServiceTest
{
    private Firestore db;
    private String COL_NAME = "resposta";



    @Autowired
    private RespostaService myService;

    @Autowired
    private DocenteService docenteService;

    @BeforeEach
    void startDB()
    {
        db = FirestoreClient.getFirestore();
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,lab,0,1,1,26/12/22,27/12/22","30/12/22,lab,1,1,1,28/12/22,29/12/22"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio")
    @Order(1)
    void testCreateRespostaLaboratorio(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertNotNull(result,"Resposta nao inserida");

    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial,2,30/12/22,31/12/22,1","1/1/23,labMaterial,3,2/1/23,4/1/23,2"})
    @DisplayName("Testa a criacao de uma RespostaMaterial")
    @Order(2)
    void testCreateRespostaMaterial(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3);

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertNotNull(result,"Resposta nao inserida");

    }

    @ParameterizedTest
    @CsvSource({"1/12/22,RespostaUtilizador,4,true,1,Ruan carlos","2/12/22,RespostaUtilizador,5,false,1,Ruan carlos2"})
    @DisplayName("Testa a criacao de uma RespostaUtilizador")
    @Order(3)
    void testCreateRespostaUtilizador(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId, String nomeUtilizador) throws ExecutionException, InterruptedException {
        //Falta ver se o docente existe e se tem de ter o mesmo nome ou numero
        RespostaUtilizador r = new RespostaUtilizador(data,descricao,respostaId,aceite,utilizadorId,nomeUtilizador);
        String result = myService.createRespostaUtilizador(r);
        assertNotNull(result,"Resposta nao inserida");
    }


    @Test
    @DisplayName("Testa se as respostas foram criadas")
    @Order(4)
    void testGetAllRespostasSize() throws ExecutionException,InterruptedException
    {
        assertNotEquals(0,myService.getAllRespostas(),"Nenhuma resposta encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas nao sao null")
    @Order(5)
    void testGetAllRespostasNotNull() throws ExecutionException,InterruptedException
    {
        assertNotNull(myService.getAllRespostas(),"Nenhuma resposta encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas de laboratorio foram criadas")
    @Order(6)
    void testGetRespostaLaboratorioSize() throws ExecutionException,InterruptedException
    {
        assertNotEquals(0,myService.getRespostaLaboratorio(),"Nenhuma resposta de Laboratorio encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas nao sao null")
    @Order(7)
    void testGetRespostaLaboratorioNotNull() throws ExecutionException,InterruptedException
    {
        assertNotNull(myService.getRespostaLaboratorio());
    }



    @ParameterizedTest
    @ValueSource(ints = {0,1})
    @DisplayName("Testa se as respostas sao apenas do tipo laboratorio")
    @Order(8)
    void testGetRespostaLaboratorioType(int i) throws ExecutionException,InterruptedException
    {
        //RespostaLaboratorio tem tipo 1
        assertEquals(1,myService.getRespostaLaboratorio().get(i).getTipoResposta());
    }

   @Test
   @DisplayName("Testa se é possivel dar update numa RespostaLaboratorio")
   @Order(9)
   void testUpdateRespostaLaboratorio()throws ExecutionException,InterruptedException
   {
       RespostaLaboratorio r = new RespostaLaboratorio("24/12/22","RespostaLaboratorioUpdated",0,1,1,"26/11/22","27/11/22");
       assertNotEquals("Resposta não encontrada para ser atualizada",myService.updateRespostaLaboratorio(r));
   }

   @Test
   @DisplayName("Testa se é possivel dar update numa RespostaLaboratorio inexistente")
   @Order(10)
   void testUpdateRespostaLaboratorioInexistent() throws ExecutionException,InterruptedException
   {
       RespostaLaboratorio r = new RespostaLaboratorio("24/12/22","RespostaLaboratorioUpdated",-1,1,1,"26/11/22","27/11/22");
       assertEquals("Resposta não encontrada para ser atualizada",myService.updateRespostaLaboratorio(r));
   }


    //Testes RespostaMaterial

    @Test
    @DisplayName("Testa se as respostas de material foram criadas")
    @Order(11)
    void testGetRespostaMaterialSize() throws ExecutionException,InterruptedException
    {
        assertNotEquals(0,myService.getRespostaMaterial(),"Nenhuma resposta de Material encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas nao sao null")
    @Order(12)
    void testGetRespostaMaterialNotNull() throws ExecutionException,InterruptedException
    {
        assertNotNull(myService.getRespostaMaterial());
    }



    @ParameterizedTest
    @ValueSource(ints = {2,3})
    @DisplayName("Testa se as respostas sao apenas do tipo material")
    @Order(13)
    void testGetRespostaMaterialType(int i) throws ExecutionException,InterruptedException
    {
        //RespostaMaterial tem tipo 2
        assertEquals(2,myService.getRespostaMaterial().get(i).getTipoResposta());
    }

    @Test
    @DisplayName("Testa se é possivel dar update numa RespostaMaterial")
    @Order(14)
    void testUpdateRespostaMaterial()throws ExecutionException,InterruptedException
    {
        ArrayList<Integer>novo = new ArrayList<>();
        novo.add(7);
        novo.add(8);
        novo.add(9);

        RespostaMaterial r = new RespostaMaterial("24/12/22","RespostaMaterialUpdated",2,"30/12/22","31/12/22",1,novo);
        assertNotEquals("Resposta não encontrada para ser atualizada",myService.updateRespostaMaterial(r));
    }

    @Test
    @DisplayName("Testa se é possivel dar update numa RespostaMaterial inexistente")
    @Order(15)
    void testUpdateRespostaMaterialInexistent() throws ExecutionException,InterruptedException
    {
        ArrayList<Integer>novo = new ArrayList<>();
        novo.add(7);
        novo.add(8);
        novo.add(9);
        RespostaMaterial r = new RespostaMaterial("24/12/22","RespostaMaterial",-1,"30/12/22","31/12/22",1,novo);
        assertEquals("Resposta não encontrada para ser atualizada",myService.updateRespostaMaterial(r));
    }

    //Testes RespostaUtilizador

    @Test
    @DisplayName("Testa se as respostas de utilizador foram criadas")
    @Order(16)
    void testGetRespostaUtilizadorSize() throws ExecutionException,InterruptedException
    {
        assertNotEquals(0,myService.getRespostaUtilizador(),"Nenhuma resposta de Utilizador encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas de utilizador nao sao null")
    @Order(17)
    void testGetRespostaUtilizadorNotNull() throws ExecutionException,InterruptedException
    {
        assertNotNull(myService.getRespostaUtilizador());
    }



    @ParameterizedTest
    @ValueSource(ints = {4,5})
    @DisplayName("Testa se as respostas sao apenas do tipo Utilizador")
    @Order(18)
    void testGetRespostaUtilizadorType(int i) throws ExecutionException,InterruptedException
    {
        //RespostaMaterial tem tipo 0
        assertEquals(0,myService.getRespostaUtilizador().get(i).getTipoResposta());
    }

    @Test
    @DisplayName("Testa se é possivel dar update numa RespostaUtilizador")
    @Order(19)
    void testUpdateRespostaUtilizador()throws ExecutionException,InterruptedException
    {
        RespostaUtilizador r = new RespostaUtilizador("1/12/22","RespostaUtilizadorUpdated",4,true,1,"Ruan carlos");
        assertNotEquals("Resposta não encontrada para ser atualizada",myService.updateRespostaUtilizador(r));
    }

    @Test
    @DisplayName("Testa se é possivel dar update numa RespostaUtilizador inexistente")
    @Order(20)
    void testUpdateRespostaUtilizadorInexistent() throws ExecutionException,InterruptedException
    {
        RespostaUtilizador r = new RespostaUtilizador("1/12/22","RespostaUtilizadorUpdated",-1,true,1,"Ruan carlos");
        assertEquals("Resposta não encontrada para ser atualizada",myService.updateRespostaUtilizador(r));
    }

    @ParameterizedTest
    @ValueSource(ints = {3,5,1})
    @DisplayName("Testa se e possivel apagar uma resposta")
    @Order(21)
    void testDeleteResposta(int id) throws ExecutionException,InterruptedException
    {
        assertNotEquals("Resposta não encontrada para ser eleminada",myService.deleteResposta(id));
    }
    // TODO: 26/12/2022 TESTAR LIMITES DE CRIAÇÃO DE RESPOSTAS PARA OS 3
    // TODO: 26/12/2022 TESTAR LIMITES NA ATUALIZAÇÃO DE RESPOSTAS PARA OS 3
    // TODO: 26/12/2022 TESTAR SE APAGAR PEDIDOS ACEITA NEGATIVOS
}
