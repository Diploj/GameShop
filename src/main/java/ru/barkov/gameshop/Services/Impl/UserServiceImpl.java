package ru.barkov.gameshop.Services.Impl;

import ru.barkov.gameshop.Controllers.Requests.LoginRequest;
import ru.barkov.gameshop.Controllers.Requests.RegisterRequest;
import ru.barkov.gameshop.Controllers.Requests.UpdateUserRequestDto;
import ru.barkov.gameshop.Data.Entities.UserEntity;
import ru.barkov.gameshop.Data.Repositories.UserRepository;
import ru.barkov.gameshop.Controllers.Response.UserResponseDto;
import ru.barkov.gameshop.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        UserEntity user = new UserEntity();
        user.setLogin(registerRequest.login());
        user.setPasswordHash(encoder.encode(registerRequest.password()));
        repository.save(user);
    }

    public UserResponseDto login(LoginRequest loginRequest) {
        Optional<UserEntity> entity = repository.findByLogin(loginRequest.login());
        if(entity.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        UserEntity user = entity.get();
        if(!encoder.matches(loginRequest.password(),user.getPasswordHash())){
            throw new InvalidParameterException("Invalid password");
        }
        UserResponseDto dto = mapper.map(user,UserResponseDto.class);
        return dto;
    }
    @Transactional
    public void update(UpdateUserRequestDto userDto) {
        Optional<UserEntity> entity = repository.findById(userDto.userid());
        if(entity.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        UserEntity user = entity.get();
        if(!encoder.matches(userDto.oldPassword(), user.getPasswordHash())){
            throw new InvalidParameterException("Invalid password");
        }
        mapper.map(userDto,user);
        if(userDto.newPassword() != null){
            user.setPasswordHash(encoder.encode(userDto.newPassword()));
        }
        repository.save(user);
    }

}
