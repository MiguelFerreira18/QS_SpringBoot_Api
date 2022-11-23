package com.example.test.demo.service;

import com.example.test.demo.model.Material;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MaterialService {

    public static final String COL_NAME = "material";


    public String saveMat(Material mat) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
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

        return colApiFuture.get().getUpdateTime().toString();
    }


    public String deleteMat(int id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot>  future= db.collection(COL_NAME).whereEqualTo("materialId",id).get();
        if (future.get().size()<=0)
            return "Material não encontrado para ser eleminado";
        System.out.println(db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()));
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }


    public String updateMat(Material mat) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", mat.getMaterialId()).get();
        //update a document from firestore
        if(future.get().size()<=0)
            return "No elements to be queried";
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);

        return apiFuture.get().getUpdateTime().toString();

    }

    public List<Material> getMat() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Material> mats = new ArrayList<>();
        if(documents.size()>0){
            for (QueryDocumentSnapshot doc: documents){
                mats.add(doc.toObject(Material.class));
            }
            return mats;
        }
        return null;
    }

    /*CASOS PARTICULARES*/
    /*
    * ADICIONAR RESPOSTA AO MATERIAL
    * ALTERAR RESPOSTA NO MATERIAL*/

    public String createRespostaToMaterial(int idResposta,int idMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idMaterial).get();
        if(future.get().size()<=0)
            return "No elements to be queried";
        Material mat = future.get().getDocuments().get(0).toObject(Material.class);
        mat.getRespostasMaterial().add(idResposta);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);
        return apiFuture.get().getUpdateTime().toString();
    }
    public String updateRespostaToMaterial(int idResposta,int idMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idMaterial).get();
        if(future.get().size()<=0)
            return "No elements to be queried";
        Material mat = future.get().getDocuments().get(0).toObject(Material.class);
        mat.getRespostasMaterial().remove(mat.getRespostasMaterial().indexOf(idResposta));
        mat.getRespostasMaterial().add(idResposta);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(mat);
        return apiFuture.get().getUpdateTime().toString();
    }

}
