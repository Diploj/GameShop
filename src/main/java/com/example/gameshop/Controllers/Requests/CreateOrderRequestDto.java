package com.example.gameshop.Controllers.Requests;

import java.util.List;

public record CreateOrderRequestDto(
        Long userId,
        List<Long> gameIds
){}

