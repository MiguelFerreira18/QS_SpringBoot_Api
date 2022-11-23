package com.example.test.demo.model;

import java.util.List;

public class Pedido {
    private int autorId;
    private int pedidoId;
    private int labId;
    private List<EtiquetaMaterial> etiquetas;
    private boolean resposta;
    private String tipoPedido;
    private int respostaId;

    public Pedido(int respostaId,int autorId,int pedidoId, int labId, List<EtiquetaMaterial> etiquetas, boolean resposta, String tipoPedido) {
        this.autorId = autorId;
        this.labId = labId;
        this.etiquetas = etiquetas;
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

    public List<EtiquetaMaterial> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<EtiquetaMaterial> etiquetas) {
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
