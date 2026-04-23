package ru.barkov.gameshop.Services;

import ru.barkov.gameshop.Controllers.Requests.LoginRequest;
import ru.barkov.gameshop.Controllers.Requests.RegisterRequest;
import ru.barkov.gameshop.Controllers.Requests.UpdateUserRequestDto;
import ru.barkov.gameshop.Controllers.Response.UserResponseDto;

public interface UserService {
    void register(RegisterRequest registerRequest);
    UserResponseDto login(LoginRequest loginRequest);
    void update(UpdateUserRequestDto userDto);

}
