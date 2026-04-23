package ru.barkov.gameshop.Controllers.Requests;

public record CreateGameRequestDto(
    String name,
    double price
){}
