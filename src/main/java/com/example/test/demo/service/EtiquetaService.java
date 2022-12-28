package com.example.test.demo.service;

import com.example.test.demo.model.EtiquetaMaterial;
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
    public static final String COL_NAME_COMPONENT = "component";
    public static final String COL_NAME_MATERIAL = "material";

    /**
     * Metodo cria uma nova etiqueta na base de dados
     *
     * @param etiquetaMaterial objeto enviado do client side
     * @return retorna a data de criação da etiqueta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createEtiqueta(EtiquetaMaterial etiquetaMaterial) throws ExecutionException, InterruptedException {
        if (verifyEtiqueta(etiquetaMaterial)) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        EtiquetaMaterial oldEtiqueta = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldEtiqueta = doc.toObject(EtiquetaMaterial.class);
            if (oldEtiqueta.getEtiquetaId() > biggest) {
                biggest = oldEtiqueta.getEtiquetaId();
            }
        }
        etiquetaMaterial.setEtiquetaId(biggest + 1);
        /*ADICIONA UM NOVO MATERIAL*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(etiquetaMaterial);
        return "etiqueta created";
    }


    /**
     * Metodo que retorna uma lista de etiquetas
     *
     * @return retorna uma lista de etiquetas
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<EtiquetaMaterial> getAllEtiquetas() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<EtiquetaMaterial> mats = new ArrayList<>();
        if (documents.isEmpty())
            return null;
        for (QueryDocumentSnapshot doc : documents) {
            mats.add(doc.toObject(EtiquetaMaterial.class));
        }
        return mats;
    }


    /**
     * metodo que atualiza uma etiqueta com um id especifico
     *
     * @param etiquetaMaterial objeto enviado do client side
     * @return retorna a data de atualização da etiqueta
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateEtiqueta(EtiquetaMaterial etiquetaMaterial) throws ExecutionException, InterruptedException {
        if (verifyEtiqueta(etiquetaMaterial)) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", etiquetaMaterial.getEtiquetaId()).get();
        //update a document from firestore

        if (future.get().size() <= 0)
            return null;
        ApiFuture<WriteResult> apiFuture = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(etiquetaMaterial);
        return "etiqueta updated:" + etiquetaMaterial.getEtiquetaId();
    }

    /**
     * metodo que apaga uma etiqueta na base de dados
     *
     * @param id id da etiqueta a ser apagada
     * @return retorna a data de apagamento da etiquetas
     * @throws ExecutionException
     * @throws InterruptedException
     */

    public String deleteEtiqueta(int id) throws ExecutionException, InterruptedException {
        if (id < 0)
            return null;
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("etiquetaId", id).get();
        if (future.get().size() <= 0)
            return null;
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return "etiqueta deleted with:" + id;
    }

    /*CASOS PARTICULARES*/
    /*
     * COMPONENTES:*ADICIONAR COMPONENTE À ETIQUETA
     * APAGAR COMPONENTE À ETIQUETA
     * ALTERAR COMPONENTE À ETIQUETA
     * LER TODOS OS COMPONENTES DA ETIQUETA
     * MATERIAIS:*ADICIONAR MATERIAL À ETIQUETA
     * LER MATERIAIS DA ETIQUETA
     * APAGAR MATERIAL DA ETIQUETA
     */

    /**
     * metodo que adiciona um componente a uma etiqueta
     *
     * @param idEtiqueta id da etiqueta
     * @param comp       componente a ser adicionado
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
     *
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
     *
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
     *
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
     *
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
    /*METODOS AUXILIARES*/

    /**
     * metodo verifica se as componentes da lista são validas
     *
     * @param idComponente id da componente a ser verificada
     * @return retorna verdadeiro se for invalida
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean verifyComponentes(Integer idComponente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME_COMPONENT).whereEqualTo("id", idComponente).get();
        if (future.get().isEmpty())
            return true;
        return false;
    }

    /**
     * metodo que verifica se os materiais da lista são validos
     *
     * @param material id do material a ser verificado
     * @return retorna verdadeiro se for invalido
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean verifyMateriais(Integer material) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME_MATERIAL).whereEqualTo("materialId", material).get();
        if (future.get().isEmpty())
            return true;
        return false;
    }

    /**
     * metodo que verifica se a etiqueta é valida
     *
     * @param etiquetaMaterial objeto a ser verificado
     * @return retorna verdadeiro se for invalida
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean verifyEtiqueta(EtiquetaMaterial etiquetaMaterial) throws ExecutionException, InterruptedException {
        return (etiquetaMaterial.getDescricaoMaterial() == null || etiquetaMaterial.getDescricaoMaterial().length() > 128 || etiquetaMaterial.getDescricaoMaterial().length() < 1)
                || (etiquetaMaterial.getQuantidade() < 0 || etiquetaMaterial.getQuantidade() > 99)
                || (etiquetaMaterial.getSubEtiqueta() == null || etiquetaMaterial.getSubEtiqueta().length() > 32 || etiquetaMaterial.getSubEtiqueta().length() < 1)
                || (etiquetaMaterial.getEtiquetaId() < 0)|| (etiquetaMaterial.getEtiqueta() == null || etiquetaMaterial.getEtiqueta().length() > 32 || etiquetaMaterial.getEtiqueta().length() < 1)
                || (etiquetaMaterial.getComponentes() == null || etiquetaMaterial.getMateriaisId() == null || verifyArrays(etiquetaMaterial));//Probelma nisto VerifyArrays
    }


    /**
     * metodo que verifica se os arraysLists são validos
     *
     * @param etiquetaMaterial objeto a ser verificado
     * @return retorna verdadeiro se pelo menos 1 for invalido
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean verifyArrays(EtiquetaMaterial etiquetaMaterial) throws ExecutionException, InterruptedException {
        if (etiquetaMaterial.getComponentes().size() > 0) {
            for (Integer componente : etiquetaMaterial.getComponentes()) {
                if (componente == null || componente < 0 || verifyComponentes(componente)) {
                    return true;
                }
            }
        }
        if (etiquetaMaterial.getMateriaisId().size() > 0) {
            for (Integer material : etiquetaMaterial.getMateriaisId()) {
                if (material == null || material < 0 || verifyMateriais(material)) {
                    return true;
                }
            }

        }
        return false;
    }
}
