package ru.barkov.gameshop;

import ru.barkov.gameshop.Controllers.Response.OrderResponseDto;
import ru.barkov.gameshop.Data.Entities.GameEntity;
import ru.barkov.gameshop.Data.Entities.OrderEntity;
import ru.barkov.gameshop.Data.Repositories.GameRepository;
import ru.barkov.gameshop.Data.Repositories.OrderRepository;
import ru.barkov.gameshop.Data.Repositories.UserRepository;
import ru.barkov.gameshop.Services.Impl.OrderServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private OrderServiceImpl service;
    @Test
    void findByIdSuccessTest() {
        Long id = 1L;
        OrderEntity entity = new OrderEntity();
        OrderResponseDto dto = new OrderResponseDto();
        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity,OrderResponseDto.class)).thenReturn(dto);
        OrderResponseDto res = service.findById(id);
        assertEquals(res, dto);
        verify(modelMapper).map(entity,OrderResponseDto.class);
        verify(orderRepository).findById(id);
    }

    @Test
    void findByIdNotFoundTest() {
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        Exception e = assertThrows(EntityNotFoundException.class,() -> {service.findById(id);});
        assertEquals(e.getMessage(),"Order not found");
        verify(orderRepository).findById(id);
    }

    @Test
    void addGameToOrderSuccessTest() {
        Long id = 1L;
        OrderEntity order = new OrderEntity();
        GameEntity game = new GameEntity();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        service.addGameToOrder(id,id);
        assertTrue(order.getGames().contains(game));
        verify(orderRepository).findById(id);
        verify(gameRepository).findById(id);
        verify(orderRepository).save(order);
    }

    @Test
    void removeGameFromOrderSuccessTest() {
        Long id = 1L;
        OrderEntity order = new OrderEntity();
        GameEntity game = new GameEntity();
        order.getGames().add(game);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        service.removeGameFromOrder(id,id);
        assertFalse(order.getGames().contains(game));
        verify(orderRepository).findById(id);
        verify(gameRepository).findById(id);
        verify(orderRepository).save(order);
    }
}