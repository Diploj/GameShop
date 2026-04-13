package com.example.gameshop.Controllers.Requests;

public record UpdateGameRequestDto(
        Long gameId,
        String name,
        double price
){}
