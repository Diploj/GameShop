package com.example.gameshop.Controllers.Requests;

public record UpdateGameRequest (
        Long id,
        String name,
        double price
){}
