package com.example.gameshop.Controllers;

import com.example.gameshop.Controllers.Requests.CreateGameRequest;
import com.example.gameshop.Controllers.Requests.UpdateGameRequest;
import com.example.gameshop.Services.Dto.GameDto;
import com.example.gameshop.Services.Interfaces.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/game")
public class GameController {
    private final GameService service;
    private final ModelMapper mapper;
    @Autowired
    public GameController(GameService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id") Long id){
        try {
            var game = service.findById(id);
            return ResponseEntity.ok(game);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("findAllByName")
    public ResponseEntity<?> findAllByName(@RequestParam("name")String name){
        try {
            var games = service.findAllByName(name);
            return ResponseEntity.ok(games);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CreateGameRequest request){
        try {
            var game = service.create(request.name(), request.price());
            return ResponseEntity.ok(game);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody UpdateGameRequest request){
        try {
            var dto = new GameDto();
            mapper.map(request,dto);
            service.update(dto);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id){
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
