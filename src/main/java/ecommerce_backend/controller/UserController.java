package ecommerce_backend.controller;


import ecommerce_backend.dto.UserRequestDto;
import ecommerce_backend.dto.UserResponseDto;
import ecommerce_backend.entity.User;
import ecommerce_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto request){
        return userService.createUser(request);
    }
}
