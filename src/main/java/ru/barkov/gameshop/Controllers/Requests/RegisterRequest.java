package ru.barkov.gameshop.Controllers.Requests;

public record RegisterRequest (
    String login,
    String password
){}
