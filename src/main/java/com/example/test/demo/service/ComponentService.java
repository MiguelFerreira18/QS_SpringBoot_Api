package com.example.test.demo.service;

import com.example.test.demo.model.Componente;
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
public class ComponentService {
    private static final String COL_NAME = "component";

    public String createComponent(Componente componente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Componente oldComp = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldComp = doc.toObject(Componente.class);
            if (oldComp.getId() > biggest) {
                biggest = oldComp.getId();

            }
        }
        componente.setId(biggest + 1);
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(componente);

        return "componente created";
    }
    public List<Componente> getAllComponents() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Componente> componentes = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot doc : documents) {
                componentes.add(doc.toObject(Componente.class));
            }
            return componentes;
        }
        return null;
    }
    public String updateComponent(Componente componente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(componente);
        return colApiFuture.get().getUpdateTime().toString();
    }
    public String deleteComponent(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(String.valueOf(id)).delete();
        return "Document with ID " + id + " has been deleted";
    }
}
