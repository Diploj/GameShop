package com.example.gameshop.Controllers;

import com.example.gameshop.Controllers.Requests.CreateOrderRequestDto;
import com.example.gameshop.Controllers.Response.OrderResponseDto;
import com.example.gameshop.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {
    private final OrderService service;
    @GetMapping("findById")
    ResponseEntity<OrderResponseDto> findById(@RequestParam("id") Long id) {
        OrderResponseDto order = service.findById(id);
        return ResponseEntity.ok(order);
    }
    @PostMapping("create")
    ResponseEntity<OrderResponseDto> create(@RequestBody CreateOrderRequestDto request) {
        OrderResponseDto order = service.create(request.userId(), request.gameIds());
        return ResponseEntity.ok(order);
    }

    @GetMapping("findAllByUser")
    ResponseEntity<List<OrderResponseDto>> findAllByUser(@RequestParam("userId") Long userId) {
        List<OrderResponseDto> orders = service.findAllByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("addGameToOrder")
    ResponseEntity addGameToOrder(@RequestParam("orderId")Long orderId,@RequestParam("gameId") Long gameId) {
        service.addGameToOrder(orderId,gameId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("removeGameFromOrder")
    ResponseEntity removeGameFromOrder(@RequestParam("orderId")Long orderId,@RequestParam("gameId") Long gameId) {
        service.removeGameFromOrder(orderId,gameId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("delete")
    ResponseEntity delete(@RequestParam("id")Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
