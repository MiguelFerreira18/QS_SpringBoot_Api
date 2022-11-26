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


    /*PEDIDOS DO TIPO UTILIZADOR*/

    //cria um novo pedido do tipo utilizador
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

    //altera um novo pedido do utilizador
    public String updatePedidoUtilizador(PedidoUtilizador pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", pedido.getPedidoId()).whereEqualTo("resposta", false).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    //elemina pedido do utlizador sem resposta
    public String deletePedidoUtilizador(int pedidoId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", pedidoId).whereEqualTo("resposta", false).get();
        //delete a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser eliminado ou já foi respondido";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }


    /*PEDIDOS DO TIPO MATERIAL*/
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
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", pedido.getPedidoId()).whereEqualTo("resposta", false).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    //elimina pedido do tipo material sem resposta
    public String deletePedidoMaterial(int pedidoId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", pedidoId).whereEqualTo("resposta", false).get();
        //delete a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser eliminado ou já foi respondido";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }

    /*PEDIDOS DO TIPO LABORATÓRIO*/

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
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", pedido.getPedidoId()).whereEqualTo("resposta", false).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    //elimina pedido do tipo laboratório sem resposta
    public String deletePedidoLaboratorio(int pedidoId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", pedidoId).whereEqualTo("resposta", false).get();
        //delete a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser eliminado ou já foi respondido";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }




    /*CALMA AQUI*/

    /*public String savePedido(Pedido pedido) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Pedido oldPedido = null;

        *//*AUTO INCREMENTA O ID QUANDO ADICIONA*//*
        for (QueryDocumentSnapshot doc : documents) {
            oldPedido = doc.toObject(Pedido.class);
            if (oldPedido.getAutorId() > biggest) {
                biggest = oldPedido.getPedidoId();

            }
        }
        pedido.setPedidoId(biggest + 1);
        *//*ADICIONA UM NOVO PEDIDO*//*
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(pedido);

        return colApiFuture.get().getUpdateTime().toString();
    }*/
/*
    public String deletePedido(int id) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", id).get();
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser eleminado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }*/

  /*  public String updatePedido(Pedido pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", pedido.getPedidoId()).get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }*/

    public List<Pedido> getPedido() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Pedido> pedidos = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot doc : documents) {
                pedidos.add(doc.toObject(Pedido.class));
            }
            return pedidos;
        }
        return null;
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
    public String addEtiqueta(int idPedido, ArrayList<String> nomeEtiquetas) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", idPedido).whereEqualTo("tipoPedido", "Pedido Material").get();
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
    public String deleteEtiqueta(int idPedido, String nomeEtiqueta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", idPedido).whereEqualTo("tipoPedido", "Pedido Material").get();
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
    public List<String> getEtiquetas(int idPedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", idPedido).whereEqualTo("tipoPedido", "Pedido Material").get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return null;
        PedidoMaterial pedido = future.get().getDocuments().get(0).toObject(PedidoMaterial.class);
        return pedido.getEtiquetas();
    }
}
