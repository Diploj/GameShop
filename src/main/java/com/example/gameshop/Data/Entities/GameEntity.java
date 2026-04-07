package com.example.gameshop.Data.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    @Column(nullable = false)
    private double price;
    @ManyToMany(mappedBy = "games")
    private List<OrderEntity> orders = new ArrayList<>();

}
