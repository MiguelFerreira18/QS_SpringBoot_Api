package com.example.test.demo.service;

import com.example.test.demo.model.Material;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MaterialServiceTest
{

    private Firestore db;
    private String COL_NAME = "material";
    // TODO: 29/12/2022 muda os nomes das variaveis para um nome completo para ser mais facil de ler

    @Autowired
    private MaterialService myService;
        @BeforeEach
        void startDB()
        {
            db = FirestoreClient.getFirestore();
        }
        @ParameterizedTest
        @CsvSource({"carrinho,true,false,1,1,3","helicoptero,true,false,2,2,3"})
        @Order(1)
        @DisplayName("Deve testar se um material e criado na base de dados")
        void testCreateNormalMat(String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId,int labId) throws ExecutionException, InterruptedException
        {
            Material m = new Material(descricao,isDisponivel,isAvariado,materialId,etiquetaId);
            String result = myService.createMaterial(m,labId);
            assertNotNull(result);
        }

        @ParameterizedTest
        @CsvSource({"materialNeg,true,false,-1,1,0","materialNeg2,true,false,-2,2,0"})
        @Order(2)
        @DisplayName("Deve testar se e possivel criar um material com menor que 0")
        void testCreateMatNegativeId(String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId,int labId) throws ExecutionException, InterruptedException
        {
            Material m = new Material(descricao,isDisponivel,isAvariado,materialId,etiquetaId);
            String result = myService.createMaterial(m,labId);
            assertNull(result);
        }

        @ParameterizedTest
        @CsvSource({"u,true,false,11,1,0","aaaaatxnjevwxmxagqacnklyoxgqigwpfsqujmwagnevakjvxtezitkjxkxrpgayowlxcncftoniumpueqollqmnlodopxfryzqhowesztcbfldwznldncohghvmiooalixf,true,false,12,2,0",",true,false,13,1,0",",true,false,14,1,0"})
        @Order(3)
        @DisplayName("Deve testar se e possivel criar um material com descricao fora dos limites")
        void testCreateMatDescOutOfBonds(String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId,int labId) throws ExecutionException, InterruptedException
        {
            Material m = new Material(descricao,isDisponivel,isAvariado,materialId,etiquetaId);
            String result = myService.createMaterial(m,labId);
            assertNull(result);
        }

        @ParameterizedTest
        @CsvSource({"avariadoDispo,true,true,10,1,3","avariadoDispo2,true,true,11,2,3"})
        @Order(4)
        @DisplayName("Deve testar se e possivel criar um material avariado e disponivel")
        void testCreateMatAvariadoDisponivel(String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId,int labId) throws ExecutionException, InterruptedException
        {
            Material m = new Material(descricao,isDisponivel,isAvariado,materialId,etiquetaId);
            String result = myService.createMaterial(m,labId);
            assertNull(result);
        }

        @ParameterizedTest
        @CsvSource({"etiquetaNeg,true,true,10,-1,0","etiquetaNeg2,true,true,11,-2,0"})
        @Order(5)
        @DisplayName("Deve testar se e possivel criar um material com etiqueta menor que zero")
        void testCreateMatEtiquetaOutOfBonds(String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId,int labId) throws ExecutionException, InterruptedException
        {
            Material m = new Material(descricao,isDisponivel,isAvariado,materialId,etiquetaId);
            String result = myService.createMaterial(m,labId);
            assertNull(result);
        }

        @Test
        @Order(6)
        @DisplayName("Deve testar se o material esta armezenado na base de dados")
        void testSaveMatDb() throws ExecutionException, InterruptedException
        {
            //Verifica se exite um material de id 1 na base de dados
            ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId",1).get();
            assertNotEquals(0,future.get().size());
        }

        @ParameterizedTest
        @CsvSource({"carrinhoT1,false,false,0,6,3","carrinhoT2,true,false,0,6,3"})
        @Order(7)
        @DisplayName("Deve testar se e possivel criar materiais com o mesmo id")
        void testCreateMatSameId( String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId,int labId) throws ExecutionException,InterruptedException
        {
            Material m = new Material(descricao,isDisponivel,isAvariado,materialId,etiquetaId);
            myService.createMaterial(m,labId);

            //A base de dados ja contem 2 objetos do tipo material inseridos anteriormente
            ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId",0).get();
            //Se future.get().size() for diferente de 1 significa que criou materiais com o mesmo id, o que nao pode acontecer
            assertEquals(1,future.get().size());
        }

        @ParameterizedTest
        @CsvSource({"0","1"})
        @Order(10)
        @DisplayName("Deve testar se da para remover um material da base de dados")
        void testDeleteMat(int id) throws ExecutionException, InterruptedException
        {
            //funciona mas nao esquecer que caso se adicione um material e apague-se logo de seguida, o contador comecara em zero outra vez
            Material m = new Material("droneCom8char"+id,true,false,id,2);
            myService.createMaterial(m,0);
            assertEquals("deleted material with:"+id,myService.deleteMateriais(id));
        }
    @Test
    @Order(9)
    @DisplayName("Deve testar se da para eliminar um material inexistente na base de dados")
    void testDeleteMatInexistent() throws ExecutionException, InterruptedException
    {
        assertNull(myService.deleteMateriais(-1));
    }

    @Test
    @Order(8)
    @DisplayName("Testa se e possivel atualizar um material existente ")
    void testUpdateMat() throws ExecutionException, InterruptedException
    {
        Material m = new Material("droneComMaisChars",true,false,0,6);
        myService.createMaterial(m,0);

        Material h = new Material("droneComAlgunsChars",false,true,0,6);
        assertEquals("material updated with:" + 0,myService.updateMat(h));
    }

    @Test
    @Order(11)
    @DisplayName("Testa se e possivel atualizar um material inexistente")
    void testUpdateMatInexistent() throws ExecutionException, InterruptedException
    {
        Material m = new Material("droneDentroDolimite",true,false,-20,6);
        assertNull(myService.updateMat(m));
    }

    @ParameterizedTest
    @CsvSource({"a,false,false,2,6","aaaaatxnjevwxmxagqacnklyoxgqigwpfsqujmwagnevakjvxtezitkjxkxrpgayowlxcncftoniumpueqollqmnlodopxfryzqhowesztcbfldwznldncohghvmiooalixf,false,false,3,6"})
    @Order(12)
    @DisplayName("Testa se e possivel atualizar um material com descricao fora dos limites")
    void testUpdateMatDescOutOfBonds(String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId) throws ExecutionException, InterruptedException
    {
        //nao esuqecer q para realizar o teste, os materiais de id 2 e 3 tem de existir na bd
        Material m = new Material(descricao,isDisponivel,isAvariado,materialId,etiquetaId);
        assertNull(myService.updateMat(m));
    }

    @ParameterizedTest
    @CsvSource({"etiquetaerrada1,false,false,2,-6","etiquetaerrada2,false,false,3,-4"})
    @Order(13)
    @DisplayName("Testa se e possivel atualizar um material com etiqueta fora dos limites")
    void testUpdateMatEtiquetaOutOfBonds(String descricao, boolean isDisponivel, boolean isAvariado, int materialId, int etiquetaId) throws ExecutionException, InterruptedException
    {
        //nao esuqecer q para realizar o teste, os materiais de id 2 e 3 tem de existir na bd
        Material m = new Material(descricao,isDisponivel,isAvariado,materialId,etiquetaId);
        assertNull(myService.updateMat(m));
    }

    @Test
    @Order(14)
    @DisplayName("Testa se e possivel atualizar um material avariado e disponivel")
    void testUpdateMatAvariadoDisponivel() throws ExecutionException, InterruptedException
    {
        //nao esquecer q para realizar o teste, o material tem de ter id 2
        Material m = new Material("droneCom8char",true,true,2,2);
        assertNull(myService.updateMat(m));
    }

    @Test
    @Order(15)
    @DisplayName("Testa se vem null ao procurar todos os materiais, presumindo que ha materiais")
    void testGetMatNotNull() throws ExecutionException, InterruptedException
    {
        assertNotNull(myService.getAllMateriais());
    }

    @Test
    @Order(16)
    @DisplayName("Testa se o tamanho do array de materiais e maior que zero")
    void testGetAllMatsSize() throws ExecutionException, InterruptedException
    {
        assertNotEquals(0,myService.getAllMateriais());
    }
    // TODO: 26/12/2022 TESTAR SE LIMITES DE CRIAÇÃO DE MATERIAIS ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE LIMITES DE NA ATUALIZAÇÃO DE MATERIAIS ESTÃO A FUNCIONAR
    // TODO: 26/12/2022 TESTAR SE O ID PODE SER MENOR QUE 0

}