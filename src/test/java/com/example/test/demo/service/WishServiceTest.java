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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
    @CsvSource(value = {"0,nomeMat1,descMat1,10,24/12/2022", "1,nomeMat2,descMat2,11,24/12/2022", "2,nomeMat3,descMat3,12,24/12/2022" })
    @Order(1)
    public void shouldTestCreateWishIsSent(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.createWish(wish);
        assertNotNull(result, result);
    }


    @ParameterizedTest
    @DisplayName("Testa se existe uma wish com o id especificado")
    @ValueSource(ints = {0, 1, 2})
    @Order(2)
    public void shouldTestCreateWishIsInDataBase(int id) throws Exception {
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("idWish", id).get();
        int size = future.get().getDocuments().size();
        assertNotEquals(0, size, "Tamanho é:" + size);
    }


    @Test
    @DisplayName("Testa se vem null ao procurar todas as wishes, presumindo que existem wishes")
    @Order(3)
    public void shouldTestIfGetAllWishesIsNotNull() throws Exception {
        assertNotNull(myService.getWishList());
    }

    @Test
    @DisplayName("Testa se getAllWishes não tem resultado menor que zero")
    @Order(4)
    public void shouldTestIfGetAllWishesHasNotLessThanZero() throws Exception {
        assertTrue(myService.getWishList().size() >= 0);
    }

    //Wish não dá erro ao fazer update de id que não existe, mas não adiciona na base de dados
    //Basicamente só é preciso colocar um aviso?

    @ParameterizedTest
    @DisplayName("Testa se é realizado um update na wish ao receber uma wish com o mesmo id")
    @CsvSource(value = {"0,nomeMat1UPDATED,descMat1UPDATED,15,24/12/2022", "1,nomeMat2UPDATED,descMat2UPDATED,15,24/12/2022", "2,nomeMat3UPDATED,descMat3UPDATED,15,24/12/2022" })
    @Order(5)
    public void shouldTestIfUpdateWishWorks(int id, String nomeMaterial, String descMaterial, int idDocente, String date) throws Exception {
        Wish wish = new Wish(id, nomeMaterial, descMaterial, idDocente, date);
        String result = myService.updateWish(wish);
        assertNotNull(result, result);
    }


    @ParameterizedTest
    @DisplayName("Testa se é possível apagar uma wish, presumindo a sua existência com o id")
    @ValueSource(ints = {0, 1, 2})
    @Order(6)
    public void shouldTestIfDeleteWishWorks(int id) throws Exception {
        String result = myService.deleteWish(id);
        assertNotNull(result, result);
    }
}
