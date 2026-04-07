package com.example.gameshop.Controllers.Requests;

public record CreateGameRequest (
    String name,
    double price
){}
