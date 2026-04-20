package com.example.gameshop;

import com.example.gameshop.Controllers.Requests.LoginRequest;
import com.example.gameshop.Controllers.Response.UserResponseDto;
import com.example.gameshop.Data.Entities.UserEntity;
import com.example.gameshop.Data.Repositories.UserRepository;
import com.example.gameshop.Services.Impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.InvalidParameterException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserServiceImpl service;

    private static final String login = "test";
    private static final String password = "test";
    private static final String passwordEncoded = "hash";

    @Test
    void loginSuccessTest() {
        LoginRequest request = new LoginRequest(login,password);
        UserEntity entity = new UserEntity();
        entity.setLogin(login);
        entity.setPasswordHash(passwordEncoded);
        UserResponseDto dto = new UserResponseDto();
        dto.setLogin(login);
        when(repository.findByLogin(login)).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches(password,passwordEncoded)).thenReturn(true);
        when(modelMapper.map(entity,UserResponseDto.class)).thenReturn(dto);
        UserResponseDto res = service.login(request);
        assertEquals(res, dto);
        verify(modelMapper).map(entity,UserResponseDto.class);
        verify(repository).findByLogin(login);
        verify(passwordEncoder).matches(password,passwordEncoded);
    }

    @Test
    void loginNotFoundTest() {
        LoginRequest request = new LoginRequest(login,password);

        when(repository.findByLogin(login)).thenReturn(Optional.empty());

        Exception e = assertThrows(EntityNotFoundException.class,() -> service.login(request));

        assertEquals(e.getMessage(),"User not found");
        verify(repository).findByLogin(login);
    }

    @Test
    void loginWrongPasswordTest() {
        LoginRequest request = new LoginRequest(login,password);
        UserEntity entity = new UserEntity();
        entity.setLogin(login);
        entity.setPasswordHash(passwordEncoded);

        when(repository.findByLogin(login)).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches(password,passwordEncoded)).thenReturn(false);

        Exception e = assertThrows(InvalidParameterException.class, () -> service.login(request));

        assertEquals("Invalid password",e.getMessage());
        verify(repository).findByLogin(login);
        verify(passwordEncoder).matches(password,passwordEncoded);
    }


}