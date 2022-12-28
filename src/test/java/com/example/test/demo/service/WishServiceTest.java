package com.example.test.demo.service;

import com.example.test.demo.model.Wish;
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

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class WishServiceTest {
    @Autowired
    private WishListService myService;
    Firestore db;
    private static final String COL_NAME = "wishList";

    @BeforeEach
    void setUp()  {
        db = FirestoreClient.getFirestore();
    }


    @ParameterizedTest
    @DisplayName("Teste para inserir uma wish")
    @CsvSource(value = {"0,nomeMat1,descMat1,0,24/12/2022", "1,nomeMat2,descMat2,1,24/12/2022", "2,nomeMat3,descMat3,2,24/12/2022" })
    @Order(1)
    public void shouldTestCreateWishIsSent(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.createWish(wish);
        assertEquals(result,"wish created");
    }

    @ParameterizedTest
    @DisplayName("Teste para inserir uma wish com nome de material fora dos limites")
    @CsvSource(value = {"0,n,descMat1,0,24/12/2022", "1,nome,descMat2,1,24/12/2022", "2,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,descMat3,2,24/12/2022" })
    @Order(2)
    public void shouldTestCreateWishNomeOutOfBonds(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.createWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Teste para inserir uma wish com descricao de material fora dos limites")
    @CsvSource(value = {"0,nomeMat1,desc,0,24/12/2022", "1,nomeMat2,descMat,1,24/12/2022", "2,nomeMat3,bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb,2,24/12/2022" })
    @Order(3)
    public void shouldTestCreateWishDescOutOfBonds(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.createWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Teste para inserir uma wish com id menor que zero")
    @CsvSource(value = {"-1,nomeMat1,descMat1,0,24/12/2022", "-2,nomeMat2,descMat2,1,24/12/2022", "-3,nomeMat3,descMat3,2,24/12/2022"})
    @Order(4)
    public void shouldTestCreateWishIdNegatitve(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.createWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Teste para inserir uma wish com id de docente menor que zero")
    @CsvSource(value = {"0,nomeMat1,descMat1,-1,24/12/2022", "1,nomeMat2,descMat2,99999999,24/12/2022", "2,nomeMat3,descMat3,-3,24/12/2022" })
    @Order(5)
    public void shouldTestCreateWishIdDocenteInxistent(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.createWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Testa se existe uma wish com o id especificado")
    @ValueSource(ints = {0, 1, 2})
    @Order(6)
    public void shouldTestCreateWishIsInDataBase(int id) throws Exception {
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("idWish", id).get();
        int size = future.get().getDocuments().size();
        assertNotEquals(0, size, "Tamanho é:" + size);
    }

    @Test
    @DisplayName("Testa se vem null ao procurar todas as wishes, presumindo que existem wishes")
    @Order(7)
    public void shouldTestIfGetAllWishesIsNotNull() throws Exception {
        assertNotNull(myService.getAllWishes());
    }

    @Test
    @DisplayName("Testa se getAllWishes não tem resultado menor que zero")
    @Order(8)
    public void shouldTestIfGetAllWishesHasNotLessThanZero() throws Exception {
        assertTrue(myService.getAllWishes().size() >= 0);
    }

    //Wish não dá erro ao fazer update de id que não existe, mas não adiciona na base de dados
    //Basicamente só é preciso colocar um aviso?

    @ParameterizedTest
    @DisplayName("Testa se e realizado um update na wish ao receber uma wish com o mesmo id")
    @CsvSource(value = {"0,nomeMat1UPDATED,descMat1UPDATED,1,24/12/2022", "1,nomeMat2UPDATED,descMat2UPDATED,2,24/12/2022", "2,nomeMat3UPDATED,descMat3UPDATED,3,24/12/2022" })
    @Order(9)
    public void shouldTestIfUpdateWishWorks(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertEquals(result, "wish updated with:"+id,result);
    }

    @ParameterizedTest
    @DisplayName("Testa se é realizado um update numa wish inexistente")
    @CsvSource(value = {"-1,nomeMat1UPDATED,descMat1UPDATED,1,24/12/2022", "-2,nomeMat2UPDATED,descMat2UPDATED,2,24/12/2022", "-3,nomeMat3UPDATED,descMat3UPDATED,3,24/12/2022" })
    @Order(10)
    public void shouldTestUpdateWishInexistent(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Testa se é realizado um update na wish com o nome de material fora dos limites")
    @CsvSource(value = {"0,n,descMat1UPDATED,1,24/12/2022", "1,1234567,descMat2UPDATED,2,24/12/2022", "2,123123123123123123123123123123123,descMat3UPDATED,3,24/12/2022" })
    @Order(11)
    public void shouldTestUpdateWishNomeOutOfBounds(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Testa se e realizado um update na wish com a descricao de material fora dos limites")
    @CsvSource(value = {"0,nomeMat1UPDATED,n,1,24/12/2022", "1,nomeMat2UPDATED,1234567,2,24/12/2022", "2,nomeMat3UPDATED,123123123123123123123123123123123123123123123123123123123123123123,3,24/12/2022" })
    @Order(12)
    public void shouldTestUpdateWishDescOutOfBounds(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Testa se é realizado um update na wish com id menor que zero")
    @CsvSource(value = {"-1,nomeMat1UPDATED,descMat1UPDATED,1,24/12/2022", "-2,nomeMat2UPDATED,descMat2UPDATED,2,24/12/2022", "-3,nomeMat3UPDATED,descMat3UPDATED,3,24/12/2022" })
    @Order(13)
    public void shouldTestUpdateWishIdNegative(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Testa se é realizado um update na wish com o id de docente inexistente")
    @CsvSource(value = {"1,nomeMat1UPDATED,descMat1UPDATED,-1,24/12/2022", "2,nomeMat2UPDATED,descMat2UPDATED,-2,24/12/2022", "3,nomeMat3UPDATED,descMat3UPDATED,99999999,24/12/2022" })
    @Order(14)
    public void shouldTestUpdateWishIdDocenteInexistent(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Testa se é realizado um update na wish com a data fora dos limites ")
    @CsvSource(value = {"0,nomeMat1UPDATED,descMat1UPDATED,1,1", "1,nomeMat2UPDATED,descMat2UPDATED,2,24 / 12  /  2022", "2,nomeMat3UPDATED,descMat3UPDATED,3,-1" })
    @Order(15)
    public void shouldTestUpdateWishDataOutOfBonds(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertNull(result);
    }

    @ParameterizedTest
    @DisplayName("Testa se é possivel criar uma wish com data fora do limite")
    @CsvSource(value = {"0,nomeMat1UPDATED,descMat1UPDATED,1,1", "1,nomeMat2UPDATED,descMat2UPDATED,2,24 / 12  /  2022 ", "2,nomeMat3UPDATED,descMat3UPDATED,3,-1" })
    @Order(16)
    public void shouldTestCreateWishDataOutOfBonds(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertNull(result);
    }


    @ParameterizedTest
    @DisplayName("Testa se é possível apagar uma wish, presumindo a sua existência com o id")
    @ValueSource(ints = {0, 1, 2})
    @Order(17)
    public void shouldTestIfDeleteWishWorks(int id) throws Exception {
        String result = myService.deleteWish(id);
        assertEquals(result,"wish deleted with:" + id);
    }
    // TODO: 26/12/2022 TESTAR SE LIMITES DE CRIAÇÃO DE WISHES ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE LIMITES DE NA ATUALIZAÇÃO DE UM WISH ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE O ID PODE SER MENOR QUE 0
}
