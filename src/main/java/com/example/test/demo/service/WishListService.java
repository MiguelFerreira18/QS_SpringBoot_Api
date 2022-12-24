package com.example.test.demo.service;


import com.example.test.demo.model.Material;
import com.example.test.demo.model.Resposta;
import com.example.test.demo.model.RespostaLaboratorio;
import com.example.test.demo.model.Wish;
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
public class WishListService {
    private static final String COL_NAME = "wishList";

    public String createWish(Wish wish) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Wish oldWish = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldWish = doc.toObject(Wish.class);
            if (oldWish.getIdWish() > biggest) {
                biggest = oldWish.getIdWish();

            }
        }
        wish.setIdWish(biggest + 1);
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().create(wish);
        return colApiFuture.get().getUpdateTime().toString();

    }
    public String deleteWish(int idWish) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot>  future= db.collection(COL_NAME).whereEqualTo("idWish",idWish).get();
        if (future.get().size()<=0)
            return "Wish não encontrada para ser eleminado";
        System.out.println(db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()));
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }

    public List<Wish> getWishList() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream().map((document) -> document.toObject(Wish.class)).toList();
    }

    public String updateWish(Wish wish) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("idWish", wish.getIdWish()).get();
        //update a document from firestore
        if(future.get().size()<=0)
            return "No elements to be queried";
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(wish);

        return apiFuture.get().getUpdateTime().toString();

    }


}
