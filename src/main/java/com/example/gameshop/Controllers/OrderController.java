package com.example.gameshop.Controllers;

import com.example.gameshop.Controllers.Requests.CreateOrderRequest;
import com.example.gameshop.Data.Entities.OrderEntity;
import com.example.gameshop.Services.Dto.OrderDto;
import com.example.gameshop.Services.Interfaces.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {
    private final OrderService service;
    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }
    @GetMapping("findById")
    ResponseEntity<?> findById(@RequestParam("id") Long id) {
        try {
            var order = service.findById(id);
            return ResponseEntity.ok(order);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("create")
    ResponseEntity<?> create(@RequestBody CreateOrderRequest request) {
        try {
            var order = service.create(request.userId(), request.gameIds());
            return ResponseEntity.ok(order);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("findAllByUser")
    ResponseEntity<?> findAllByUser(@RequestParam("userId") Long userId) {
        try {
            var orders = service.findAllByUser(userId);
            return ResponseEntity.ok(orders);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("addGameToOrder")
    ResponseEntity<?> addGameToOrder(@RequestParam("orderId")Long orderId,@RequestParam("gameId") Long gameId) {
        try {
            service.addGameToOrder(orderId,gameId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("removeGameFromOrder")
    ResponseEntity<?> removeGameFromOrder(@RequestParam("orderId")Long orderId,@RequestParam("gameId") Long gameId) {
        try {
            service.removeGameFromOrder(orderId,gameId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("delete")
    ResponseEntity<?> delete(@RequestParam("id")Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
