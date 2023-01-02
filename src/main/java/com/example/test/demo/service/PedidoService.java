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


    private static final String COL_NAME = "pedido";
    private static final String COL_NAME_MATERIAL = "material";
    private static final String COL_NAME_LABORATORIO = "laboratorio";
    private static final String COL_NAME_DOCENTE = "docente";
    private static final String PEDIDO_NAME_UTILIZADOR = "pedidoUtilizador";
    private static final String PEDIDO_NAME_MATERIAL = "pedidoMaterial";
    private static final String PEDIDO_NAME_LABORATORIO = "pedidoLaboratorio";
    private static final String STRING_LITERAL_TIPO = "tipoPedido";


    /**
     * @return lista de todos os pedidos ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Object> getAllPedidos() throws ExecutionException, InterruptedException {
        List<Object> pedidos = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;
        for (QueryDocumentSnapshot document : documents) {
            Object resposta = document.toObject(Object.class);
            pedidos.add(resposta);
        }
        return pedidos;
    }

    /*PEDIDOS DO TIPO UTILIZADOR*/

    /**
     * Método que retorna todos os pedidos do tipo utilizador
     *
     * @return lista de pedidos de utilizador ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<PedidoUtilizador> getAllPedidosUtilizador() throws ExecutionException, InterruptedException {
        List<PedidoUtilizador> pedidos = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_UTILIZADOR).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;
        for (QueryDocumentSnapshot document : documents) {
            PedidoUtilizador resposta = document.toObject(PedidoUtilizador.class);
            pedidos.add(resposta);
        }
        return pedidos;
    }


    /**
     * Metodo cria pedido do tipo PedidoUtilizador na base de dados
     *
     * @param pedido pedido a ser inserido
     * @return pedidoUtilizador created ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createPedidoUtilizador(PedidoUtilizador pedido) throws ExecutionException, InterruptedException {

        if (verifyPedidoUtilizador(pedido))
            return null;

        Firestore db = FirestoreClient.getFirestore();

        //Tem de existir numero Utilizador/Docente
        ApiFuture<QuerySnapshot> future2 = db.collection(COL_NAME_DOCENTE).whereEqualTo("docenteNumber", pedido.getDocenteId()).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if (documents2.isEmpty()) {
            return null;
        }

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
        db.collection(COL_NAME).document().set(pedido);

        return "pedidoUtilizador created";
    }


    /**
     * altera um pedido do utlizador
     *
     * @param pedido pedido a ser alterado com valores novos
     * @return pedidoUtilizador updated ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updatePedidoUtilizador(PedidoUtilizador pedido) throws ExecutionException, InterruptedException {
        if (verifyPedidoUtilizador(pedido))
            return null;
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db
                .collection(COL_NAME)
                .whereEqualTo("pedidoId", pedido.getPedidoId())
                .whereEqualTo("resposta", false)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_UTILIZADOR)
                .get();

        //update a document from firestore
        if (future.get().isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return "PedidoUtilizador updated with: " + pedido.getPedidoId();
    }


    /**
     * elimina um pedido do utilizador
     *
     * @param pedidoId id do pedido a ser eliminado
     * @return pedidoUtilizador deleted with: pedidoId ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deletePedidoUtilizador(int pedidoId) throws ExecutionException, InterruptedException {
        if (pedidoId < 0)
            return null;
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedidoId)
                .whereEqualTo("resposta", false)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_UTILIZADOR)
                .get();
        //delete a document from firestore
        if (future.get().isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return "PedidoUtilizador deleted with:" + pedidoId;
    }


    /*PEDIDOS DO TIPO MATERIAL*/

    /**
     * Método que retorna todos os pedidos do tipo PedidoMaterial
     *
     * @return lista de pedidos do tipo PedidoMaterial ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<PedidoMaterial> getAllPedidosMaterial() throws ExecutionException, InterruptedException {
        List<PedidoMaterial> pedidoMaterials = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_MATERIAL).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;
        for (QueryDocumentSnapshot document : documents) {
            PedidoMaterial resposta = document.toObject(PedidoMaterial.class);
            pedidoMaterials.add(resposta);
        }
        return pedidoMaterials;

    }

    //cria um pedido do tipo material

    /**
     * Metodo cria um pedido do tipo PedidoMaterial na base de dados
     *
     * @param pedido pedido a ser inserido
     * @return pedidoMaterial created ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createPedidoMaterial(PedidoMaterial pedido) throws ExecutionException, InterruptedException {
        if (verifyPedidoMaterial(pedido)) {
            return null;
        }
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
        db.collection(COL_NAME).document().set(pedido);

        return "pedidoMaterial created";
    }

    //altera um pedido do tipo material

    /**
     * altera um pedido do tipo PedidoMaterial
     *
     * @param pedido pedido a ser alterado com valores novos
     * @return pedidoMaterial updated ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updatePedidoMaterial(PedidoMaterial pedido) throws ExecutionException, InterruptedException {
        if (verifyPedidoMaterial(pedido))
            return null;
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedido.getPedidoId())
                .whereEqualTo("resposta", false)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", pedido.getAuthorId())
                .get();

        //update a document from firestore
        if (future.get().size() <= 0)
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return "PedidoMaterial updated with: " + pedido.getPedidoId();
    }

    //elimina pedido do tipo material sem resposta

    /**
     * elimina um pedido do tipo PedidoMaterial
     *
     * @param pedidoId id do pedido a ser eliminado
     * @param authorId id do utilizador que criou o pedido
     * @return pedidoMaterial deleted ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deletePedidoMaterial(int pedidoId, int authorId) throws ExecutionException, InterruptedException {
        if (pedidoId < 0 || authorId < 0)
            return null;
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedidoId)
                .whereEqualTo("resposta", false)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", authorId)
                .get();
        //delete a document from firestore
        if (future.get().isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return "PedidoMaterial deleted with:" + pedidoId;
    }

    /*PEDIDOS DO TIPO LABORATÓRIO*/

    /**
     * Método que retorna todos os pedidos do tipo PedidoLaboratorio
     *
     * @return lista de pedidos do tipo PedidoLaboratorio ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<PedidoLaboratorio> getAllPedidosLaboratorio() throws ExecutionException, InterruptedException {
        List<PedidoLaboratorio> pedidoLaboratorios = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_LABORATORIO).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;
        for (QueryDocumentSnapshot document : documents) {
            PedidoLaboratorio resposta = document.toObject(PedidoLaboratorio.class);
            pedidoLaboratorios.add(resposta);
        }
        return pedidoLaboratorios;

    }

    //cria um pedido do tipo laboratório

    /**
     * Metodo cria um pedido do tipo PedidoLaboratorio na base de dados
     *
     * @param pedido pedido a ser inserido
     * @return pedidoLaboratorio created ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createPedidoLaboratorio(PedidoLaboratorio pedido) throws ExecutionException, InterruptedException {
        if (verifyPedidoLaboratorio(pedido)) {
            return null;
        }
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
        db.collection(COL_NAME).document().set(pedido);

        return "pedidoLaboratorio created";
    }


    //altera um pedido do tipo laboratório

    /**
     * altera um pedido do tipo PedidoLaboratorio
     *
     * @param pedido pedido a ser alterado com valores novos
     * @return pedidoLaboratorio updated ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updatePedidoLaboratorio(PedidoLaboratorio pedido) throws ExecutionException, InterruptedException {
        if (verifyPedidoLaboratorio(pedido))
            return null;
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedido.getPedidoId())
                .whereEqualTo("resposta", false)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_LABORATORIO)
                .whereEqualTo("authorId", pedido.getAuthorId())
                .get();
        //update a document from firestore
        if (future.get().isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return "PedidoLaboratorio updated with:" + pedido.getPedidoId();
    }

    //elimina pedido do tipo laboratório sem resposta

    /**
     * elimina um pedido do tipo PedidoLaboratorio
     *
     * @param pedidoId id do pedido a ser eliminado
     * @param authorId id do utilizador que criou o pedido
     * @return pedidoLaboratorio deleted ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deletePedidoLaboratorio(int pedidoId, int authorId) throws ExecutionException, InterruptedException {
        if (pedidoId < 0 || authorId < 0)
            return null;
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", pedidoId)
                .whereEqualTo("resposta", false)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_LABORATORIO)
                .whereEqualTo("authorId", authorId)
                .get();
        //delete a document from firestore
        if (future.get().isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return "PedidoLaboratorio deleted with:" + pedidoId;
    }


    /*CASOS PARTICULARES*/
    /*
     * ADICIONAR UM MATERIAL AO PEDIDO
     * APAGAR MATERIAL DO PEDIDO
     * LER TODAS OS MATERIAIS DESSE PEDIDO
     * TROCAR DE ESTADO DO PEDIDO
     */

    /**
     * Este metodo altera o estado do pedido quando o pedido tiver sido respondido
     *
     * @param idPedido id do pedido que vai ter a alteração de estado
     * @return retorna se o pedido foi atualizado e o id do mesmo
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String changeHasResposta(String tipo, int idPedido) throws ExecutionException, InterruptedException {
        if (idPedido < 0)
            return null;
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", idPedido)
                .get();
        Pedido pedido = null;
        //update a document from firestore
        if (future.get().isEmpty())
            return null;
        switch (tipo) {
            case PEDIDO_NAME_UTILIZADOR:
                pedido = future.get().getDocuments().get(0).toObject(PedidoUtilizador.class);
                break;
            case PEDIDO_NAME_LABORATORIO:
                pedido = future.get().getDocuments().get(0).toObject(PedidoLaboratorio.class);
                break;

            case PEDIDO_NAME_MATERIAL:
                pedido = future.get().getDocuments().get(0).toObject(PedidoMaterial.class);
                break;
        }
        if (pedido == null)
            return null;
        pedido.setResposta(true);
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return "Pedido updated with:" + idPedido;
    }

    /**
     * @param idPedido
     * @param idMaterial
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addMaterial(int idPedido, List<Integer> idMaterial, int authorId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", idPedido)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", authorId)
                .get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        PedidoMaterial pedido = future.get().getDocuments().get(0).toObject(PedidoMaterial.class);
        pedido.getMateriais().addAll(idMaterial);
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    /**
     * @param idPedido
     * @param idMaterial
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteMaterial(int idPedido, int idMaterial, int authorId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", idPedido)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", authorId)
                .get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return "Pedido não encontrado para ser atualizado";
        PedidoMaterial pedido = future.get().getDocuments().get(0).toObject(PedidoMaterial.class);
        pedido.getMateriais().remove(idMaterial);
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(pedido);
        return writeResult.get().getUpdateTime().toString();
    }

    /**
     * @param idPedido
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Integer> getMateriais(int idPedido, int authorId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME)
                .whereEqualTo("pedidoId", idPedido)
                .whereEqualTo(STRING_LITERAL_TIPO, PEDIDO_NAME_MATERIAL)
                .whereEqualTo("authorId", authorId)
                .get();
        //update a document from firestore
        if (future.get().size() <= 0)
            return null;
        PedidoMaterial pedido = future.get().getDocuments().get(0).toObject(PedidoMaterial.class);
        return pedido.getMateriais();
    }

    /*METODOS AUXILIARES*/

    /**
     * Metodo para verificar se o material existe
     *
     * @param materiais lista de materiais
     * @return true se não existir, false se existir
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean checkMaterials(List<Integer> materiais) throws ExecutionException, InterruptedException {
        for (int i = 0; i < materiais.size(); i++) {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection(COL_NAME_MATERIAL)
                    .whereEqualTo("materialId", materiais.get(i))
                    .get();
            if (materiais.get(i) < 0 || future.get().isEmpty())
                return true;
        }
        return false;
    }

    /**
     * Metodo auxiliar para verificar se o docente existe na bd
     *
     * @param docente - id do docente
     * @return true se o docente existir na bd ou false se nao existir
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean checkDocentes(int docente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME_DOCENTE)
                .whereEqualTo("docenteNumber", docente)
                .get();
        if (future.get().size() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Metodo para verificar se o Pedido do tipo PedidoUtilizador é valido
     *
     * @param pedido pedido a ser verificado
     * @return true se não for valido, false for valido
     */
    private boolean verifyPedidoUtilizador(PedidoUtilizador pedido) {
        return pedido == null
                || pedido.getTipoPedido() == null || pedido.getTipoPedido().equals("")
                || pedido.getDataPedido() == null
                || pedido.getDocenteId() < 0 || pedido.getPedidoId() < 0
                || pedido.getDescricao() == null || pedido.getDescricao().length() > 64 || pedido.getDescricao().length() < 8;
    }

    /**
     * Metodo para verificar se o Pedido do tipo PedidoMaterial é valido
     *
     * @param pedido pedido a ser verificado
     * @return true se não for valido, false for valido
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean verifyPedidoMaterial(PedidoMaterial pedido) throws ExecutionException, InterruptedException {
        return pedido == null
                || pedido.getTipoPedido() == null || pedido.getTipoPedido().equals("")
                || pedido.getDataPedido() == null
                || pedido.getAuthorId() < 0 || pedido.getPedidoId() < 0
                || checkMaterials(pedido.getMateriais()) || checkDocentes(pedido.getAuthorId());
    }

    /**
     * Metodo para verificar se o Pedido do tipo PedidoLaboratorio é valido
     *
     * @param pedido pedido a ser verificado
     * @return true se não for valido, false for valido
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean verifyPedidoLaboratorio(PedidoLaboratorio pedido) throws ExecutionException, InterruptedException {
        return pedido == null
                || pedido.getTipoPedido() == null || pedido.getTipoPedido().equals("")
                || pedido.getDataPedido() == null
                || pedido.getPedidoId() < 0
                || pedido.getAuthorId() < 0 || pedido.getLabId() < 0
                || checkIfLaboratorio(pedido.getLabId());
    }

    /**
     * Metodo para verificar se o Laboratorio existe
     *
     * @param labId id do laboratorio
     * @return true se não existir, false se existir
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private boolean checkIfLaboratorio(int labId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME_LABORATORIO)
                .whereEqualTo("laboratorioId", labId)
                .get();
        return future.get().isEmpty();
    }


}
