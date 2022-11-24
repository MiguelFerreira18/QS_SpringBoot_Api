package com.example.test.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int autorId;
    private int pedidoId;
    private int labId;
    private List<String> etiquetas;
    private boolean resposta;
    private String tipoPedido;
    private int respostaId;

    /*!*NÃO ESQUECER DE PERGUNTAR AO QUENTAL*!*/

    /*FAZER DEPOIS PEDIDOS ABSTRATOS*/
    public Pedido(int respostaId,int autorId,int pedidoId, int labId, boolean resposta, String tipoPedido) {
        this.autorId = autorId;
        this.labId = labId;
        this.etiquetas = new ArrayList<>();
        this.resposta = resposta;
        this.tipoPedido = tipoPedido;
        this.pedidoId = pedidoId;
        this.respostaId = respostaId;
    }

    public Pedido() {
    }

    public int getRespostaId() {
        return respostaId;
    }

    public void setRespostaId(int respostaId) {
        this.respostaId = respostaId;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public int getLabId() {
        return labId;
    }

    public void setLabId(int labId) {
        this.labId = labId;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public boolean isResposta() {
        return resposta;
    }

    public void setResposta(boolean resposta) {
        this.resposta = resposta;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }
}
