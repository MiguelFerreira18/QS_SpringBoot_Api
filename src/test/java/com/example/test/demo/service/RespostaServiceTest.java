package com.example.test.demo.service;

import com.example.test.demo.model.*;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RespostaServiceTest {
    private Firestore db;
    private String COL_NAME = "resposta";


    @Autowired
    private RespostaService myService;
    @Autowired
    private PedidoService pedidoService;




    @BeforeEach
    void startDB() {
        db = FirestoreClient.getFirestore();

        try {
            pedidoService.createPedidoLaboratorio(new PedidoLaboratorio(0, 3, 10, LocalDate.now().toString()));
            pedidoService.createPedidoMaterial(new PedidoMaterial(1, 10, LocalDate.now().toString(), LocalDate.now().toString()));
            pedidoService.createPedidoUtilizador(new PedidoUtilizador(2, "teste", 10));


        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterEach
    void destroy(){
        try {
            pedidoService.deletePedidoLaboratorio(0,10);
            pedidoService.deletePedidoMaterial(1,10);
            pedidoService.deletePedidoUtilizador(2);


        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //Testes de Create

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,10,0,3,26/12/2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1,1,10,0,3,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio")
    @Order(1)
    void testCreateRespostaLaboratorio(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertEquals("RespostaLaboratorio created", result);
    }

    @ParameterizedTest
    @CsvSource({"24/,RespostaLabDesc0,0,0,4,1,26/12/2022,27/12/2022,false", "20/12/202222222,RespostaLabDesC1,1,1,5,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com a data fora dos limites")
    @Order(2)
    void testCreateRespostaLaboratorioDataOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,Desc0,0,0,4,1,26/12/2022,27/12/2022,false", "20/12/2022,iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii,1,1,5,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com a descricao fora dos limites")
    @Order(3)
    void testCreateRespostaLaboratorioDescricaoOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,-1,0,4,1,26/12/2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1,-99,1,5,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com o id da resposta fora dos limites")
    @Order(4)
    void testCreateRespostaLaboratorioIdOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,-1,4,1,26/12/2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1,1,999999,5,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com o id do docente inexistente na base de dados")
    @Order(5)
    void testCreateRespostaLaboratorioIdDocenteInexistent(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,1,4,-1,26/12/2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1,1,1,5,99999,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com o id do laboratorio inexistente na base de dados")
    @Order(6)
    void testCreateRespostaLaboratorioIdLabInexistent(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,0,4,1,2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1,1,1,5,2,28  /  1 2/2 022,29/12/2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com a data de reserva de inicio fora dos limites")
    @Order(7)
    void testCreateRespostaLaboratorioReservaInicioOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,0,4,1,26/12/2022,2022,false", "20/12/202,RespostaLabDesC1,1,1,5,2,28/12/2022,29   / 12 /   2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com a data de reserva de fim fora dos limites")
    @Order(8)
    void testCreateRespostaLaboratorioReservaFimOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertNull(result);
    }


    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,0,-1,1,26/12/2022,27/12/2022,false", "20/12/202,RespostaLabDesC1,1,1,9999,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a criacao de uma RespostaLaboratorio com o id de pedido fora dos limites")
    @Order(8)
    void testCreateRespostaLaboratorioPedidoOutOfBounds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.createRespostaLaboratorio(r, pedidoId);
        assertNull(result);
    }


    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,10,30/12/22,31/12/22,3,1,false", "01/01/23,RespostaMaterial1,10,02/01/23,04/01/23,3,1,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial")
    @Order(9)
    void testCreateRespostaMaterial(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertEquals("respostaMaterial created", result);
    }

    @ParameterizedTest
    @CsvSource({"24/12,RespostaMaterial0,2,30/12/22,31/12/22,1,0,false", "01  /  01  /  23,RespostaMaterial1,3,02/01/23,04/01/23,2,1,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com a data fora dos limites")
    @Order(10)
    void testCreateRespostaMaterialDataOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,R,2,30/12/22,31/12/22,1,0,false", "01/01/23,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,3,02/01/23,04/01/23,2,1,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com a descricao fora dos limites")
    @Order(11)
    void testCreateRespostaMaterialDescOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,-1,30/12/22,31/12/22,1,0,false", "01/01/23,RespostaMaterial1,-5,02/01/23,04/01/23,2,1,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com o id da resposta menor que zero")
    @Order(12)
    void testCreateRespostaMaterialIdNegative(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12,31/12/22,1,0,false", "01/01/23,RespostaMaterial1,3,022 / 01111/2023,04/01/23,2,1,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com a dataReserva fora dos limites")
    @Order(13)
    void testCreateRespostaMaterialReservaOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,/12/22,1,0,false", "01/01/23,RespostaMaterial1,3,02/01/23,042 / 011 / 2023,2,1,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com a data de Entrega fora do limite")
    @Order(14)
    void testCreateRespostaMaterialEntregaOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,-1,0,false", "01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,9999,1,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com o id de utilizador nao existente na base de dados")
    @Order(15)
    void testCreateRespostaMaterialIdDocenteInexistent(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,1,0,false", "01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,2,1,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com materiais inexistentes ")
    @Order(16)
    void testCreateRespostaMaterialIdMateriaisInexistent(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(-98);
        materiaisId.add(-1);
        materiaisId.add(123); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,1,-1,false", "01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,2,999,false"})
    @DisplayName("Testa a criacao de uma RespostaMaterial com pedidoId inexistente na base de dados ")
    @Order(16)
    void testCreateRespostaMaterialPedidoIdInexistent(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(-98);
        materiaisId.add(-1);
        materiaisId.add(123); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.createRespostaMaterial(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,4,true,3,2", "10/10/2010,RespostaUtilizador1,5,true,3,2"})
    @DisplayName("Testa a criacao de um RespostaUtilizador")
    @Order(17)
    void testCreateRespostaUtilizador(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.createRespostaUtilizador(r, pedidoId);
        assertEquals("RespostaUtilizador created", result);
    }

    @ParameterizedTest
    @CsvSource({"2010,RespostaUtilizador0,4,true,1,2", "101010/101/2010,RespostaUtilizador1,5,true,2,3"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com a data fora dos limites")
    @Order(18)
    void testCreateRespostaUtilizadorDataOutOfBounds(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.createRespostaUtilizador(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,R,4,true,1,2", "10/10/2010,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,5,true,2,3"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com a descricao fora dos limites")
    @Order(19)
    void testCreateRespostaUtilizadorDescOutOfBounds(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.createRespostaUtilizador(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,-1,true,1,2", "10/10/2010,RespostaUtilizador1,-5,true,2,3"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com respostaId menor que zero")
    @Order(20)
    void testCreateRespostaUtilizadorIdNegative(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.createRespostaUtilizador(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,4,true,-1,2", "10/10/2010,RespostaUtilizador1,5,true,9999,3"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com utilizadorId inexistente na base de dados")
    @Order(21)
    void testCreateRespostaUtilizadorIdDocenteInexistent(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.createRespostaUtilizador(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,4,true,1,2,Ruan carlosChanged72", "10/10/2010,RespostaUtilizador1,2,true,2,3,r"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com nome utilizador/docente inexistente na base de dados")
    @Order(22)
    void testCreateRespostaUtilizadorNomeDocenteInexistent(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.createRespostaUtilizador(r, pedidoId);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0,4,true,1,-2,Ruan carlosChanged7", "10/10/2010,RespostaUtilizador1,5,true,2,9999,Ruan carlosChanged8"})
    @DisplayName("Testa a criacao de um RespostaUtilizador com pedidoId inexistente na base de dados")
    @Order(22)
    void testCreateRespostaUtilizadorPedidoIdInexistent(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.createRespostaUtilizador(r, pedidoId);
        assertNull(result);
    }

    //Testes de Update --------------------------------------------------------------------------------------------------

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0Update,0,10,0,3,26/12/2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1Update,1,10,0,3,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa se e possivel dar update numa RespostaLaboratorio")
    @Order(23)
    void testUpdateRespostaLaboratorio(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        assertEquals("RespostaLaboratorio updated with:" + respostaId, myService.updateRespostaLaboratorio(r));
    }

    @ParameterizedTest
    @CsvSource({"24/,RespostaLabDesc0Update,0,0,4,1,26/12/2022,27/12/2022,false", "20/12/202222222,RespostaLabDesC1Update,1,1,5,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a atualizao de uma RespostaLaboratorio com a data fora dos limites")
    @Order(24)
    void testUpdateRespostaLaboratorioDataOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.updateRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,updt0,0,0,4,1,26/12/2022,27/12/2022,false", "20/12/2022,iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii,1,1,5,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a atualizacao de uma RespostaLaboratorio com a descricao fora dos limites")
    @Order(25)
    void testUpdateRespostaLaboratorioDescricaoOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.updateRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0Update,-1,0,4,1,26/12/2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1Update,-99,1,5,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a atualizacao de uma RespostaLaboratorio com o id da resposta fora dos limites")
    @Order(26)
    void testUpdateRespostaLaboratorioIdOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.updateRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0Update,0,-1,4,1,26/12/2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1Update,1,999999,5,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a atualizacao de uma RespostaLaboratorio com o id do docente inexistente na base de dados")
    @Order(27)
    void testUpdateRespostaLaboratorioIdDocenteInexistent(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.updateRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,1,4,-1,26/12/2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1,1,1,5,99999,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a atualizacao de uma RespostaLaboratorio com o id do laboratorio inexistente na base de dados")
    @Order(28)
    void testUpdateRespostaLaboratorioIdLabInexistent(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.updateRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0Update,0,0,4,1,2022,27/12/2022,false", "20/12/2022,RespostaLabDesC1Update,1,1,5,2,28  /  1 2/2 022,29/12/2022,false"})
    @DisplayName("Testa a atualizacao de uma RespostaLaboratorio com a data de reserva de inicio fora dos limites")
    @Order(29)
    void testUpdateRespostaLaboratorioReservaInicioOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.updateRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,0,4,1,26/12/2022,2022,false", "20/12/202,RespostaLabDesC1,1,1,5,2,28/12/2022,29   / 12 /   2022,false"})
    @DisplayName("Testa a atualizacao de uma RespostaLaboratorio com a data de reserva de fim fora dos limites")
    @Order(30)
    void testUpdateRespostaLaboratorioReservaFimOutOfBonds(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.updateRespostaLaboratorio(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/2022,RespostaLabDesc0,0,0,-4,1,26/12/2022,27/12/2022,false", "20/12/202,RespostaLabDesC1,1,1,99999,2,28/12/2022,29/12/2022,false"})
    @DisplayName("Testa a atualizacao de uma RespostaLaboratorio com o pedidoId inexistente")
    @Order(30)
    void testUpdateRespostaLaboratorioPedidoIdInexistent(String data, String descricao, int respostaId, int utilizadorId, int pedidoId, int laboratorioId, String dataReservaInicio, String dataReservaFim, boolean aceite) throws ExecutionException, InterruptedException {
        RespostaLaboratorio r = new RespostaLaboratorio(data, descricao, respostaId, utilizadorId, pedidoId, laboratorioId, dataReservaInicio, dataReservaFim, aceite);
        String result = myService.updateRespostaLaboratorio(r);
        assertNull(result);
    }


    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0Update,2,30/12/22,31/12/22,10,1,false", "01/01/23,RespostaMaterial1Update,3,02/01/23,04/01/23,10,1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial")
    @Order(31)
    void testUpdateRespostaMaterial(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertEquals("RespostaMaterial updated with:" + respostaId, result);
    }

    @ParameterizedTest
    @CsvSource({"24/12,RespostaMaterial0Update,2,30/12/22,31/12/22,1,0,false", "01  /  01  /  23,RespostaMaterial1Update,3,02/01/23,04/01/23,2,1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial com a data fora dos limites")
    @Order(32)
    void testUpdateRespostaMaterialDataOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,R,2,30/12/22,31/12/22,1,0,false", "01/01/23,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,3,02/01/23,04/01/23,2,1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial com a descricao fora dos limites")
    @Order(33)
    void testUpdateRespostaMaterialDescOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0Update,-1,30/12/22,31/12/22,1,0,false", "01/01/23,RespostaMaterial1Update,-5,02/01/23,04/01/23,2,1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial com o id da resposta menor que zero")
    @Order(34)
    void testUpdateRespostaMaterialIdNegative(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0Update,2,30/12,31/12/22,1,0,false", "01/01/23,RespostaMaterial1Update,3,022 / 01111/2023,04/01/23,2,1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial com a dataReserva fora dos limites")
    @Order(35)
    void testUpdateRespostaMaterialReservaOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0Update,2,30/12/22,/12/22,1,0,false", "01/01/23,RespostaMaterial1Update,3,02/01/23,042 / 011 / 2023,2,1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial com a dataEntrega fora do limite")
    @Order(36)
    void testUpdateRespostaMaterialEntregaOutOfBounds(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,-1,0,false", "01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,9999,1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial com o id de utilizador/docente nao existente na base de dados")
    @Order(37)
    void testUpdateRespostaMaterialIdDocenteInexistent(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,1,0,false", "01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,2,1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial com materiais inexistentes ")
    @Order(38)
    void testUpdateRespostaMaterialIdMateriaisInexistent(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(-1);
        materiaisId.add(-998);
        materiaisId.add(999); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"24/12/22,RespostaMaterial0,2,30/12/22,31/12/22,1,9999,false", "01/01/23,RespostaMaterial1,3,02/01/23,04/01/23,2,-1,false"})
    @DisplayName("Testa a atualizacao de uma RespostaMaterial com pedidoId inexistente ")
    @Order(38)
    void testUpdateRespostaMaterialPedidoIdInexistent(String data, String descricao, int respostaId, String dataReserva, String dataEntrega, int utilizadorId, int pedidoId, boolean aceite) throws ExecutionException, InterruptedException {
        ArrayList<Integer> materiaisId = new ArrayList<>();
        materiaisId.add(1);
        materiaisId.add(2);
        materiaisId.add(3); //tem de ter obrigatoriamente pelo menos um destes materiais na base de dados

        RespostaMaterial r = new RespostaMaterial(data, descricao, respostaId, dataReserva, dataEntrega, utilizadorId, pedidoId, materiaisId, aceite);
        String result = myService.updateRespostaMaterial(r);
        assertNull(result);
    }

    //RespostaUtilizador

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0Update,4,true,10,2,Ruan carlosChanged7", "10/10/2010,RespostaUtilizador1Update,5,true,10,2,Ruan carlosChanged8"})
    @DisplayName("Testa a atualizacao de uma RespostaUtilizador")
    @Order(39)
    void testUpdateRespostaUtilizador(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.updateRespostaUtilizador(r);
        assertEquals("RespostaUtilizador updated with:" + respostaId, result);
    }

    @ParameterizedTest
    @CsvSource({"2010,RespostaUtilizador0Update,4,true,1,2,Ruan carlosChanged7", "101010/101/2010,RespostaUtilizador1Update,5,true,2,3,Ruan carlosChanged8"})
    @DisplayName("Testa a atualizacao de uma RespostaUtilizador com a data fora dos limites")
    @Order(40)
    void testUpdateRespostaUtilizadorDataOutOfBounds(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.updateRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,R,4,true,1,2,Ruan carlosChanged7", "10/10/2010,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,5,true,2,3,Ruan carlosChanged8"})
    @DisplayName("Testa a atualizacao de uma RespostaUtilizador com a descricao fora dos limites")
    @Order(41)
    void testUpdateRespostaUtilizadorDescOutOfBounds(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.updateRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0Update,-1,true,1,2,Ruan carlosChanged7", "10/10/2010,RespostaUtilizador1Update,-5,true,2,3,Ruan carlosChanged8"})
    @DisplayName("Testa a atualizacao de uma RespostaUtilizador com respostaId menor que zero")
    @Order(42)
    void testUpdateRespostaUtilizadorIdNegative(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.updateRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0Update,4,true,-1,2,Ruan carlosChanged7", "10/10/2010,RespostaUtilizador1Update,5,true,9999,3,Ruan carlosChanged8"})
    @DisplayName("Testa a atualizacao de um RespostaUtilizador com utilizadorId inexistente na base de dados")
    @Order(43)
    void testUpdateRespostaUtilizadorIdDocenteInexistent(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.updateRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0Update,4,true,1,2,Ruan carlosChanged72", "10/10/2010,RespostaUtilizador1Update,2,true,2,3,r"})
    @DisplayName("Testa a atualizacao de uma RespostaUtilizador com o nome do utilizador/docente inexistente na base de dados")
    @Order(44)
    void testUpdateRespostaUtilizadorNomeDocenteInexistent(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.updateRespostaUtilizador(r);
        assertNull(result);
    }

    @ParameterizedTest
    @CsvSource({"10/10/2010,RespostaUtilizador0Update,4,true,1,-2,Ruan carlosChanged7", "10/10/2010,RespostaUtilizador1Update,2,true,2,999,Ruan carlosChanged8"})
    @DisplayName("Testa a atualizacao de uma RespostaUtilizador com o pedidoIde inexistente na base de dados")
    @Order(44)
    void testUpdateRespostaUtilizadorPedidoIdInexistent(String data, String descricao, int respostaId, boolean aceite, int utilizadorId, int pedidoId) throws ExecutionException, InterruptedException {
        RespostaUtilizador r = new RespostaUtilizador(data, descricao, respostaId, aceite, utilizadorId, pedidoId);
        String result = myService.updateRespostaUtilizador(r);
        assertNull(result);
    }


    //Testes GetTipo

    @Test
    @DisplayName("Testa se as respostas de laboratorio foram criadas")
    @Order(45)
    void testGetRespostaLaboratorioSize() throws ExecutionException, InterruptedException {
        assertNotEquals(0, myService.getRespostaLaboratorio().size(), "Nenhuma resposta de Laboratorio encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas de laboratorio nao sao null")
    @Order(46)
    void testGetRespostaLaboratorioNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getRespostaLaboratorio());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DisplayName("Testa se as respostas sao apenas do tipo laboratorio")
    @Order(47)
    void testGetRespostaLaboratorioType(int i) throws ExecutionException, InterruptedException {
        //RespostaLaboratorio tem tipo 1
        assertEquals(1, myService.getRespostaLaboratorio().get(i).getTipoResposta());
    }

    @Test
    @DisplayName("Testa se as respostas de material foram criadas")
    @Order(48)
    void testGetRespostaMaterialSize() throws ExecutionException, InterruptedException {
        assertNotEquals(0, myService.getRespostaMaterial().size(), "Nenhuma resposta de Material encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas de material nao sao null")
    @Order(49)
    void testGetRespostaMaterialNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getRespostaMaterial());
    }


    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    @DisplayName("Testa se as respostas sao apenas do tipo material")
    @Order(50)
    void testGetRespostaMaterialType(int i) throws ExecutionException, InterruptedException {
        //RespostaMaterial tem tipo 2
        assertEquals(2, myService.getRespostaMaterial().get(i).getTipoResposta());
    }

    @Test
    @DisplayName("Testa se as respostas de utilizador foram criadas")
    @Order(51)
    void testGetRespostaUtilizadorSize() throws ExecutionException, InterruptedException {
        assertNotEquals(0, myService.getRespostaUtilizador().size(), "Nenhuma resposta de Utilizador encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas de utilizador nao sao null")
    @Order(52)
    void testGetRespostaUtilizadorNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getRespostaUtilizador());
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 5})
    @DisplayName("Testa se as respostas sao apenas do tipo Utilizador")
    @Order(53)
    void testGetRespostaUtilizadorType(int i) throws ExecutionException, InterruptedException {
        //RespostaMaterial tem tipo 0
        assertEquals(0, myService.getRespostaUtilizador().get(i).getTipoResposta());
    }

    //Testes GetAll

    @Test
    @DisplayName("Testa se as respostas foram criadas, presumindo que as respostas ja foram criadas")
    @Order(54)
    void testGetAllRespostasSize() throws ExecutionException, InterruptedException {
        assertNotEquals(0, myService.getAllRespostas().size(), "Nenhuma resposta encontrada");
    }

    @Test
    @DisplayName("Testa se as respostas nao sao null, presumindo que as respostas ja foram criadas")
    @Order(55)
    void testGetAllRespostasNotNull() throws ExecutionException, InterruptedException {
        assertNotNull(myService.getAllRespostas(), "Nenhuma resposta encontrada");
    }

    //Testes delete
    @ParameterizedTest
    @ValueSource(ints = {3, 5, 1})
    @DisplayName("Testa se e possivel apagar uma resposta")
    @Order(56)
    void testDeleteResposta(int id) throws ExecutionException, InterruptedException {
        assertEquals("RespostaUtilizador deleted with:" + id, myService.deleteResposta(id));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 9999})
    @DisplayName("Testa se e possivel apagar uma resposta inexistente")
    @Order(57)
    void testDeleteRespostaInexistent(int id) throws ExecutionException, InterruptedException {
        assertNull(myService.deleteResposta(id));
    }


    // TODO: 26/12/2022 TESTAR LIMITES DE CRIAÇÃO DE RESPOSTAS PARA OS 3
    // TODO: 26/12/2022 TESTAR LIMITES NA ATUALIZAÇÃO DE RESPOSTAS PARA OS 3
    // TODO: 26/12/2022 TESTAR SE APAGAR PEDIDOS ACEITA NEGATIVOS
}
