package ru.barkov.gameshop.Data.Repositories;

import ru.barkov.gameshop.Data.Entities.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository  extends JpaRepository<GameEntity,Long> {
    List<GameEntity> findAllByNameContains(String name);
}
