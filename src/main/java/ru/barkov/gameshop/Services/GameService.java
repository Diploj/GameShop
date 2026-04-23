package ru.barkov.gameshop.Services;

import ru.barkov.gameshop.Controllers.Requests.CreateGameRequestDto;
import ru.barkov.gameshop.Controllers.Requests.UpdateGameRequestDto;
import ru.barkov.gameshop.Controllers.Response.GameResponseDto;

import java.util.List;

public interface GameService {
    GameResponseDto findById(Long id);
    List<GameResponseDto> findAllByName(String name);
    GameResponseDto create(CreateGameRequestDto gameDto);
    void update(UpdateGameRequestDto gameDto);
    void delete(Long id);
}
