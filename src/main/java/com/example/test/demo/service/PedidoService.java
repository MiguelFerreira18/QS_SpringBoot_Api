package com.example.test.demo.service;

import com.example.test.demo.model.Material;
import com.example.test.demo.model.Pedido;
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

    public String savePedido(Pedido pedido) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Pedido oldPedido = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldPedido = doc.toObject(Pedido.class);
            if (oldPedido.getAutorId() > biggest) {
                biggest = oldPedido.getPedidoId();

            }
        }
        pedido.setPedidoId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().set(pedido);

        return colApiFuture.get().getUpdateTime().toString();
    }

    public String deletePedido(Pedido pedido) throws InterruptedException, ExecutionException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot>  future= db.collection(COL_NAME).whereEqualTo("pedidoId",pedido.getPedidoId()).get();
        if (future.get().size()<=0)
            return "Pedido não encontrado para ser eleminado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return writeResult.get().getUpdateTime().toString();
    }

    public String updatePedido(Pedido pedido) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("pedidoId", pedido.getPedidoId()).get();
        //update a document from firestore
        if(future.get().size()<=0)
            return "Pedido não encontrado para ser atualizado";
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    public List<Pedido> getPedido() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Pedido> pedidos = new ArrayList<>();
        if(documents.size()>0){
            for (QueryDocumentSnapshot doc: documents){
                pedidos.add(doc.toObject(Pedido.class));
            }
            return pedidos;
        }
        return null;
    }




}
