package com.example.gameshop.Services.Interfaces;

import com.example.gameshop.Data.Entities.OrderEntity;
import com.example.gameshop.Services.Dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto findById(Long id);
    OrderDto create(Long userId, List<Long> gamesIds);
    List<OrderDto> findAllByUser(Long userId);
    void addGameToOrder(Long orderId,Long gameId);
    void removeGameFromOrder(Long orderId,Long gameId);
    void delete(Long id);

}
