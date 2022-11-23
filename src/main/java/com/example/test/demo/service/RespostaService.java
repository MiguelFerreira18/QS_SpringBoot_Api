package com.example.test.demo.service;

import com.example.test.demo.model.Docente;
import com.example.test.demo.model.Pedido;
import com.example.test.demo.model.Resposta;
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
public class RespostaService {

    private static final String COL_NAME = "resposta";

    public List<Resposta> getAllRespostas() throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Resposta> respostas = new ArrayList<>();
        if(documents.size()>0){
            for (QueryDocumentSnapshot doc: documents){
                respostas.add(doc.toObject(Resposta.class));
            }
            return respostas;
        }
        return null;
    }
    public String createResposta(Resposta resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Resposta oldResposta = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldResposta = doc.toObject(Resposta.class);
            if (oldResposta.getRespostaId() > biggest) {
                biggest = oldResposta.getRespostaId();

            }
        }
        oldResposta.setRespostaId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(oldResposta);
        return colApiFuture.get().getUpdateTime().toString();
    }
    public String deleteResposta(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", id).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser eleminada";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }

    public String updateResposta(Resposta resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", resposta.getRespostaId()).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser atualizada";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return writeResult.get().getUpdateTime().toString();
    }




}
