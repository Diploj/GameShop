package com.example.gameshop;

import com.example.gameshop.Controllers.Requests.UpdateGameRequestDto;
import com.example.gameshop.Controllers.Response.GameResponseDto;
import com.example.gameshop.Data.Entities.GameEntity;
import com.example.gameshop.Data.Repositories.GameRepository;
import com.example.gameshop.Services.Impl.GameServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
    @Mock
    private GameRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private GameServiceImpl service;

    private static final Long gameId = 1L;
    private static final String name = "test1";
    private static final double price = 1;
    @Test
    void findByIdSuccessTest() {
        Long id = 1L;
        GameEntity entity = new GameEntity();
        entity.setId(id);
        GameResponseDto dto = new GameResponseDto();
        dto.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity,GameResponseDto.class)).thenReturn(dto);
        GameResponseDto res = service.findById(id);
        assertEquals(res, dto);
        verify(modelMapper).map(entity,GameResponseDto.class);
        verify(repository).findById(id);
    }

    @Test
    void findByIdNotFoundTest() {
        when(repository.findById(gameId)).thenReturn(Optional.empty());
        Exception e = assertThrows(EntityNotFoundException.class,() -> {service.findById(gameId);});
        assertEquals(e.getMessage(),"Game not found");
        verify(repository).findById(gameId);
    }

    @Test
    void updateSuccessTest() {
        UpdateGameRequestDto dto = new UpdateGameRequestDto(gameId,name, price);
        GameEntity entity = new GameEntity();

        when(repository.findById(gameId)).thenReturn(Optional.of(entity));
        doAnswer(invocation -> {
            UpdateGameRequestDto src = invocation.getArgument(0);
            GameEntity dest = invocation.getArgument(1);
            dest.setName(src.name());
            dest.setPrice(src.price());
            return null;
        }).when(modelMapper).map(any(UpdateGameRequestDto.class), any(GameEntity.class));

        service.update(dto);

        assertEquals(entity.getName(), name);
        assertEquals(entity.getPrice(), price);
        verify(repository).save(entity);
        verify(modelMapper).map(dto, entity);
    }

    @Test
    void updateNotFoundTest() {
        Long gameId = 1L;
        String name = "test";
        double price = 1;
        UpdateGameRequestDto requestDto = new UpdateGameRequestDto(gameId, name,price);
        when(repository.findById(gameId)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class, () -> service.update(requestDto));
        assertEquals(e.getMessage(),"Game not found");
        verify(repository, never()).save(any());
    }


}