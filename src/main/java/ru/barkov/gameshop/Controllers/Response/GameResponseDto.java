package ru.barkov.gameshop.Controllers.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResponseDto {
    private Long id;

    private String name;

    private double price;
}
