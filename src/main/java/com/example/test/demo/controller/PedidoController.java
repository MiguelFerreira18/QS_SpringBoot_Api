package com.example.test.demo.controller;

import com.example.test.demo.model.PedidoLaboratorio;
import com.example.test.demo.model.PedidoMaterial;
import com.example.test.demo.model.PedidoUtilizador;
import com.example.test.demo.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/all")
    public List<Object> getallPedidos() throws InterruptedException, ExecutionException {
        return pedidoService.getAllPedidos();
    }
    /*HTTP REQUESTS FOR PEDIDO UTILIZADOR (ignore a gralha)*/

    @GetMapping("/allUtilizadores")
    public List<PedidoUtilizador> getPedidosUtilizador() throws Exception {
        return pedidoService.getAllPedidosUtilizador();
    }
    @PostMapping("/utilizador")
    public void addPedidoUtilizador(@RequestBody PedidoUtilizador pedido) throws ExecutionException, InterruptedException {
        pedidoService.createPedidoUtilizador(pedido);
    }
    @PutMapping("/utilizador")
    public void updatePedidoUtilizador(@RequestBody PedidoUtilizador pedido) throws ExecutionException, InterruptedException {
        pedidoService.updatePedidoUtilizador(pedido);
    }
    @DeleteMapping("/utilizador/{id}")
    public void deletePedidoUtilizador(@PathVariable String id) throws ExecutionException, InterruptedException {
        pedidoService.deletePedidoUtilizador(Integer.parseInt(id));
    }

    /*HTTP REQUESTS FOR PEDIDO MATERIAL*/
    @GetMapping("/allMateriais")
    public List<PedidoMaterial> getPedidosMaterial() throws Exception {
        return pedidoService.getAllPedidosMaterial();
    }
    @PostMapping("/material")
    public void addPedidoMaterial(@RequestBody PedidoMaterial pedido) throws ExecutionException, InterruptedException {
        pedidoService.createPedidoMaterial(pedido);
    }
    @PutMapping("/material")
    public void updatePedidoMaterial(@RequestBody PedidoMaterial pedido) throws ExecutionException, InterruptedException {
        pedidoService.updatePedidoMaterial(pedido);
    }
    @DeleteMapping("/material/{id}")
    public void deletePedidoMaterial(@PathVariable int id,@RequestBody int authorId) throws ExecutionException, InterruptedException {
        pedidoService.deletePedidoMaterial(id,authorId);
    }

    /*HTTP REQUESTS FOR PEDIDO LABORATORIO*/
    @GetMapping("/allLaboratorios")
    public List<PedidoLaboratorio> getPedidosLaboratorio() throws Exception {
        return pedidoService.getAllPedidosLaboratorio();
    }
    @PostMapping("/laboratorio")
    public void addPedidoLaboratorio(@RequestBody PedidoLaboratorio pedido) throws ExecutionException, InterruptedException {
        pedidoService.createPedidoLaboratorio(pedido);
    }
    @PutMapping("/laboratorio")
    public void updatePedidoLaboratorio(@RequestBody PedidoLaboratorio pedido) throws ExecutionException, InterruptedException {
        pedidoService.updatePedidoLaboratorio(pedido);
    }
    @DeleteMapping("/laboratorio/{id}")
    public void deletePedidoLaboratorio(@PathVariable int id,@RequestBody int authorId) throws ExecutionException, InterruptedException {
        pedidoService.deletePedidoLaboratorio(id,authorId);
    }


    /*CASOS PARTICULARES*/

    @PostMapping("/createEtiquetaPedido/{id}")
    public String createEtiquetaPedido(@PathVariable int id, @RequestBody ArrayList<Integer> idMaterial, @RequestBody int authorId) throws Exception {
        return pedidoService.addMaterial(id,idMaterial,authorId);
    }
    @DeleteMapping("/deleteEtiquetaPedido/{id}")
    public String deleteEtiquetaPedido(@PathVariable int id,@RequestBody Integer idMaterial,@RequestBody int authorId) throws Exception {
        return pedidoService.deleteMaterial(id,idMaterial,authorId);
    }
    @GetMapping("/getEtiquetasPedido/{id}")
    public List<Integer> getEtiquetasPedido(@PathVariable int id,@RequestBody int authorId) throws Exception {
        return pedidoService.getMateriais(id,authorId);
    }



}
