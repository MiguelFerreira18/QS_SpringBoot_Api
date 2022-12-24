package com.example.test.demo.service;

import com.example.test.demo.model.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DataSnapshot;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class RespostaService {

    private static final String COL_NAME = "resposta";
    private static final String PATH_QUARY_DOCENTE = "docente";


    /*TODAS AS RESPOSTAS */
    public List<Object> getAllRespostas() throws ExecutionException, InterruptedException {
        List<Object> respostas = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            Object resposta = document.toObject(Object.class);
            respostas.add(resposta);
        }
        return respostas;
    }


    /*RESPOSTA LABORATORIO*/
    public List<RespostaLaboratorio> getRespostaLaboratorio() throws ExecutionException, InterruptedException {
        List<RespostaLaboratorio> respostasLaboratorio = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            RespostaLaboratorio resposta = document.toObject(RespostaLaboratorio.class);
            respostasLaboratorio.add(resposta);
        }
        return respostasLaboratorio;
    }


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
        resposta.setRespostaId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(resposta);
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
    public List<RespostaMaterial> getRespostaMaterial() throws ExecutionException, InterruptedException {
        List<RespostaMaterial> respostasMaterial = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            RespostaMaterial resposta = document.toObject(RespostaMaterial.class);
            respostasMaterial.add(resposta);
        }
        return respostasMaterial;
    }


    public String createRespostaMaterial(RespostaMaterial resposta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Resposta oldResposta = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldResposta = doc.toObject(RespostaMaterial.class);
            if (oldResposta.getRespostaId() > biggest) {
                biggest = oldResposta.getRespostaId();

            }
        }
        resposta.setRespostaId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(resposta);
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
    public List<RespostaUtilizador> getRespostaUtilizador() throws ExecutionException, InterruptedException {
        List<RespostaUtilizador> respostasUtilizador = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            RespostaUtilizador resposta = document.toObject(RespostaUtilizador.class);
            respostasUtilizador.add(resposta);
        }
        return respostasUtilizador;
    }

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
        resposta.setRespostaId(biggest + 1);


        /*ADICIONA UM NOVO PEDIDO*/

        //SE A RESPOSTA TIVER A DIZRE QUE FOI ACEITE O ESTADO DO DOCENTE MUDA PARA 1 SE NÃO, MUDA PARA -1(DEVE SER ELIMINADO O DOCENTE)
        if (resposta.isAceite()) {
            ApiFuture<QuerySnapshot> docenteQuery = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNome", resposta.getNomeUtilizador()).get();
            Docente docenteWithAccess = docenteQuery.get().getDocuments().get(0).toObject(Docente.class);
            docenteWithAccess.setHasAccess(1);
            db.collection("Docente").document(docenteQuery.get().getDocuments().get(0).getId()).set(docenteWithAccess);

        } else if (!resposta.isAceite()) {

            /*!PERGUNTAR AO QUENTAL COMO PROCEDER (SE APAGA OU NÃO O DOCENTE AQUI)!*/
            ApiFuture<QuerySnapshot> docenteQuery = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNome", resposta.getNomeUtilizador()).get();
            Docente docenteWithAccess = docenteQuery.get().getDocuments().get(0).toObject(Docente.class);
            docenteWithAccess.setHasAccess(-1);
            db.collection("Docente").document(docenteQuery.get().getDocuments().get(0).getId()).set(docenteWithAccess);
        }

        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(resposta);
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
