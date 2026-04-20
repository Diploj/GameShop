package com.example.gameshop.Services;

import com.example.gameshop.Controllers.Response.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto findById(Long id);
    OrderResponseDto create(Long userId, List<Long> gamesIds);
    List<OrderResponseDto> findAllByUser(Long userId);
    void addGameToOrder(Long orderId,Long gameId);
    void removeGameFromOrder(Long orderId,Long gameId);
    void delete(Long id);

}
