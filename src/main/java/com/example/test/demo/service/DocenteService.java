package com.example.test.demo.service;

import com.example.test.demo.model.Docente;
import com.example.test.demo.util.AESUtil;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

@Service
public class DocenteService {
    @Autowired
    private AESUtil aesUtil;

    private static final String COL_NAME = "docente";

//    public String requestTest(String password) {
//        byte[] pwd = aesUtil.encrypt(password.getBytes());
//        System.out.println(pwd.toString());
//        String decrypted = new String(aesUtil.decrypt(pwd));
//        System.out.println(decrypted);
//        return decrypted;
//    }


    /**
     * metodo para creiar um novo docente na base de dados
     *
     * @param docente docente que vêm do client side
     * @return retorna o momento em que foi adicionado
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createDocentes(Docente docente) throws ExecutionException, InterruptedException {
        if (checkDocente(docente) || checkPassword(docente) || checkEmail(docente.getDocenteEmail()) || checkForDuplicates(docente) || checkUcsDocente(docente)) {
            return null;
        }
        if (checkIsAdmin(docente))
            docente.setHasAccess(1);

        byte[] pwd = aesUtil.encrypt(docente.getDocentePassword().getBytes());
        docente.setDocentePassword(new String(pwd));

        Firestore db = FirestoreClient.getFirestore();
        /*ADICIONA UM NOVO Docente*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(docente);
        return "docente created";
    }

    /**
     * metodo para obter todos os docentes da base de dados
     *
     * @return retorna a lista de docentes
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Docente> getAllDocentes() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Docente> docentes = new ArrayList<>();
        if (documents.size() < 0)
            return null;

        for (QueryDocumentSnapshot doc : documents)
            docentes.add(doc.toObject(Docente.class));

        return docentes;
    }

    /**
     * metodo para alterar um docente na base de dados
     *
     * @param docente docente que vêm do client side
     * @return retorna se o docente foi alterado ou não
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateDocente(Docente docente) throws ExecutionException, InterruptedException {
        if (checkDocente(docente) || checkPassword(docente) || checkEmail(docente.getDocenteEmail()) || checkUcsDocente(docente)) {
            return null;
        }

        byte[] pwd = aesUtil.encrypt(docente.getDocentePassword().getBytes());
        docente.setDocentePassword(new String(pwd));

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", docente.getDocenteNumber()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() < 0)
            return null;

        ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(docente);
        return "updated docente: " + docente.getDocenteNumber();
    }

    /**
     * metodo para eliminar um docente na base de dados
     *
     * @param id id do docente que vêm do client side
     * @return retorna se o docente foi eliminado ou não
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteDocente(int id) throws ExecutionException, InterruptedException {
        if (id < 0)
            return null;

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() < 0)
            return null;

        ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).delete();
        return "deleted with id:" + id;
    }



    /*CASOS PARTICULARES*/
    /*ADICIONAR UC OA DOCENTE
//     REMOVER UC AO DOCENTE
//     ALTERAR UC AO DOCENTE
//     LER TODAS AS UC DO DOCENTE */

    /**
     * adiciona uma uc ao docente
     *
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
     *
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
     *
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
     *
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

    /*!AUTH REQUEST!*/

    /**
     * metodo para que um utilizador faça login
     * @param numeroDocente numero do docente que vêm do client side
     * @return retorna o docente com o id dado
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Docente loginDocente(int numeroDocente, String password) throws ExecutionException, InterruptedException {
        if (numeroDocente < 0 || password == null || password.isEmpty())
            return null;

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", numeroDocente).whereEqualTo("docentePassword", password).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() < 0) {
            return null;
        }
        Docente docente = documents.get(0).toObject(Docente.class);
        byte[] pwd = aesUtil.decrypt(docente.getDocentePassword().getBytes());
        if (pwd.toString().equals(password))
            return docente;
        return documents.get(0).toObject(Docente.class);
    }
    /*!METODOS AUXILIARES!*/

    private boolean checkDocente(Docente docente) {
        return docente == null
                || docente.getDocenteNome() == null
                || docente.getDocenteNome().equals("")
                || docente.getDocenteNome().length() > 32
                || docente.getDocenteNumber() <= 0
                || docente.getHasAccess() < -1
                || docente.getHasAccess() > 1;
    }

    private boolean checkPassword(Docente docente) {
        return docente.getDocentePassword() == null
                || docente.getDocentePassword().equals("")
                || docente.getDocentePassword().length() > 16
                || docente.getDocentePassword().length() < 3
                || !Pattern.compile("[^a-zA-Z0-9]").matcher(docente.getDocentePassword()).find()
                || !Pattern.compile("[A-Z]").matcher(docente.getDocentePassword()).find()
                || !Pattern.compile("[0-9]").matcher(docente.getDocentePassword()).find();
    }

    private boolean checkEmail(String email) {
        return !Pattern.compile("[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+").matcher(email).find()
                || email.length() > 32
                || email == null
                || email.equals("");
    }

    private boolean checkForDuplicates(Docente docente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("docenteNumber", docente.getDocenteNumber()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.size() > 0;
    }

    private boolean checkUcsDocente(Docente docente) {
        for (String uc : docente.getUnidadesCurriculares())
            if (uc == null || uc.equals("") || uc.length() > 32 || uc.length() < 2)
                return true;
        return false;
    }

    private boolean checkIsAdmin(Docente docente) {
        return docente.getHasAccess() == 0 && docente.isAdmin();
    }
}
