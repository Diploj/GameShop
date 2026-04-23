package ru.barkov.gameshop.Controllers.Requests;

public record UpdateGameRequestDto(
        Long gameId,
        String name,
        double price
){}
