package com.example.gameshop.Data.Repositories;

import com.example.gameshop.Data.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    @Query("SELECT o FROM OrderEntity o Where o.user.id = :user_id")
    List<OrderEntity> findAllByUserId(@Param("user_id")Long userId);
}
