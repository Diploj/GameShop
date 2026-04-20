package com.example.gameshop.Services.Impl;

import com.example.gameshop.Controllers.Requests.CreateGameRequestDto;
import com.example.gameshop.Controllers.Requests.UpdateGameRequestDto;
import com.example.gameshop.Data.Entities.GameEntity;
import com.example.gameshop.Data.Repositories.GameRepository;
import com.example.gameshop.Controllers.Response.GameResponseDto;
import com.example.gameshop.Services.GameService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository repository;
    private final ModelMapper mapper;

    public GameResponseDto findById(Long id) {
        Optional<GameEntity> entity = repository.findById(id);
        if(entity.isEmpty()){
            throw new EntityNotFoundException("Game not found");
        }
        GameResponseDto dto = mapper.map(entity.get(),GameResponseDto.class);
        return dto;
    }


    public List<GameResponseDto> findAllByName(String name) {
        List<GameEntity> entitiesList = repository.findAllByNameContains(name);
        return entitiesList.stream()
                .map(entity -> mapper.map(entity, GameResponseDto.class))
                .toList();
    }

    @Transactional
    public GameResponseDto create(CreateGameRequestDto gameDto) {
        GameEntity entity = new GameEntity();
        entity.setName(gameDto.name());
        entity.setPrice(gameDto.price());
        GameResponseDto dto = mapper.map(repository.save(entity),GameResponseDto.class);
        return dto;
    }

    @Transactional
    public void update(UpdateGameRequestDto gameDto) {
        Optional<GameEntity> entity = repository.findById(gameDto.gameId());
        if(entity.isEmpty())
            throw new EntityNotFoundException("Game not found");
        GameEntity game = entity.get();
        mapper.map(gameDto,game);
        repository.save(game);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
