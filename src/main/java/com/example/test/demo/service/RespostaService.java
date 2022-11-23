package com.example.test.demo.service;

import com.example.test.demo.model.*;
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
    /*RESPOSTA LABORATORIO*/
  /*  public List<RespostaLaboratorio> getAllRespostasLaboratorio() throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<RespostaLaboratorio> respostas = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot doc : documents) {
                respostas.add(doc.toObject(RespostaLaboratorio.class));
            }
            return respostas;
        }
        return null;
    }*/

    public String createRespostaLaboratorio(RespostaLaboratorio resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Resposta oldResposta = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldResposta = doc.toObject(RespostaLaboratorio.class);
            if (oldResposta.getRespostaId() > biggest) {
                biggest = oldResposta.getRespostaId();

            }
        }
        oldResposta.setRespostaId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(oldResposta);
        return colApiFuture.get().getUpdateTime().toString();
    }

    public String updateRespostaLaboratorio(RespostaLaboratorio resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", resposta.getRespostaId()).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser atualizada";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return writeResult.get().getUpdateTime().toString();
    }

    /*RESPOSTA MATERIAL*/
//    public List<RespostaMaterial> getAllRespostasMaterial() throws ExecutionException, InterruptedException {
//
//        Firestore db = FirestoreClient.getFirestore();
//        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        List<RespostaMaterial> respostas = new ArrayList<>();
//        if (documents.size() > 0) {
//            for (QueryDocumentSnapshot doc : documents) {
//                respostas.add(doc.toObject(RespostaMaterial.class));
//            }
//            return respostas;
//        }
//        return null;
//    }

    public String createRespostaMaterial(RespostaMaterial resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Resposta oldResposta = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldResposta = doc.toObject(RespostaLaboratorio.class);
            if (oldResposta.getRespostaId() > biggest) {
                biggest = oldResposta.getRespostaId();

            }
        }
        oldResposta.setRespostaId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(oldResposta);
        return colApiFuture.get().getUpdateTime().toString();
    }

    public String updateRespostaMaterial(RespostaMaterial resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", resposta.getRespostaId()).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser atualizada";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return writeResult.get().getUpdateTime().toString();
    }

    /*RESPOSTA UTILIZADOR*/
  /*  public List<RespostaUtilizador> getAllRespostasUtilizador() throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<RespostaUtilizador> respostas = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot doc : documents) {
                respostas.add(doc.toObject(RespostaUtilizador.class));
            }
            return respostas;
        }
        return null;
    }*/

    public String createRespostaUtilizador(RespostaUtilizador resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Resposta oldResposta = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldResposta = doc.toObject(RespostaUtilizador.class);
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

    public String updateRespostaUtilizador(RespostaUtilizador resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", resposta.getRespostaId()).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser atualizada";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return writeResult.get().getUpdateTime().toString();
    }
    /*CASOS PARTICULARES*/
    /*
     *ADICIONA MATERIAL A UMA RESPOSTA
     *LER TODOS OS MATERIAIS DE UMA RESPOSTA
     *
     */


    /**
     * @param respostaId
     * @param materialId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addMaterialToResposta(int respostaId, int materialId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", respostaId).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser atualizada";
        RespostaMaterial resposta = future.get().getDocuments().get(0).toObject(RespostaMaterial.class);
        resposta.getMateriaisId().add(materialId);
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return writeResult.get().getUpdateTime().toString();

    }

    /**
     * @param respostaId
     * @param materialId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteMaterialFromResposta(int respostaId, int materialId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", respostaId).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser atualizada";
        RespostaMaterial resposta = future.get().getDocuments().get(0).toObject(RespostaMaterial.class);
        resposta.getMateriaisId().remove(materialId);
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return writeResult.get().getUpdateTime().toString();
    }

    /**
     * @param respostaId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Integer> getMateriaisFromResposta(int respostaId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", respostaId).get();
        if (future.get().size() <= 0)
            return null;
        RespostaMaterial resposta = future.get().getDocuments().get(0).toObject(RespostaMaterial.class);
        return resposta.getMateriaisId();
    }




}
