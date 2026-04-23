package ru.barkov.gameshop.Controllers;

import ru.barkov.gameshop.Controllers.Requests.CreateGameRequestDto;
import ru.barkov.gameshop.Controllers.Requests.UpdateGameRequestDto;
import ru.barkov.gameshop.Controllers.Response.GameResponseDto;
import ru.barkov.gameshop.Services.GameService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/game")
public class GameController {
    private final GameService service;
    private final ModelMapper mapper;
    @GetMapping("findById")
    public ResponseEntity<GameResponseDto> findGameById(@RequestParam("id") Long id){
        GameResponseDto game = service.findById(id);
        return ResponseEntity.ok(game);
    }
    @GetMapping("findAllByName")
    public ResponseEntity<List<GameResponseDto>> findAllGamesByName(@RequestParam("name")String name){
        List<GameResponseDto> games = service.findAllByName(name);
        return ResponseEntity.ok(games);
    }
    @PostMapping("create")
    public ResponseEntity<GameResponseDto> createGame(@RequestBody CreateGameRequestDto request){
        GameResponseDto game = service.create(request);
        return ResponseEntity.ok(game);
    }
    @PostMapping("update")
    public ResponseEntity updateGame(@RequestBody UpdateGameRequestDto request){
        service.update(request);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("delete")
    public ResponseEntity deleteGame(@RequestParam("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
