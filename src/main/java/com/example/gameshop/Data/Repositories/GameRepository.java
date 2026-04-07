package com.example.gameshop.Data.Repositories;

import com.example.gameshop.Data.Entities.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository  extends JpaRepository<GameEntity,Long> {
    List<GameEntity> findAllByNameContains(String name);
}
