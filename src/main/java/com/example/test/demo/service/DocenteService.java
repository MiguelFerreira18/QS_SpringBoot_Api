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

    /**
     * metodo para creiar um novo docente na base de dados
     * @param docente docente que vêm do client side
     * @return retorna o momento em que foi adicionado
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String saveDocente(Docente docente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(docente);
        return colApiFuture.get().getUpdateTime().toString();
    }

    /**
     * metodo para obter todos os docentes da base de dados
     * @return retorna a lista de docentes
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * metodo para alterar um docente na base de dados
     * @param docente docente que vêm do client side
     * @return retorna o momento em que foi alterado
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * metodo para eliminar um docente na base de dados
     * @param id id do docente que vêm do client side
     * @return retorna o momento em que foi eliminado
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * metodo para retornar um docente atravès do id
     * @param id id do docente que vêm do client side
     * @return retorna o docente com o id dado
     * @throws ExecutionException
     * @throws InterruptedException
     */
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
     * adiciona uma uc ao docente
     * @param id id do docente
     * @param uc nome da uc
     * @return retorna o momento em que foi adicionado
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
     * remove uma uc ao docente
     * @param id id do docente
     * @param uc nome da uc
     * @return retorna o momento em que foi removido
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
     * altera uma uc ao docente
     * @param id id do docente
     * @param uc nome da uc
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
     * retorna todas as uc do docente
     * @param id id do docente
     * @return retorna uma lista com todas as uc do docente
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