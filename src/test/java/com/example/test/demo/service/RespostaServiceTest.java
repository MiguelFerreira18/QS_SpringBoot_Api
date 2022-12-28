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


    //Testes de Create

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,0,1,26/12/2022,27/12/2022","20/12/2022,RespostaLabDesC1,1,1,2,28/12/2022,29/12/2022"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio")
    @Order(1)
    void testCreateRespostaLaboratorio(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertEquals("RespostaLaboratorio created",result);
    }

    @ParameterizedTest
    @CsvSource({"24/,RespostaLabDesc0,0,0,1,26/12/2022,27/12/2022","20/12/20222222,RespostaLabDesC1,1,1,2,28  / 12   /2 022,29/12/2022"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com a data fora dos limites")
    @Order(2)
    void testCreateRespostaLaboratorioDataOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,Desc0,0,0,1,26/12/2022,27/12/2022","20/12/2022,iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii,1,1,2,28/12/2022,29/12/2022"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com a descricao fora dos limites")
    @Order(3)
    void testCreateRespostaLaboratorioDescricaoOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,-1,0,1,26/12/2022,27/12/2022","20/12/2022,RespostaLabDesC1,-99,1,2,28/12/2022,29/12/2022"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com o id da resposta fora dos limites")
    @Order(4)
    void testCreateRespostaLaboratorioIdOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,-1,1,26/12/2022,27/12/2022","20/12/2022,RespostaLabDesC1,1,999999,2,28/12/2022,29/12/2022"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com o id do docente inexistente na base de dados")
    @Order(5)
    void testCreateRespostaLaboratorioIdDocenteInexistent(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,-1,1,26/12/2022,27/12/2022","20/12/2022,RespostaLabDesC1,1,1,99999,28/12/2022,29/12/2022"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com o id do laboratorio inexistente na base de dados")
    @Order(6)
    void testCreateRespostaLaboratorioIdLabInexistent(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,0,1,2022,27/12/2022","20/12/2022,RespostaLabDesC1,1,1,2,28  /  1 2/2 022,29/12/2022"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com a data de reserva de inicio fora dos limites")
    @Order(7)
    void testCreateRespostaLaboratorioReservaInicioOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,0,1,26/12/2022,2022","20/12/202,RespostaLabDesC1,1,1,2,28/12/2022,29   / 12 /   2022"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com a data de reserva de fim fora dos limites")
    @Order(8)
    void testCreateRespostaLaboratorioReservaFimOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int laboratorioId, String dataReservaInicio, String dataReservaFim) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data,descricao,respostaId,utilizadorId,laboratorioId,dataReservaInicio,dataReservaFim);
        String result = myService.createRespostaLaboratorio(r);
        assertNull(result);
    }


    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,1","01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,2"})
    @DisplayName("Testa a criacao de uma RespostaMaterial")
    @Order(9)
    void testCreateRespostaMaterial(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertEquals("respostaMaterial created",result);
    }

    @ParameterizedTest
    @CsvSource({"24/12,RespostaMaterial0,2,30/12/22,31/12/22,1","01  /  01  /  23,RespostaMaterial1,3,02/01/23,04/01/23,2"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com a data fora dos limites")
    @Order(10)
    void testCreateRespostaMaterialDataOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,R,2,30/12/22,31/12/22,1","01/01/23,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,3,02/01/23,04/01/23,2"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com a descricao fora dos limites")
    @Order(11)
    void testCreateRespostaMaterialDescOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,-1,30/12/22,31/12/22,1","01/01/23,RespostaMaterial1,-5,02/01/23,04/01/23,2"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com o id da resposta menor que zero")
    @Order(12)
    void testCreateRespostaMaterialIdNegative(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12,31/12/22,1","01/01/23,RespostaMaterial1,3,022 / 01111/2023,04/01/23,2"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com a dataReserva fora dos limites")
    @Order(13)
    void testCreateRespostaMaterialReservaOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,/12/22,1","01/01/23,RespostaMaterial1,3,02/01/23,042 / 011 / 2023,2"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com a data de Reserva fora do limite")
    @Order(14)
    void testCreateRespostaMaterialEntregaOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,-1","01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,9999"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com o id de utilizador nao existente na base de dados")
    @Order(15)
    void testCreateRespostaMaterialIdDocenteInexistent(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,1","01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,2"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com materiais inexistentes ")
    @Order(16)
    void testCreateRespostaMaterialIdMateriaisInexistent(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId) throws ExecutionException, InterruptedException {
        ArrayList <Integer> materiaisId = new ArrayList<>();
        materiaisId.add(-1);
        materiaisId.add(9999);
        materiaisId.add(-50); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data,descricao,respostaId,dataReserva,dataEntrega,utilizadorId,materiaisId);
        String result = myService.createRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,4,true,1,Ruan carlosChanged7","10/10/2010,RespostaUtilizador1,5,false,2,Ruan carlosChanged8"})
    @DisplayName("Testa a criacao de um RespostaUtilizador")
    @Order(17)
    void testCreateRespostaUtilizador(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId, String nomeUtilizador) throws ExecutionException, InterruptedException
    {
        RespostaUtilizador r = new RespostaUtilizador(data,descricao,respostaId,aceite,utilizadorId,nomeUtilizador);
        String result = myService.createRespostaUtilizador(r);
        assertEquals("RespostaUtilizador created",result);
    }

    @ParameterizedTest
    @CsvSource({"2010,RespostaUtilizador0,4,true,1,Ruan carlosChanged7","101010/101/2010,RespostaUtilizador1,5,false,2,Ruan carlosChanged8"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com a data fora dos limites")
    @Order(18)
    void testCreateRespostaUtilizadorDataOutOfBounds(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId, String nomeUtilizador) throws ExecutionException, InterruptedException
    {
        RespostaUtilizador r = new RespostaUtilizador(data,descricao,respostaId,aceite,utilizadorId,nomeUtilizador);
        String result = myService.createRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,R,4,true,1,Ruan carlosChanged7","10/10/2010,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,5,false,2,Ruan carlosChanged8"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com a descricao fora dos limites")
    @Order(19)
    void testCreateRespostaUtilizadorDescOutOfBounds(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId, String nomeUtilizador) throws ExecutionException, InterruptedException
    {
        RespostaUtilizador r = new RespostaUtilizador(data,descricao,respostaId,aceite,utilizadorId,nomeUtilizador);
        String result = myService.createRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,-1,true,1,Ruan carlosChanged7","10/10/2010,RespostaUtilizador1,-5,false,2,Ruan carlosChanged8"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com respostaId menor que zero")
    @Order(20)
    void testCreateRespostaUtilizadorIdNegative(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId, String nomeUtilizador) throws ExecutionException, InterruptedException
    {
        RespostaUtilizador r = new RespostaUtilizador(data,descricao,respostaId,aceite,utilizadorId,nomeUtilizador);
        String result = myService.createRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,4,true,-1,Ruan carlosChanged7","10/10/2010,RespostaUtilizador1,9999,false,2,Ruan carlosChanged8"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com utilizadorId inexistente na base de dados")
    @Order(21)
    void testCreateRespostaUtilizadorIdDocenteInexistent(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId, String nomeUtilizador) throws ExecutionException, InterruptedException
    {
        RespostaUtilizador r = new RespostaUtilizador(data,descricao,respostaId,aceite,utilizadorId,nomeUtilizador);
        String result = myService.createRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,4,true,1,Ruan carlosChanged72","10/10/2010,RespostaUtilizador1,2,false,2,r"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com utilizadorId inexistente na base de dados")
    @Order(22)
    void testCreateRespostaUtilizadorNomeDocenteInexistent(String data, String descricao, int respostaId,  boolean aceite,int utilizadorId, String nomeUtilizador) throws ExecutionException, InterruptedException
    {
        RespostaUtilizador r = new RespostaUtilizador(data,descricao,respostaId,aceite,utilizadorId,nomeUtilizador);
        String result = myService.createRespostaUtilizador(r);
        assertNull(result);
    }

    //Testes de Update











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
        novo.add(2);
        novo.add(3);
        novo.add(4);

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
    //Testes GetTipo


    //Testes GetAll

    @Test
    @DisplayName("Testa se as respostas foram criadas, presumindo que as respostas ja foram criadas")
    @Order(4)
    void testGetAllRespostasSize() throws ExecutionException,InterruptedException
    {
        assertNotEquals(0,myService.getAllRespostas(),"Nenhuma resposta encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas nao sao null, presumindo que as respostas ja foram criadas")
    @Order(5)
    void testGetAllRespostasNotNull() throws ExecutionException,InterruptedException
    {
        assertNotNull(myService.getAllRespostas(),"Nenhuma resposta encontrada");
    }

    //Testes delete
    @ParameterizedTest
    @ValueSource(ints = {3,5,1})
    @DisplayName("Testa se e possivel apagar uma resposta")
    @Order(21)
    void testDeleteResposta(int id) throws ExecutionException,InterruptedException
    {
        assertEquals("RespostaUtilizador deleted with:"+id,myService.deleteResposta(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1,9999})
    @DisplayName("Testa se e possivel apagar uma resposta")
    @Order(21)
    void testDeleteRespostaInexistent(int id) throws ExecutionException,InterruptedException
    {
        assertNull(myService.deleteResposta(id));
    }


    // TODO: 26/12/2022 TESTAR LIMITES DE CRIAÇÃO DE RESPOSTAS PARA OS 3
    // TODO: 26/12/2022 TESTAR LIMITES NA ATUALIZAÇÃO DE RESPOSTAS PARA OS 3
    // TODO: 26/12/2022 TESTAR SE APAGAR PEDIDOS ACEITA NEGATIVOS
}
