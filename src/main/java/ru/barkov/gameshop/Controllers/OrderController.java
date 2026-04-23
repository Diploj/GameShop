package ru.barkov.gameshop.Controllers;

import ru.barkov.gameshop.Controllers.Requests.CreateOrderRequestDto;
import ru.barkov.gameshop.Controllers.Response.OrderResponseDto;
import ru.barkov.gameshop.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    ResponseEntity<OrderResponseDto> findOrderById(@RequestParam("id") Long id) {
        OrderResponseDto order = service.findById(id);
        return ResponseEntity.ok(order);
    }
    @PostMapping("create")
    ResponseEntity<OrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto request) {
        OrderResponseDto order = service.create(request.userId(), request.gameIds());
        return ResponseEntity.ok(order);
    }

    @GetMapping("findAllByUser")
    ResponseEntity<List<OrderResponseDto>> findAllOrdersByUser(@RequestParam("userId") Long userId) {
        List<OrderResponseDto> orders = service.findAllByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("addGameToOrder")
    ResponseEntity<Long> addGameToOrder(@RequestParam("orderId")Long orderId,@RequestParam("gameId") Long gameId) {
        service.addGameToOrder(orderId,gameId);
        return ResponseEntity.ok(orderId);
    }

    @PostMapping("removeGameFromOrder")
    ResponseEntity removeGameFromOrder(@RequestParam("orderId")Long orderId,@RequestParam("gameId") Long gameId) {
        service.removeGameFromOrder(orderId,gameId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete")
    ResponseEntity deleteOrder(@RequestParam("id")Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
