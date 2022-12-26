package com.example.test.demo.service;

import com.example.test.demo.model.Componente;
import com.example.test.demo.model.EtiquetaMaterial;
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
public class EtiquetaService {

    public static final String COL_NAME = "etiquetaMaterial";

    /**
     * Metodo cria uma nova etiqueta na base de dados
     * @param etiquetaMaterial objeto enviado do client side
     * @return retorna a data de criação da etiqueta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String saveEtiqueta(EtiquetaMaterial etiquetaMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Material oldMat = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldMat = doc.toObject(Material.class);
            if (oldMat.getMaterialId() > biggest) {
                biggest = oldMat.getMaterialId();

            }
        }
        etiquetaMaterial.setEtiquetaId(biggest + 1);
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(etiquetaMaterial);

        return "etiqueta created";
    }

    /**
     * Metodo que retorna uma lista de etiquetas
     * @return retorna uma lista de etiquetas
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<EtiquetaMaterial> getAllEtiquetas() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<EtiquetaMaterial> mats = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot doc : documents) {
                mats.add(doc.toObject(EtiquetaMaterial.class));
            }
            return mats;
        }
        return null;
    }


    /**
     * metodo que atualiza uma etiqueta
     * @param etiquetaMaterial objeto enviado do client side
     * @return retorna a data de atualização da etiqueta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateEtiqueta(EtiquetaMaterial etiquetaMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", etiquetaMaterial.getEtiquetaId()).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "No elements to be queried";
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(etiquetaMaterial);

        return apiFuture.get().getUpdateTime().toString();
    }

    /**
     * metodo que apaga uma etiqueta
     * @param id id da etiqueta a ser apagada
     * @return retorna a data de apagamento da etiquetas
     * @throws ExecutionException
     * @throws InterruptedException
     */

    public String deleteEtiqueta(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", id).get();
        if (future.get().size() <= 0)
            return "Material não encontrado para ser eleminado";
        System.out.println(db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()));
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }
    /*CASOS PARTICULARES*/
    /*
     * COMPONENTES:*ADICIONAR COMPONENTE À ETIQUETA
     *APAGAR COMPONENTE À ETIQUETA
     *ALTERAR COMPONENTE À ETIQUETA
     * LER TODOS OS COMPONENTES DA ETIQUETA
     * MATERIAIS:*ADICIONAR MATERIAL À ETIQUETA
     *LER MATERIAIS DA ETIQUETA
     * APAGAR MATERIAL DA ETIQUETA
     */

    /**
     * metodo que adiciona um componente a uma etiqueta
     * @param idEtiqueta id da etiqueta
     * @param comp componente a ser adicionado
     * @return retorna a data de atualização da etiqueta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addComponente(int idEtiqueta, int comp) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", idEtiqueta).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "No elements to be queried";
        EtiquetaMaterial etiquetaMaterial = future.get().getDocuments().get(0).toObject(EtiquetaMaterial.class);
        etiquetaMaterial.getComponentes().add(comp);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(etiquetaMaterial);

        return apiFuture.get().getUpdateTime().toString();
    }

    /**
     * metodo apaga um componente de uma etiqueta
     * @param idEtiqueta id da etiqueta
     * @param componente componente a ser apagado
     * @return retorna a data de atualização da etiqueta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteComponente(int idEtiqueta, int componente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", idEtiqueta).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "No elements to be queried";
        EtiquetaMaterial etiquetaMaterial = future.get().getDocuments().get(0).toObject(EtiquetaMaterial.class);
        for (int i = 0; i < etiquetaMaterial.getComponentes().size(); i++) {
            if (componente == etiquetaMaterial.getComponentes().get(i)) {
                etiquetaMaterial.getComponentes().remove(componente);
                break;
            }
        }
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(etiquetaMaterial);

        return apiFuture.get().getUpdateTime().toString();
    }

    /**
     * metodo que devolve todos os componentes de uma etiqueta
     * @param idEtiqueta id da etiqueta
     * @return retorna uma lista de componentes
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Integer> getAllComponentes(int idEtiqueta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", idEtiqueta).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return null;
        EtiquetaMaterial etiquetaMaterial = future.get().getDocuments().get(0).toObject(EtiquetaMaterial.class);
        return etiquetaMaterial.getComponentes();
    }
    /*FIM DOS CASOS PARTICULARES DOS COMPONENTES*/

    /**
     * metodo que adiciona um material a uma etiqueta
     * @param idEtiqueta id da etiqueta
     * @param idMaterial id do material a ser adicionado
     * @return retorna a data de atualização da etiqueta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addMaterialToEtiqueta(int idEtiqueta, int idMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", idEtiqueta).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "No elements to be queried";
        EtiquetaMaterial etiquetaMaterial = future.get().getDocuments().get(0).toObject(EtiquetaMaterial.class);
        etiquetaMaterial.getMateriaisId().add(idMaterial);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(etiquetaMaterial);

        return apiFuture.get().getUpdateTime().toString();
    }

    /**
     * metodo que apaga um material de uma etiqueta
     * @param idEtiqueta id da etiqueta
     * @param idMaterial id do material a ser apagado
     * @return retorna a data de atualização da etiqueta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteMaterialFromEtiqueta(int idEtiqueta, int idMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", idEtiqueta).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "No elements to be queried";
        EtiquetaMaterial etiquetaMaterial = future.get().getDocuments().get(0).toObject(EtiquetaMaterial.class);
        etiquetaMaterial.getMateriaisId().remove(idMaterial);
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(etiquetaMaterial);

        return apiFuture.get().getUpdateTime().toString();
    }

    /**
     * metodo que devolve todos os materiais de uma etiqueta
     * @param idEtiqueta
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Integer> getAllMateriaisFromEtiqueta(int idEtiqueta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", idEtiqueta).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return null;
        EtiquetaMaterial etiquetaMaterial = future.get().getDocuments().get(0).toObject(EtiquetaMaterial.class);
        return etiquetaMaterial.getMateriaisId();
    }

}
