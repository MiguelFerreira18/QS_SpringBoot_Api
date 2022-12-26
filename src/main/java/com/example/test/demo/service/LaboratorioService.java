package com.example.test.demo.service;

import com.example.test.demo.model.Docente;
import com.example.test.demo.model.Laboratorio;
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
public class LaboratorioService {

    private static final String COL_NAME = "laboratorio";

    public String saveLaboratorio(Laboratorio laboratorio) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(laboratorio);
        return "laboratorio created";
    }

    public List<Laboratorio> getAllLabs() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Laboratorio> docentes = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot doc : documents) {
                docentes.add(doc.toObject(Laboratorio.class));
            }
            return docentes;
        }
        return null;
    }

    public String deleteLab(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).delete();
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "laboratorio não encontrado";
    }

    public String updateLab(Laboratorio laboratorio) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", laboratorio.getLaboratorioId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(laboratorio);
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "laboratorio não encontrado";
    }

    /*CASOS PARTICULARES*/
    /*
     * ADICIONAR UM NOVO MATERIAL AO LABORATORIO
     * APAGAR UM MATERIAL DO LABORATÓRIO
     * LER TODOS OS MATERIAIS DO LABORATÓRIO
     */

    /**
     * @param id
     * @param matId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addMaterialToLab(int id, int matId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.size() > 0) {
            Laboratorio docente = documents.get(0).toObject(Laboratorio.class);
            docente.getMateriaisId().add(matId);
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(docente);
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "docente não encontrado";
    }

    /**
     * @param id
     * @param matId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteMaterialFromLab(int id, int matId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.size() > 0) {
            Laboratorio docente = documents.get(0).toObject(Laboratorio.class);
            docente.getMateriaisId().remove(matId);
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(docente);
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "docente não encontrado";
    }

    /**
     * @param id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Integer> getMateriaisFromLab(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.size() > 0) {
            Laboratorio docente = documents.get(0).toObject(Laboratorio.class);
            return docente.getMateriaisId();
        }
        return null;
    }

    /*CASOS PARTICULARES*/
    /*
    * Cria resposta no laboratorio
    * altera resposta no laboratorio
    */


    public String createRespostaLaboratorio(int labId, int respostaId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", labId).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (future.get().size() <= 0)
            return "Laboratório não encontrado";

        Laboratorio lab = future.get().getDocuments().get(0).toObject(Laboratorio.class);
        lab.getRespostasLaboratorio().remove(lab.getRespostasLaboratorio().indexOf(respostaId));
        lab.getRespostasLaboratorio().add(respostaId);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(lab);
        return apiFuture.get().getUpdateTime().toString();

    }

    public String updateRespostaLaboratório(int idResposta,int idLaboratorio) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idLaboratorio).get();
        if(future.get().size()<=0)
            return "No elements to be queried";
        Laboratorio laboratorio = future.get().getDocuments().get(0).toObject(Laboratorio.class);
        laboratorio.getRespostasLaboratorio().remove(laboratorio.getRespostasLaboratorio().indexOf(idResposta));
        laboratorio.getRespostasLaboratorio().add(idResposta);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(laboratorio);
        return apiFuture.get().getUpdateTime().toString();
    }


}
