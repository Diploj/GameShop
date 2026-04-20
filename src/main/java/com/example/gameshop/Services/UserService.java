package com.example.gameshop.Services;

import com.example.gameshop.Controllers.Requests.LoginRequest;
import com.example.gameshop.Controllers.Requests.RegisterRequest;
import com.example.gameshop.Controllers.Requests.UpdateUserRequestDto;
import com.example.gameshop.Controllers.Response.UserResponseDto;

public interface UserService {
    void register(RegisterRequest registerRequest);
    UserResponseDto login(LoginRequest loginRequest);
    void update(UpdateUserRequestDto userDto);

}
