package com.example.gameshop.Services.Implementation;

import com.example.gameshop.Data.Entities.OrderEntity;
import com.example.gameshop.Data.Repositories.GameRepository;
import com.example.gameshop.Data.Repositories.OrderRepository;
import com.example.gameshop.Data.Repositories.UserRepository;
import com.example.gameshop.Services.Dto.OrderDto;
import com.example.gameshop.Services.Interfaces.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            GameRepository gameRepository,
            UserRepository userRepository,
            ModelMapper mapper) {
        this.orderRepository = orderRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public OrderDto findById(Long id) {
        var entity = orderRepository.findById(id);
        if(entity.isEmpty()){
            throw new EntityNotFoundException("Order not found");
        }
        var dto = new OrderDto();
        mapper.map(entity.get(),dto);
        return dto;
    }

    @Transactional
    public OrderDto create(Long userId, List<Long> gamesIds) {
        var order = new OrderEntity();
        var userEntity = userRepository.findById(userId);
        if(userEntity.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        var games = gameRepository.findAllById(gamesIds);
        order.setGames(games);
        order.setUser(userEntity.get());
        Date date = Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        order.setDeliveryDate(date);
        var dto = new OrderDto();
        mapper.map(orderRepository.save(order),dto);
        return dto;
    }

    public List<OrderDto> findAllByUser(Long userId) {
        var entitiesList = orderRepository.findAllByUserId(userId);
        var dtoList = new ArrayList<OrderDto>();
        mapper.map(entitiesList,dtoList);
        return dtoList;
    }

    @Transactional
    public void addGameToOrder(Long orderId, Long gameId) {
        var gameEntity = gameRepository.findById(gameId);
        var orderEntity = orderRepository.findById(orderId);
        if(gameEntity.isEmpty()){
            throw new EntityNotFoundException("Game not found");
        }
        if(orderEntity.isEmpty()){
            throw new EntityNotFoundException("Order not found");
        }
        orderEntity.get().getGames().add(gameEntity.get());
        orderRepository.save(orderEntity.get());
    }

    @Transactional
    public void removeGameFromOrder(Long orderId, Long gameId) {
        var gameEntity = gameRepository.findById(gameId);
        var orderEntity = orderRepository.findById(orderId);
        if(gameEntity.isEmpty()){
            throw new EntityNotFoundException("Game not found");
        }
        if(orderEntity.isEmpty()){
            throw new EntityNotFoundException("Order not found");
        }
        orderEntity.get().getGames().remove(gameEntity.get());
        orderRepository.save(orderEntity.get());
    }

    @Transactional
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
