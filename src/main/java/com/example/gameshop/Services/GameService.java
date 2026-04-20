package com.example.gameshop.Services;

import com.example.gameshop.Controllers.Requests.CreateGameRequestDto;
import com.example.gameshop.Controllers.Requests.UpdateGameRequestDto;
import com.example.gameshop.Controllers.Response.GameResponseDto;

import java.util.List;

public interface GameService {
    GameResponseDto findById(Long id);
    List<GameResponseDto> findAllByName(String name);
    GameResponseDto create(CreateGameRequestDto gameDto);
    void update(UpdateGameRequestDto gameDto);
    void delete(Long id);
}
