package com.example.gameshop.Services.Implementation;

import com.example.gameshop.Data.Entities.UserEntity;
import com.example.gameshop.Data.Repositories.UserRepository;
import com.example.gameshop.Services.Dto.UserDto;
import com.example.gameshop.Services.Interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;
    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder encoder, ModelMapper mapper) {
        this.repository = repository;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    @Transactional
    public void register(String login, String password) {
        var user = new UserEntity();
        user.setLogin(login);
        user.setPasswordHash(encoder.encode(password));
        repository.save(user);
    }

    public UserDto login(String login, String password) {
        var entity = repository.findByLogin(login);
        if(entity.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        var user = entity.get();
        if(!encoder.matches(password,user.getPasswordHash())){
            throw new InvalidParameterException("Invalid password");
        }
        var dto = new UserDto();
        mapper.map(entity,dto);
        return dto;
    }
    @Transactional
    public void update(UserDto userDto, String password) {
        var entity = repository.findById(userDto.getId());
        if(entity.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        var user = entity.get();
        if(!encoder.matches(password, user.getPasswordHash())){
            throw new InvalidParameterException("Invalid password");
        }
        userDto.setId(null);
        mapper.map(userDto,user);
        repository.save(user);
    }

}
