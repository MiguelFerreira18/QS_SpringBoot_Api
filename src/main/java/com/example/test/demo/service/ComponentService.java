package com.example.test.demo.service;

import com.example.test.demo.model.Componente;
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
public class ComponentService {
    private static final String COL_NAME = "component";

    /**
     * Este metodo cria um componente na base de dados dado um objeto Componente
     *
     * @param componente Objeto do tipo Componente
     * @return retorna que criou o componente se for concluido com sucesso
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createComponent(Componente componente) throws ExecutionException, InterruptedException {
        if (componente == null || componente.getDescricao().length() > 256 || componente.getQuantidade() < 0 || componente.getQuantidade() > 99) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Componente oldComp = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldComp = doc.toObject(Componente.class);
            if (oldComp.getId() > biggest) {
                biggest = oldComp.getId();

            }
        }
        componente.setId(biggest + 1);
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(componente);

        return "componente created";
    }

    /**
     * Este metodo retorna todos os componentes da base de dados
     *
     * @return retorna uma lista de componentes se não existir nenhum retorna null
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Componente> getAllComponents() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Componente> componentes = new ArrayList<>();
        if (documents.isEmpty())
            return null;
        for (QueryDocumentSnapshot doc : documents)
            componentes.add(doc.toObject(Componente.class));
        return componentes;
    }

    /**
     * Este metodo atualiza um componente na base de dados dado um objeto Componente
     *
     * @param componente objeto do tipo Componente
     * @return retorna o id do componente se for concluido com sucesso
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateComponent(Componente componente) throws ExecutionException, InterruptedException {
        if (componente == null || componente.getDescricao().length() > 256 || componente.getQuantidade() < 0 || componente.getQuantidade() > 99 || componente.getId() < 0) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("id", componente.getId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() < 0) {
            return "Not found with : " + componente.getId() + " id";
        }
        ApiFuture<WriteResult> writeResultApiFuture = db.collection(COL_NAME).document(documents.get(0).getId()).set(componente);
        return "updated component: " + componente.getId();
    }

    /**
     * Este metodo apaga um componente na base de dados dado um id
     *
     * @param id id do componente a ser apagado
     * @return retorna o id do componente se for concluido com sucesso
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteComponent(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("id", id).get();
        if (future.get().size() <= 0)
            return "Not found with : " + id + " id";
        System.out.println(db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()));
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return "deleted component: " + id;
    }

    /**
     * Este metodo apaga todos os componentes da base de dados
     *
     * @return retorna que apagou todos os componentes se for concluido com sucesso
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteAllComponents() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.size() < 0) {
            return "no components to be deleted";
        }
        for (QueryDocumentSnapshot doc : documents) {
            ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(doc.getId()).delete();
        }
        return "deleted all components";
    }
}
