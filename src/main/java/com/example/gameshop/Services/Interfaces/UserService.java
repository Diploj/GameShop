package com.example.gameshop.Services.Interfaces;

import com.example.gameshop.Services.Dto.UserDto;

public interface UserService {
    void register(String login,String password);
    UserDto login(String login, String password);
    void update(UserDto user, String password);

}
