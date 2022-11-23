package com.example.test.demo.controller;

import com.example.test.demo.model.Pedido;
import com.example.test.demo.service.PedidoService;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/all")
    public List<Pedido> getPedido() throws Exception {
        return pedidoService.getPedido();
    }
    @PostMapping("/create")
    public String createPedido(@RequestBody Pedido pedido) throws Exception {
        return pedidoService.savePedido(pedido);
    }
    @DeleteMapping("/{id}")
    public String deletePedido(@PathVariable int id) throws Exception {
        return pedidoService.deletePedido(id);
    }
    @PutMapping("/update")
    public String updatePedido(@RequestBody Pedido pedido) throws Exception {
        return pedidoService.updatePedido(pedido);
    }

    /*CASOS PARTICULARES*/

    @PostMapping("/createEtiquetaPedido/{id}")
    public String createEtiquetaPedido(@PathVariable int id,@RequestBody String nomeEtiqueta) throws Exception {
        return pedidoService.addEtiqueta(id,nomeEtiqueta);
    }
    @DeleteMapping("/deleteEtiquetaPedido/{id}")
    public String deleteEtiquetaPedido(@PathVariable int id,@RequestBody String nomeEtiqueta) throws Exception {
        return pedidoService.deleteEtiqueta(id,nomeEtiqueta);
    }
    @GetMapping("/getEtiquetasPedido/{id}")
    public List<String> getEtiquetasPedido(@PathVariable int id) throws Exception {
        return pedidoService.getEtiquetas(id);
    }



}
