package com.example.test.demo.service;

import com.example.test.demo.model.Laboratorio;
import com.example.test.demo.model.Material;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MaterialService {

    @Autowired
    private LaboratorioService laboratorioService;


    public static final String COL_NAME = "material";
    public static final String COL_NAME_LABORATORIO = "laboratorio";



    /**
     * Metodo para inserir um material na base de dados
     *
     * @param mat Material a receber para ser criado
     * @return material created ou null em caso de falha
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String createMaterial(Material mat,int idLaboratorio) throws InterruptedException, ExecutionException {
        if(mat.getMaterialId()<0)
        {
            return null;
        }else if(checkAll(mat))
        {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();

         //Verificar se o laboratorio existe na base de dados
        ApiFuture<QuerySnapshot> future2 = db.collection(COL_NAME_LABORATORIO).whereEqualTo("laboratorioId", idLaboratorio).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if (documents2.isEmpty())
            return null;

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Material oldMat = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldMat = doc.toObject(Material.class);
            if (oldMat.getMaterialId() > biggest) {
                biggest = oldMat.getMaterialId();

            }
        }
        mat.setMaterialId(biggest + 1);

        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(mat);

        /*ADICIONA O MATERIAL AO LABORATORIO*/
        laboratorioService.addMaterialToLab(idLaboratorio,mat.getMaterialId());

        return "material created";
    }


    /**
     * Metodo para eliminar um material da base de dados
     *
     * @param id Identificacao do material
     * @return deleted material with: materialId ou null em caso de falha
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public String deleteMateriais(int id) throws InterruptedException, ExecutionException {
        if (id < 0) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;

        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return "deleted material with:" + id;
    }


    /**
     * Metodo para atualizar um material existente na base de dados
     *
     * @param mat Material que ira substituir o material a atualizar
     * @return material updated with: materialId ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateMat(Material mat) throws ExecutionException, InterruptedException {
        if (mat.getMaterialId() < 0) {
            return null;
        } else if (checkAll(mat)) {
            return null;
        }

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", mat.getMaterialId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);
        return "material updated with:" + mat.getMaterialId();

    }

    /**
     * Metodo para retornar uma lista de materiais
     *
     * @return lista de materiais
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Material> getAllMateriais() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Material> mats = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot doc : documents) {
                mats.add(doc.toObject(Material.class));
            }
            return mats;
        }
        return null;
    }

    /*CASOS PARTICULARES*/
    /*
     * ADICIONAR RESPOSTA AO MATERIAL
     * ALTERAR RESPOSTA NO MATERIAL
     * ADICIONA UMA UC NA QUAL O MATERIAL PODE SER USADO
     * ALTERA UMA UC NA QUAL O MATERIAL PODE SER USADO
     * APAGA UMA UC NA QUAL O MATERIAL PDOE SER USADO*/

    public String addRespostaToMaterial(int idResposta, int idMaterial) throws ExecutionException, InterruptedException {
        System.out.println("ID Material: " + idMaterial);
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idMaterial).get();
        if (future.get().isEmpty())
            return null;
        Material mat = future.get().getDocuments().get(0).toObject(Material.class);
        mat.getRespostasMaterial().add(idResposta);
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);
        System.out.println("Resposta adicionada ao material");
        System.out.println(mat.getRespostasMaterial());
        System.out.println(db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).get().get().get("respostasMaterial").toString());
        return "resposta added to material with id:" + idMaterial;
    }

    public String updateRespostaToMaterial(int idResposta, int idMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idMaterial).get();
        if (future.get().size() <= 0)
            return "No elements to be queried";
        Material mat = future.get().getDocuments().get(0).toObject(Material.class);
        mat.getRespostasMaterial().remove(mat.getRespostasMaterial().indexOf(idResposta));
        mat.getRespostasMaterial().add(idResposta);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);
        return apiFuture.get().getUpdateTime().toString();
    }

    public String createUcToMaterial(String idUc, int idMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idMaterial).get();
        if (future.get().size() <= 0)
            return "No elements to be queried";
        Material mat = future.get().getDocuments().get(0).toObject(Material.class);
        mat.getUnidadesCurriculares().add(idUc);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);
        return apiFuture.get().getUpdateTime().toString();
    }

    public String updateUcToMaterial(String idUc, int idMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idMaterial).get();
        if (future.get().size() <= 0)
            return "No elements to be queried";
        Material mat = future.get().getDocuments().get(0).toObject(Material.class);
        mat.getUnidadesCurriculares().remove(mat.getUnidadesCurriculares().indexOf(idUc));
        mat.getUnidadesCurriculares().add(idUc);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);
        return apiFuture.get().getUpdateTime().toString();
    }

    public String deleteUcToMaterial(String idUc, int idMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idMaterial).get();
        if (future.get().size() <= 0)
            return "No elements to be queried";
        Material mat = future.get().getDocuments().get(0).toObject(Material.class);
        mat.getUnidadesCurriculares().remove(mat.getUnidadesCurriculares().indexOf(idUc));
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);
        return apiFuture.get().getUpdateTime().toString();
    }

    //metodos auxiliares

    /**
     * Metodo para auxiliar na verificacao da criacao e atualizacao dos materiais
     *
     * @param material material a receber como parametro para ser verificado
     * @return
     */
    public boolean checkAll(Material material) {
        if (material.getDescricao() == null
                || material.getDescricao().equalsIgnoreCase("")
                || material.getDescricao().length() < 3
                || material.getDescricao().length() > 128
                || (material.isAvariado() && material.isDisponivel())) {
            return true;
        } else if (material.getEtiquetaId() < 0) {
            return true;
        }
        if(material.getUnidadesCurriculares().size()>0)
        {
        for (int i = 0; i < material.getUnidadesCurriculares().size(); i++) {
            if (material.getUnidadesCurriculares().get(i) == null
                    || material.getUnidadesCurriculares().get(i).equalsIgnoreCase("")
                    || material.getUnidadesCurriculares().get(i).length() < 4
                    || material.getUnidadesCurriculares().get(i).length() > 128) {
                return true;
            }
          }
        }

        if(material.getRespostasMaterial().size()>0){
        for (int i = 0; i < material.getRespostasMaterial().size(); i++) {
            if (material.getRespostasMaterial().get(i) == null
                    || material.getRespostasMaterial().get(i) < 0) {
                return true;
            }
          }
        }
        return false;
    }

}
