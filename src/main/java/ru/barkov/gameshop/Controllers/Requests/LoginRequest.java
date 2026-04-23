package ru.barkov.gameshop.Controllers.Requests;

public record LoginRequest(
        String login,
        String password) {
}
