package com.example.gameshop.Controllers.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Date deliveryDate;
    private List<GameResponseDto> games = new ArrayList<>();
}
