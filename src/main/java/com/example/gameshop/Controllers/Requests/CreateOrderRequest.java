package com.example.gameshop.Controllers.Requests;

import java.util.List;

public record CreateOrderRequest (
        Long userId,
        List<Long> gameIds
){}

