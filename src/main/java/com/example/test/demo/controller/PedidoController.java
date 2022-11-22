package com.example.test.demo.controller;

import com.example.test.demo.model.Pedido;
import com.example.test.demo.service.PedidoService;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/getPedido")
    public List<Pedido> getPedido() throws Exception {
        return pedidoService.getPedido();
    }
    @PostMapping("/createPedido")
    public String createPedido(@RequestBody Pedido pedido) throws Exception {
        return pedidoService.savePedido(pedido);
    }
    @DeleteMapping("/deletePedido")
    public String deletePedido(@RequestBody Pedido pedido) throws Exception {
        return pedidoService.deletePedido(pedido);
    }
    @PutMapping("/updatePedido")
    public String updatePedido(@RequestBody Pedido pedido) throws Exception {
        return pedidoService.updatePedido(pedido);
    }



}
