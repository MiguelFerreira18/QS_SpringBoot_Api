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

        return colApiFuture.get().getUpdateTime().toString();
    }

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


    public String updateEtiqueta(EtiquetaMaterial etiquetaMaterial) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", etiquetaMaterial.getEtiquetaId()).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "No elements to be queried";
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(etiquetaMaterial);

        return apiFuture.get().getUpdateTime().toString();
    }


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
     *
     * @param idEtiqueta
     * @param comp
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addComponente(int idEtiqueta, Componente comp) throws ExecutionException, InterruptedException {
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
     *
     * @param idEtiqueta
     * @param componente
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteComponente(int idEtiqueta, Componente componente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", idEtiqueta).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "No elements to be queried";
        EtiquetaMaterial etiquetaMaterial = future.get().getDocuments().get(0).toObject(EtiquetaMaterial.class);
        for (Componente comp : etiquetaMaterial.getComponentes()) {
            if (comp.getDescricao() == componente.getDescricao()) {
                etiquetaMaterial.getComponentes().remove(comp);
                break;
            }
        }
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(etiquetaMaterial);

        return apiFuture.get().getUpdateTime().toString();
    }

    /**
     *
     * @param idEtiqueta
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Componente> getAllComponentes(int idEtiqueta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", idEtiqueta).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return null;
        EtiquetaMaterial etiquetaMaterial = future.get().getDocuments().get(0).toObject(EtiquetaMaterial.class);
        return etiquetaMaterial.getComponentes();
    }
    /*FIM DOS CASOS PARTICULARES DOS DOCUMENTOS*/

    /**
     *
     * @param idEtiqueta
     * @param idMaterial
     * @return
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
     *
     * @param idEtiqueta
     * @param idMaterial
     * @return
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
     *
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
