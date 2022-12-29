package com.example.test.demo.service;

import com.example.test.demo.model.Laboratorio;
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
    private static final String COL_NAME_MATERIAIS = "material";
    private static final String COL_NAME_DOCENTE = "docente";
    private static final String COL_NAME_RESPOSTA = "resposta";

    /**
     * Metodo para inserir um laboratorio na base de dados
     *
     * @param laboratorio Laboratorio a receber para ser criado
     * @return laboratorio created ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createLaboratorio(Laboratorio laboratorio) throws ExecutionException, InterruptedException {
        /*ADICIONA UM NOVO MATERIAL*/
        if (laboratorio.getLaboratorioId() < 0) //id laboratorio tem  de ser maior ou igual a 0
        {
            return null;
        } else if (laboratorio.getRefAdmin() < 0)//refAdmin nao pode ser menor que 0
        {
            return null;
        } else if (checkAll(laboratorio)) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();


        ApiFuture<QuerySnapshot> future3 = db.collection(COL_NAME_DOCENTE).whereEqualTo("docenteNumber", laboratorio.getRefAdmin()).get();
        List<QueryDocumentSnapshot> documents3 = future3.get().getDocuments();
        if (documents3.size() != 1)//se o numeroDocente nao existir na db e so pode haver 1 docente com o mesmo numero
        {
            return null;
        }

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", laboratorio.getLaboratorioId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        //Se ja existir lab com o mesmo id
        if (documents.size() == 1) {
            return null;
        }
        db.collection(COL_NAME).document().set(laboratorio);
        return "laboratorio created";
    }

    /**
     * Metodo que retorna uma lista de Laboratorios
     *
     * @return lista laboratorios ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Laboratorio> getAllLaboratorios() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Laboratorio> laboratorios = new ArrayList<>();
        if (documents.isEmpty()) {
            return null;
        }
        for (QueryDocumentSnapshot doc : documents) {
            laboratorios.add(doc.toObject(Laboratorio.class));
        }
        return laboratorios;
    }

    /**
     * Metodo para eliminar um laboratorio da base de dados
     *
     * @param id Identificacao do laboratorio
     * @return laboratorio deleted with: laboratorioId ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteLaboratorio(int id) throws ExecutionException, InterruptedException {
        if (id < 0) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty()) {
            return null;
        }
        db.collection(COL_NAME).document(documents.get(0).getId()).delete();
        return "laboratorio deleted with:" + id;
    }

    /**
     * Metodo para atualizar um laboratorio existente na base de dados
     *
     * @param laboratorio Laboratorio que ira substituir o Laboratorio a atualizar
     * @return laboratorio updated with: laboratorioId ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateLaboratorio(Laboratorio laboratorio) throws ExecutionException, InterruptedException {
        if (laboratorio.getLaboratorioId() < 0) {
            return null;
        } else if (checkAll(laboratorio)) {
            return null;
        } else if (laboratorio.getRefAdmin() < 0) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future3 = db.collection(COL_NAME_DOCENTE).whereEqualTo("docenteNumber", laboratorio.getRefAdmin()).get();
        List<QueryDocumentSnapshot> documents3 = future3.get().getDocuments();
        if (documents3.size() != 1)//se o numeroDocente nao existir na db e so pode haver 1 docente com o mesmo numero
        {
            return null;
        }

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("laboratorioId", laboratorio.getLaboratorioId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty()) {
            return null;
        }
        db.collection(COL_NAME).document(documents.get(0).getId()).set(laboratorio);
        return "laboratorio updated with:" + laboratorio.getLaboratorioId();
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
        if (documents.isEmpty())
            return null;
        Laboratorio laboratorio = documents.get(0).toObject(Laboratorio.class);
        laboratorio.getMateriaisId().add(matId);
        db.collection(COL_NAME).document(documents.get(0).getId()).set(laboratorio);
        return "material added to lab with id:" + id;
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

    public String updateRespostaLaboratório(int idResposta, int idLaboratorio) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("materialId", idLaboratorio).get();
        if (future.get().size() <= 0)
            return "No elements to be queried";
        Laboratorio laboratorio = future.get().getDocuments().get(0).toObject(Laboratorio.class);
        laboratorio.getRespostasLaboratorio().remove(laboratorio.getRespostasLaboratorio().indexOf(idResposta));
        laboratorio.getRespostasLaboratorio().add(idResposta);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(laboratorio);
        return apiFuture.get().getUpdateTime().toString();
    }


    //Metodos auxiliares

    /**
     * Metodo para auxiliar na validacao na criacao e atualizacao do Laboratorio
     *
     * @param laboratorio Laboratorio a receber como parametro para ser verificado
     * @return
     */
    private boolean checkAll(Laboratorio laboratorio) {

        if (laboratorio.getLogs().size() > 0) {
            for (int i = 0; i < laboratorio.getLogs().size(); i++) {
                if (laboratorio.getLogs().get(i).length() < 8
                        || laboratorio.getLogs().get(i).length() > 128
                        || laboratorio.getLogs().get(i).equalsIgnoreCase("")
                        || laboratorio.getLogs().get(i) == null) {
                    return true;
                }
            }
        }

        if (laboratorio.getMateriaisId().size() > 0) {
            for (int i = 0; i < laboratorio.getMateriaisId().size(); i++) {
                if (laboratorio.getMateriaisId().get(i) < 0) //id material nao pode ser menor que zero
                {
                    return true;
                }
            }
        }
        if (laboratorio.getRespostasLaboratorio().size() > 0) {
            for (int i = 0; i < laboratorio.getRespostasLaboratorio().size(); i++) {
                if (laboratorio.getRespostasLaboratorio().get(i) < 0) //id resposta n pode ser menor q zero
                {
                    return true;
                }
            }
        }

        return false;
    }


}
