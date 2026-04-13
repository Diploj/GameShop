package com.example.gameshop.Controllers;

import com.example.gameshop.Controllers.Requests.CreateGameRequestDto;
import com.example.gameshop.Controllers.Requests.UpdateGameRequestDto;
import com.example.gameshop.Controllers.Response.GameResponseDto;
import com.example.gameshop.Services.GameService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/game")
public class GameController {
    private final GameService service;
    private final ModelMapper mapper;
    @GetMapping("findById")
    public ResponseEntity<GameResponseDto> findById(@RequestParam("id") Long id){
        GameResponseDto game = service.findById(id);
        return ResponseEntity.ok(game);
    }
    @GetMapping("findAllByName")
    public ResponseEntity<List<GameResponseDto>> findAllByName(@RequestParam("name")String name){
        List<GameResponseDto> games = service.findAllByName(name);
        return ResponseEntity.ok(games);
    }
    @PostMapping("create")
    public ResponseEntity<GameResponseDto> create(@RequestBody CreateGameRequestDto request){
        GameResponseDto game = service.create(request);
        return ResponseEntity.ok(game);
    }
    @PostMapping("update")
    public ResponseEntity update(@RequestBody UpdateGameRequestDto request){
        service.update(request);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestParam("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
