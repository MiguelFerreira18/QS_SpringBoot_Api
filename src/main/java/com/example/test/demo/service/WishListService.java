package com.example.test.demo.service;


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

        /*ADICIONA UM NOVO WISH*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().create(wish);
        return colApiFuture.get().getUpdateTime().toString();
    }
    public String deleteWish(String idWish) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(idWish).delete();
        return "Document with Wish ID " + idWish + " has been deleted at " + writeResult.get().getUpdateTime();
    }

    public List<Wish> getWishList() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream().map((document) -> document.toObject(Wish.class)).toList();
    }

}
