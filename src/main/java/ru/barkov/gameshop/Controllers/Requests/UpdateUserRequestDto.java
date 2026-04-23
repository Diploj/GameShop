package ru.barkov.gameshop.Controllers.Requests;

public record UpdateUserRequestDto(
        Long userid,
        String login,
        String oldPassword,
        String newPassword
){}
