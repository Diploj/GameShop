package ru.barkov.gameshop.Services.Impl;

import ru.barkov.gameshop.Data.Entities.GameEntity;
import ru.barkov.gameshop.Data.Entities.OrderEntity;
import ru.barkov.gameshop.Data.Entities.UserEntity;
import ru.barkov.gameshop.Data.Repositories.GameRepository;
import ru.barkov.gameshop.Data.Repositories.OrderRepository;
import ru.barkov.gameshop.Data.Repositories.UserRepository;
import ru.barkov.gameshop.Controllers.Response.OrderResponseDto;
import ru.barkov.gameshop.Services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public OrderResponseDto findById(Long id) {
        Optional<OrderEntity> entity = orderRepository.findById(id);
        if(entity.isEmpty()){
            throw new EntityNotFoundException("Order not found");
        }
        OrderResponseDto dto = mapper.map(entity.get(),OrderResponseDto.class);
        return dto;
    }

    @Transactional
    public OrderResponseDto create(Long userId, List<Long> gamesIds) {
        OrderEntity order = new OrderEntity();
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if(userEntity.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        List<GameEntity> games = gameRepository.findAllById(gamesIds);
        order.setGames(games);
        order.setUser(userEntity.get());
        Date date = Date.from(LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        order.setDeliveryDate(date);
        OrderResponseDto dto = mapper.map(orderRepository.save(order),OrderResponseDto.class);
        return dto;
    }

    public List<OrderResponseDto> findAllByUser(Long userId) {
        List<OrderEntity> entitiesList = orderRepository.findAllByUserId(userId);
        return entitiesList.stream()
                .map(entity -> mapper.map(entity, OrderResponseDto.class))
                .toList();
    }

    @Transactional
    public void addGameToOrder(Long orderId, Long gameId) {
        Optional<GameEntity> gameEntity = gameRepository.findById(gameId);
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
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
        Optional<GameEntity> gameEntity = gameRepository.findById(gameId);
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
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
