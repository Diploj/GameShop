package com.example.gameshop.Services.Dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class OrderDto {
    private Long id;
    private Date deliveryDate;
    private List<GameDto> games = new ArrayList<>();
}
