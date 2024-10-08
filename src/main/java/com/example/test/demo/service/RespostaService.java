package com.example.test.demo.service;

import com.example.test.demo.model.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class RespostaService {
    @Autowired
    private LaboratorioService laboratorioService;
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private MaterialService materialService;

    private static final String COL_NAME = "resposta";
    private static final String PATH_QUARY_DOCENTE = "docente";
    private static final String COL_NAME_LAB = "laboratorio";
    private static final String COL_NAME_MATERIAL = "material";
    private static final String COL_NAME_PEDIDO = "pedido";

    private static final String TIPO_PEDIDO_LAB = "pedidoLaboratorio";
    private static final String TIPO_PEDIDO_MATERIAL = "pedidoMaterial";
    private static final String TIPO_PEDIDO_UTILIZADOR = "pedidoUtilizador";


    /*TODAS AS RESPOSTAS */

    /**
     * Metodo que retorna todas as respostas
     *
     * @return retorna todas as respostas ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Object> getAllRespostas() throws ExecutionException, InterruptedException {
        List<Object> respostas = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty()) {
            return null;
        }
        for (QueryDocumentSnapshot document : documents) {
            Object resposta = document.toObject(Object.class);
            respostas.add(resposta);
        }
        return respostas;
    }


    /*RESPOSTA LABORATORIO*/

    /**
     * Metodo que retorna apenas as respostas de laboratorio
     *
     * @return retorna todas as respostas de laboratorio ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<RespostaLaboratorio> getRespostaLaboratorio() throws ExecutionException, InterruptedException {
        List<RespostaLaboratorio> respostasLaboratorio = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty()) {
            return null;
        }
        for (QueryDocumentSnapshot document : documents) {
            RespostaLaboratorio resposta = document.toObject(RespostaLaboratorio.class);
            respostasLaboratorio.add(resposta);
        }
        return respostasLaboratorio;
    }

    /**
     * Metodo para inserir uma respostaLaboratorio na base de dados
     *
     * @param resposta resposta a ser inserida na base de dados
     * @return RespostaLaboratorio created ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createRespostaLaboratorio(RespostaLaboratorio resposta, int id) throws ExecutionException, InterruptedException {
        System.out.println("1");
        System.out.println(resposta);
        if (checkRespostaLab(resposta) || checkIfPedidoHasResposta(id)) {
            return null;
        }
        System.out.println("2");
        Firestore db = FirestoreClient.getFirestore();
        //Docente tem de existir
        ApiFuture<QuerySnapshot> future2 = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNumber", resposta.getUtilizadorId()).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if (documents2.isEmpty()) {
            return null;
        }

        //Lab tem de existir
        ApiFuture<QuerySnapshot> future3 = db.collection(COL_NAME_LAB).whereEqualTo("laboratorioId", resposta.getLaboratorioId()).get();
        List<QueryDocumentSnapshot> documents3 = future3.get().getDocuments();
        if (documents3.isEmpty()) {
            return null;
        }

        //Pedido tem de existir para ser respondido
        ApiFuture<QuerySnapshot> future4 = db.collection(COL_NAME_PEDIDO).whereEqualTo("pedidoId", resposta.getPedidoId()).get();
        List<QueryDocumentSnapshot> documents4 = future4.get().getDocuments();
        if (documents4.isEmpty()) {
            return null;
        }


        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Resposta oldResposta = null;
/*
        if(documents.isEmpty())
        {
            return null;
        }

 */
        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldResposta = doc.toObject(RespostaLaboratorio.class);
            if (oldResposta.getRespostaId() > biggest) {
                biggest = oldResposta.getRespostaId();

            }
        }
        resposta.setRespostaId(biggest + 1);
        /*ADICIONA UM NOVO PEDIDO*/
        db.collection(COL_NAME).document().set(resposta);
        laboratorioService.addRespostaLaboratorio(id, resposta.getRespostaId());
        pedidoService.changeHasResposta(TIPO_PEDIDO_LAB,id);
        return "RespostaLaboratorio created";
    }

    /**
     * Metodo para atualizar uma RespostaLaboratorio na base de dados
     *
     * @param resposta resposta recebida como parametro para substituir uma resposta na base de dados
     * @return RespostaLaboratorio updated with: respostaId ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateRespostaLaboratorio(RespostaLaboratorio resposta) throws ExecutionException, InterruptedException {
        if (checkRespostaLab(resposta)) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        //Numero Docente tem de existir
        ApiFuture<QuerySnapshot> future2 = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNumber", resposta.getUtilizadorId()).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if (documents2.isEmpty()) {
            return null;
        }
        //Lab tem de existir
        ApiFuture<QuerySnapshot> future3 = db.collection(COL_NAME_LAB).whereEqualTo("laboratorioId", resposta.getLaboratorioId()).get();
        List<QueryDocumentSnapshot> documents3 = future3.get().getDocuments();
        if (documents3.isEmpty()) {
            return null;
        }

        //Pedido tem de existir para ser atualizado
        ApiFuture<QuerySnapshot> future4 = db.collection(COL_NAME_PEDIDO).whereEqualTo("pedidoId", resposta.getPedidoId()).get();
        List<QueryDocumentSnapshot> documents4 = future4.get().getDocuments();
        if (documents4.isEmpty()) {
            return null;
        }

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", resposta.getRespostaId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return "RespostaLaboratorio updated with:" + resposta.getRespostaId();
    }

    /*RESPOSTA MATERIAL*/

    /**
     * Metodo que retorna todas as respostas do tipo RespostaMaterial
     *
     * @return lista RespostaMaterial
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<RespostaMaterial> getRespostaMaterial() throws ExecutionException, InterruptedException {

        List<RespostaMaterial> respostasMaterial = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty()) {
            return null;
        }
        for (QueryDocumentSnapshot document : documents) {
            RespostaMaterial resposta = document.toObject(RespostaMaterial.class);
            respostasMaterial.add(resposta);
        }
        return respostasMaterial;
    }


    /**
     * Metodo para criar uma RespostaMaterial na base de dados
     *
     * @param resposta resposta do tipo material a receber como parametro para ser adicionada na base de dados
     * @return retorna null em caso de falha ou respostaMaterial created
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createRespostaMaterial(RespostaMaterial resposta, int id) throws ExecutionException, InterruptedException {

        if (checkRespostaMaterial(resposta) || checkIfPedidoHasResposta(id)) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        System.out.println("RespostaMaterial: 1");
        //Material tem de existir
        if (!resposta.getMateriaisId().isEmpty()) {
            ApiFuture<QuerySnapshot> future2 = db.collection(COL_NAME_MATERIAL).whereEqualTo("materialId", resposta.getMateriaisId().get(0)).get();
            List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();

            if (documents2.isEmpty()) {
                return null;
            }
        }
        System.out.println("RespostaMaterial: 2");
        //Docente tem de existir
        ApiFuture<QuerySnapshot> future3 = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNumber", resposta.getUtilizadorId()).get();
        List<QueryDocumentSnapshot> documents3 = future3.get().getDocuments();
        if (documents3.isEmpty()) {
            return null;
        }

        System.out.println("RespostaMaterial: 3");
        //Pedido tem de existir para ser respondido
        ApiFuture<QuerySnapshot> future4 = db.collection(COL_NAME_PEDIDO).whereEqualTo("pedidoId", resposta.getPedidoId()).get();
        List<QueryDocumentSnapshot> documents4 = future4.get().getDocuments();
        if (documents4.isEmpty()) {
            return null;
        }

        System.out.println("RespostaMaterial: 4");
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        /*
        if(documents.isEmpty())
        {
            return null;
        }

         */
        int biggest = -1;
        Resposta oldResposta = null;

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldResposta = doc.toObject(RespostaMaterial.class);
            if (oldResposta.getRespostaId() > biggest) {
                biggest = oldResposta.getRespostaId();

            }
        }
        resposta.setRespostaId(biggest + 1);
        /*ADICIONA UMA NOVA RESPOSTA*/
        ApiFuture<WriteResult> colApiFuture = db.collection(COL_NAME).document().create(resposta);
        pedidoService.changeHasResposta(TIPO_PEDIDO_MATERIAL,id);
        System.out.println(resposta.getMateriaisId());
        materialService.addRespostaToMaterial(resposta.getRespostaId(),resposta.getMateriaisId().get(0));
        return "respostaMaterial created";
    }

    /**
     * @param resposta resposta do tipoMaterial a receber como parametro para ser atualizada na base de dados
     * @return RespostaMaterial updated with: respostaId ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateRespostaMaterial(RespostaMaterial resposta) throws ExecutionException, InterruptedException {
        if (checkRespostaMaterial(resposta)) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        //Material tem de existir


        ApiFuture<QuerySnapshot> future2 = db.collection(COL_NAME_MATERIAL).whereIn("materialId", resposta.getMateriaisId()).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if (documents2.isEmpty()) {
            return null;
        }
        //Docente tem de existir
        ApiFuture<QuerySnapshot> future3 = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNumber", resposta.getUtilizadorId()).get();
        List<QueryDocumentSnapshot> documents3 = future3.get().getDocuments();
        if (documents3.isEmpty()) {
            return null;
        }

        //Pedido tem de existir para ser atualizado
        ApiFuture<QuerySnapshot> future4 = db.collection(COL_NAME_PEDIDO).whereEqualTo("pedidoId", resposta.getPedidoId()).get();
        List<QueryDocumentSnapshot> documents4 = future4.get().getDocuments();
        if (documents4.isEmpty()) {
            return null;
        }

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", resposta.getRespostaId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;

        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return "RespostaMaterial updated with:" + resposta.getRespostaId();
    }

    /*RESPOSTA UTILIZADOR*/

    /**
     * Metodo que retorna todas as RespostasUtilziador
     *
     * @return RespostasUtilizador ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<RespostaUtilizador> getRespostaUtilizador() throws ExecutionException, InterruptedException {
        List<RespostaUtilizador> respostasUtilizador = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty()) {
            return null;
        }
        for (QueryDocumentSnapshot document : documents) {
            RespostaUtilizador resposta = document.toObject(RespostaUtilizador.class);
            respostasUtilizador.add(resposta);
        }
        return respostasUtilizador;
    }

    /**
     * Metodo para criar uma RespostaUtilizador na base de dados
     *
     * @param resposta RespostaUtilizador recebida como parametro para substituir uma RespostaUtilizador na base de dados
     * @return respostaUtilizador created ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String createRespostaUtilizador(RespostaUtilizador resposta, int id) throws ExecutionException, InterruptedException {
        if (checkRespostaUtilizador(resposta)  || checkIfPedidoHasResposta(id)) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();

        //Tem de existir o id  do Utilizador/Docente
        ApiFuture<QuerySnapshot> future2 = db.collection(PATH_QUARY_DOCENTE)
                .whereEqualTo("docenteNumber", resposta.getUtilizadorId()).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if (documents2.isEmpty()) {
            return null;
        }
        //Pedido tem de existir para ser respondido
        ApiFuture<QuerySnapshot> future5 = db.collection(COL_NAME_PEDIDO).whereEqualTo("pedidoId", resposta.getPedidoId()).get();
        List<QueryDocumentSnapshot> documents5 = future5.get().getDocuments();
        if (documents5.isEmpty()) {
            return null;
        }
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        int biggest = -1;
        Resposta oldResposta = null;
        /*
        if(documents.isEmpty())
        {
            return null;
        }

         */

        /*AUTO INCREMENTA O ID QUANDO ADICIONA*/
        for (QueryDocumentSnapshot doc : documents) {
            oldResposta = doc.toObject(RespostaUtilizador.class);
            if (oldResposta.getRespostaId() > biggest) {
                biggest = oldResposta.getRespostaId();

            }
        }
        resposta.setRespostaId(biggest + 1);


        /*ADICIONA UM NOVO PEDIDO*/
        //SE A RESPOSTA TIVER A DIZRE QUE FOI ACEITE O ESTADO DO DOCENTE MUDA PARA 1 SE NÃO, MUDA PARA -1(DEVE SER ELIMINADO O DOCENTE)
        if (resposta.isAceite()) {
            ApiFuture<QuerySnapshot> docenteQuery = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNumber", resposta.getUtilizadorId()).get();
            Docente docenteWithAccess = docenteQuery.get().getDocuments().get(0).toObject(Docente.class);
            docenteWithAccess.setHasAccess(1);
            db.collection("docente").document(docenteQuery.get().getDocuments().get(0).getId()).set(docenteWithAccess);

        } else if (!resposta.isAceite()) {
            /*!PERGUNTAR AO QUENTAL COMO PROCEDER (SE APAGA OU NÃO O DOCENTE AQUI)!*/
            //Apaga docente caso a resposta nao seja aceite
            ApiFuture<QuerySnapshot> future4 = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNumber", resposta.getUtilizadorId()).get();
            List<QueryDocumentSnapshot> documents4 = future4.get().getDocuments();
            if (documents4.isEmpty()) {
                return null;
            }
            db.collection(PATH_QUARY_DOCENTE).document(documents4.get(0).getId()).delete();

            /* Codigo anterior
            ApiFuture<QuerySnapshot> docenteQuery = db.collection(PATH_QUARY_DOCENTE).whereEqualTo("docenteNome", resposta.getNomeUtilizador()).get();
            Docente docenteWithAccess = docenteQuery.get().getDocuments().get(0).toObject(Docente.class);
            docenteWithAccess.setHasAccess(-1);
            db.collection(PATH_QUARY_DOCENTE).document(docenteQuery.get().getDocuments().get(0).getId()).set(docenteWithAccess);

             */

        }

        db.collection(COL_NAME).document().set(resposta);
        pedidoService.changeHasResposta(TIPO_PEDIDO_UTILIZADOR,id);
        return "RespostaUtilizador created";
    }

    /**
     * Metodo para eliminar qualquer resposta da base de dados
     *
     * @param id identificador da resposta a eliminar
     * @return RespostaUtilizador deleted with: id ou null em caso de erro
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteResposta(int id) throws ExecutionException, InterruptedException {
        if (id < 0) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", id).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).delete();
        return "RespostaUtilizador deleted with:" + id;
    }

    /**
     * Metodo para atualizar uma RespostaUtilizador
     *
     * @param resposta RespostaUtilizador a receber como parametro para substituir a RespostaUtilziador da base de dados
     * @return RespostaUtilizador updated with: respostaId ou null em caso de falha
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String updateRespostaUtilizador(RespostaUtilizador resposta) throws ExecutionException, InterruptedException {
        if (checkRespostaUtilizador(resposta)) {
            return null;
        }
        Firestore db = FirestoreClient.getFirestore();

        //Tem de existir o id e nome do Utilizador/Docente
        ApiFuture<QuerySnapshot> future2 = db.collection(PATH_QUARY_DOCENTE)
                .whereEqualTo("docenteNumber", resposta.getUtilizadorId())
                .whereEqualTo("descricao", resposta.getDescricao()).get();
        List<QueryDocumentSnapshot> documents2 = future2.get().getDocuments();
        if (documents2.isEmpty()) {
            return null;
        }

        //Pedido tem de existir para ser atualizado
        ApiFuture<QuerySnapshot> future4 = db.collection(COL_NAME_PEDIDO).whereEqualTo("pedidoId", resposta.getPedidoId()).get();
        List<QueryDocumentSnapshot> documents4 = future4.get().getDocuments();
        if (documents4.isEmpty()) {
            return null;
        }

        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", resposta.getRespostaId()).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        if (documents.isEmpty())
            return null;
        db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return "RespostaUtilizador updated with:" + resposta.getRespostaId();
    }
    /*CASOS PARTICULARES*/
    /*
     *ADICIONA MATERIAL A UMA RESPOSTA
     *LER TODOS OS MATERIAIS DE UMA RESPOSTA
     *
     */


    /**
     * @param respostaId
     * @param materialId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String addMaterialToResposta(int respostaId, int materialId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", respostaId).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser atualizada";
        RespostaMaterial resposta = future.get().getDocuments().get(0).toObject(RespostaMaterial.class);
        resposta.getMateriaisId().add(materialId);
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return writeResult.get().getUpdateTime().toString();

    }

    /**
     * @param respostaId
     * @param materialId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public String deleteMaterialFromResposta(int respostaId, int materialId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", respostaId).get();
        if (future.get().size() <= 0)
            return "Resposta não encontrada para ser atualizada";
        RespostaMaterial resposta = future.get().getDocuments().get(0).toObject(RespostaMaterial.class);
        resposta.getMateriaisId().remove(materialId);
        ApiFuture<WriteResult> writeResult = db.collection(COL_NAME).document(future.get().getDocuments().get(0).getId()).set(resposta);
        return writeResult.get().getUpdateTime().toString();
    }

    /**
     * @param respostaId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Integer> getMateriaisFromResposta(int respostaId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME).whereEqualTo("respostaId", respostaId).get();
        if (future.get().size() <= 0)
            return null;
        RespostaMaterial resposta = future.get().getDocuments().get(0).toObject(RespostaMaterial.class);
        return resposta.getMateriaisId();
    }

    //Metodos auxiliares

    private boolean checkRespostaLab(RespostaLaboratorio respostaLaboratorio) {
        if ( respostaLaboratorio.getUtilizadorId() < 0
                || respostaLaboratorio.getRespostaId() < 0
                || respostaLaboratorio.getLaboratorioId() < 0
                || respostaLaboratorio.getData() == null || respostaLaboratorio.getData().isEmpty()
                || respostaLaboratorio.getData().length() < 4 || respostaLaboratorio.getData().length() > 20
                || respostaLaboratorio.getDataReservaInicio() == null || respostaLaboratorio.getDataReservaInicio().isEmpty()
                || respostaLaboratorio.getDataReservaInicio().length() < 8 || respostaLaboratorio.getDataReservaInicio().length() > 14
                || respostaLaboratorio.getDataReservaFim() == null || respostaLaboratorio.getDataReservaFim().isEmpty()
                || respostaLaboratorio.getDataReservaFim().length() < 8 || respostaLaboratorio.getDataReservaFim().length() > 14
                || respostaLaboratorio.getPedidoId() < 0) {
            return true;
        }
        return false;
    }

    private boolean checkRespostaMaterial(RespostaMaterial resMat) {
        if (!resMat.getMateriaisId().isEmpty()) {
            for (int i = 0; i < resMat.getMateriaisId().size(); i++) {
                if (resMat.getMateriaisId().get(i) < 0) {
                    return true;
                }
            }
        }
        if (resMat.getRespostaId() < 0
                || resMat.getUtilizadorId() < 0
                || resMat.getDataEntrega() == null || resMat.getDataEntrega().isEmpty()
                || resMat.getDataEntrega().length() < 8 || resMat.getDataEntrega().length() > 14
                || resMat.getDataReserva() == null || resMat.getDataReserva().isEmpty()
                || resMat.getDataReserva().length() < 8 || resMat.getDataReserva().length() > 14
                || resMat.getData() == null || resMat.getData().isEmpty()
                || resMat.getData().length() < 8 || resMat.getData().length() > 14
                || resMat.getDescricao().equalsIgnoreCase("") || resMat.getDescricao() == null
                || resMat.getDescricao().length() < 8 || resMat.getDescricao().length() > 64
                || resMat.getPedidoId() < 0) {
            return true;
        }

        return false;
    }

    private boolean checkRespostaUtilizador(RespostaUtilizador resUti) {
        if (resUti.getUtilizadorId() < 0
                || resUti.getData() == null || resUti.getData().equalsIgnoreCase("")
                || resUti.getData().length() < 4 || resUti.getData().length() > 18
                || resUti.getDescricao().equalsIgnoreCase("") || resUti.getDescricao() == null
                || resUti.getDescricao().length() < 4 || resUti.getDescricao().length() > 250
                || resUti.getPedidoId() < 0) {
            return true;
        }
        return false;
    }
    public boolean checkIfPedidoHasResposta(int pedidoId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COL_NAME_PEDIDO).whereEqualTo("pedidoId", pedidoId).whereEqualTo("resposta",true).get();
        if (future.get().isEmpty())
            return false;
        return true;
    }
}
