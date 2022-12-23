package com.example.test.demo.service;

import com.example.test.demo.model.Pedido;
import com.example.test.demo.model.PedidoLaboratorio;
import com.example.test.demo.model.PedidoMaterial;
import com.example.test.demo.model.PedidoUtilizador;
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
public class PedidoService {

    public static String COL_NAME = "pedido";
    public static String PEDIDO_NAME_UTILIZADOR = "pedidoUtilizador";
    public static String PEDIDO_NAME_MATERIAL = "pedidoMaterial";
    public static String PEDIDO_NAME_LABORATORIO = "pedidoLaboratorio";


    /**
     *
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Object> getAllPedidos() throws ExecutionException, InterruptedException {
        List<Object> pedidos = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            Object resposta = document.toObject(Object.class);
            pedidos.add(resposta);
        }
        return pedidos;
    }

    /*PEDIDOS DO TIPO UTILIZADOR*/

    /**
     *  Método que retorna todos os pedidos do tipo utilizador
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<PedidoUtilizador> getAllPedidosUtilizador() throws ExecutionException, InterruptedException {
        List<PedidoUtilizador> pedidos = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("tipoPedido", PEDIDO_NAME_UTILIZADOR).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            PedidoUtilizador resposta = document.toObject(PedidoUtilizador.class);
            pedidos.add(resposta);
        }
        return pedidos;
    }



    /**
     * cria um novo pedido do tipo utilizador
     * @param pedido
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createPedidoUtilizador(PedidoUtilizador pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Pedido oldPedido = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldPedido = doc.toObject(PedidoUtilizador.class);
            if (oldPedido.getPedidoId() > biggest) {
                biggest = oldPedido.getPedidoId();

            }
        }
        pedido.setPedidoId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(pedido);

        return colApiFuture.get().getUpdateTime().toString();
    }



    /**
     * altera um pedido do utlizador
     * @param pedido
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updatePedidoUtilizador(PedidoUtilizador pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db
                .collection(COL_NAME)
                .whereEqualTo("pedidoId", pedido.getPedidoId())
                .whereEqualTo("resposta", false)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_UTILIZADOR)
                .get();


        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }



    /**
     * elimina um pedido do utilizador
     * @param pedidoId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deletePedidoUtilizador(int pedidoId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedidoId)
                .whereEqualTo("resposta", false)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_UTILIZADOR)
                .get();
        //delete a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser eliminado ou já foi respondido";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }


    /*PEDIDOS DO TIPO MATERIAL*/
    public List<PedidoMaterial> getAllPedidosMaterial() throws ExecutionException, InterruptedException {
        List<PedidoMaterial> pedidoMaterials = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("tipoPedido", PEDIDO_NAME_MATERIAL).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            PedidoMaterial resposta = document.toObject(PedidoMaterial.class);
            pedidoMaterials.add(resposta);
        }
        return pedidoMaterials;

    }

    //cria um pedido do tipo material
    public String createPedidoMaterial(PedidoMaterial pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Pedido oldPedido = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldPedido = doc.toObject(PedidoMaterial.class);
            if (oldPedido.getPedidoId() > biggest) {
                biggest = oldPedido.getPedidoId();

            }
        }
        pedido.setPedidoId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(pedido);

        return colApiFuture.get().getUpdateTime().toString();
    }

    //altera um pedido do tipo material
    public String updatePedidoMaterial(PedidoMaterial pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedido.getPedidoId())
                .whereEqualTo("resposta", false)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", pedido.getAuthorId())
                .get();

        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    //elimina pedido do tipo material sem resposta
    public String deletePedidoMaterial(int pedidoId,int authorId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedidoId)
                .whereEqualTo("resposta", false)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", authorId)
                .get();
        //delete a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser eliminado ou já foi respondido";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }

    /*PEDIDOS DO TIPO LABORATÓRIO*/
    public List<PedidoLaboratorio> getAllPedidosLaboratorio() throws ExecutionException, InterruptedException {
        List<PedidoLaboratorio> pedidoLaboratorios = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("tipoPedido", PEDIDO_NAME_LABORATORIO).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            PedidoLaboratorio resposta = document.toObject(PedidoLaboratorio.class);
            pedidoLaboratorios.add(resposta);
        }
        return pedidoLaboratorios;

    }
    //cria um pedido do tipo laboratório
    public String createPedidoLaboratorio(PedidoLaboratorio pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Pedido oldPedido = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldPedido = doc.toObject(PedidoLaboratorio.class);
            if (oldPedido.getPedidoId() > biggest) {
                biggest = oldPedido.getPedidoId();

            }
        }
        pedido.setPedidoId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(pedido);

        return colApiFuture.get().getUpdateTime().toString();
    }

    //altera um pedido do tipo laboratório
    public String updatePedidoLaboratorio(PedidoLaboratorio pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedido.getPedidoId())
                .whereEqualTo("resposta", false)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_LABORATORIO)
                .whereEqualTo("authorId", pedido.getAuthorId())
                .get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    //elimina pedido do tipo laboratório sem resposta
    public String deletePedidoLaboratorio(int pedidoId,int authorId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedidoId)
                .whereEqualTo("resposta", false)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_LABORATORIO)
                .whereEqualTo("authorId", authorId)
                .get();
        //delete a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser eliminado ou já foi respondido";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }


    /*CASOS PARTICULARES*/
    /*
     * ADICIONAR UMA NOVA ETIQUETA AO PEDIDO
     * APAGAR ETIQUETA DO PEDIDO
     * LER TODAS AS ETIQUETAS
     */

    /**
     * @param idPedido
     * @param nomeEtiquetas
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addEtiqueta(int idPedido, ArrayList<String> nomeEtiquetas,int authorId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", idPedido)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", authorId)
                .get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        PedidoMaterial pedido = future.get().getDocuments().get(0).toObject(PedidoMaterial.class);
        pedido.getEtiquetas().addAll(nomeEtiquetas);
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    /**
     * @param idPedido
     * @param nomeEtiqueta
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteEtiqueta(int idPedido, String nomeEtiqueta,int authorId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", idPedido)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", authorId)
                .get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        PedidoMaterial pedido = future.get().getDocuments().get(0).toObject(PedidoMaterial.class);
        pedido.getEtiquetas().remove(nomeEtiqueta);
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    /**
     * @param idPedido
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<String> getEtiquetas(int idPedido,int authorId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", idPedido)
                .whereEqualTo("tipoPedido", PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", authorId)
                .get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return null;
        PedidoMaterial pedido = future.get().getDocuments().get(0).toObject(PedidoMaterial.class);
        return pedido.getEtiquetas();
    }
}
