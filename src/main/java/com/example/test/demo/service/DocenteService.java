package com.example.test.demo.service;

import com.example.test.demo.model.Docente;
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
public class DocenteService {

    private static final String COL_NAME = "docente";

    public String saveDocente(Docente docente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(docente);
        return colApiFuture.get().getUpdateTime().toString();
    }

    public List<Docente> getAllDocentes() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Docente> docentes = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot doc : documents) {
                docentes.add(doc.toObject(Docente.class));
            }
            return docentes;
        }
        return null;
    }

    public String updateDocente(Docente docente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", docente.getDocenteNumber()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(docente);
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "nenhum docente encontrado";
    }

    public String deleteDocente(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).delete();
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "docente não encontrado";
    }

    public Docente getDocenteById(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            return documents.get(0).toObject(Docente.class);
        }
        return null;
    }

    /*CASOS PARTICULARES*/
    /*ADICIONAR UC OA DOCENTE
//     REMOVER UC AO DOCENTE
//     ALTERAR UC AO DOCENTE
//     LER TODAS AS UC DO DOCENTE */

    /**
     *
     * @param id
     * @param uc
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addUcToDocente(int id, String uc) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            Docente docente = documents.get(0).toObject(Docente.class);
            docente.getUnidadesCurriculares().add(uc);
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(docente);
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "docente não encontrado";
    }

    /**
     *
     * @param id
     * @param uc
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteUcFromDocente(int id, String uc) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            Docente docente = documents.get(0).toObject(Docente.class);
            docente.getUnidadesCurriculares().remove(uc);
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(docente);
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "docente não encontrado";
    }

    /**
     *
     * @param id
     * @param uc
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateUcFromDocente(int id, String uc) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            Docente docente = documents.get(0).toObject(Docente.class);
            docente.getUnidadesCurriculares().remove(uc);
            ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(docente);
            return writeResultApiFuture.get().getUpdateTime().toString();
        }
        return "docente não encontrado";
    }

    /**
     *
     * @param id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<String> getAllUcFromDocente(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() > 0) {
            Docente docente = documents.get(0).toObject(Docente.class);
            return docente.getUnidadesCurriculares();
        }
        return null;
    }
}