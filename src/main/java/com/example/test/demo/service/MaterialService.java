package com.example.test.demo.service;

import com.example.test.demo.interfaces.ServicesMat;
import com.example.test.demo.model.Material;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MaterialService implements ServicesMat {

    public static final String COL_NAME = "material";

    @Override
    public String saveMat(Material mat) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();


        int bigest = -1;
        Material oldMat = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
             oldMat = doc.toObject(Material.class);
            if (oldMat.getMaterialId() > bigest) {
                bigest = oldMat.getMaterialId();
            }
        }
        mat.setMaterialId(bigest + 1);
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(mat);


        return colApiFuture.get().getUpdateTime().toString();

    }

    @Override
    public void deleteMat() {

    }

    @Override
    public void updateMat() {

    }

    @Override
    public void getMat() {

    }
}
