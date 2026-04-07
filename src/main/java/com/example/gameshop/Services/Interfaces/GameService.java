package com.example.gameshop.Services.Interfaces;

import com.example.gameshop.Controllers.Requests.UpdateGameRequest;
import com.example.gameshop.Services.Dto.GameDto;

import java.util.List;

public interface GameService {
    GameDto findById(Long id);
    List<GameDto> findAllByName(String name);
    GameDto create(String name, double price);
    void update(GameDto gameDto);
    void delete(Long id);
}
