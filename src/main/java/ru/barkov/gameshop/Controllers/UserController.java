package ru.barkov.gameshop.Controllers;

import ru.barkov.gameshop.Controllers.Requests.LoginRequest;
import ru.barkov.gameshop.Controllers.Requests.RegisterRequest;
import ru.barkov.gameshop.Controllers.Response.UserResponseDto;
import ru.barkov.gameshop.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request){
        service.register(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequest request){
        UserResponseDto user = service.login(request);
        return ResponseEntity.ok(user);
    }

}
