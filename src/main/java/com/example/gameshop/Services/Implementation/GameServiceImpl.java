package com.example.gameshop.Services.Implementation;

import com.example.gameshop.Data.Entities.GameEntity;
import com.example.gameshop.Data.Repositories.GameRepository;
import com.example.gameshop.Services.Dto.GameDto;
import com.example.gameshop.Services.Interfaces.GameService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository repository;
    private final ModelMapper mapper;
    @Autowired
    public GameServiceImpl(GameRepository repository,ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public GameDto findById(Long id) {
        var entity = repository.findById(id);
        if(entity.isEmpty()){
            throw new EntityNotFoundException("Order not found");
        }
        var dto = new GameDto();
        mapper.map(entity.get(),dto);
        return dto;
    }


    public List<GameDto> findAllByName(String name) {
        var entitiesList = repository.findAllByNameContains(name);
        return entitiesList.stream()
                .map(entity -> mapper.map(entity, GameDto.class))
                .toList();
    }

    @Transactional
    public GameDto create(String name, double price) {
        var entity = new GameEntity();
        entity.setName(name);
        entity.setPrice(price);
        var dto = new GameDto();
        mapper.map(repository.save(entity),dto);
        return dto;
    }

    @Transactional
    public void update(GameDto gameDto) {
        var entity = repository.findById(gameDto.getId());
        if(entity.isEmpty())
            throw new EntityNotFoundException("Game not found");
        gameDto.setId(null);
        var game = entity.get();
        mapper.map(gameDto,game);
        repository.save(game);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
