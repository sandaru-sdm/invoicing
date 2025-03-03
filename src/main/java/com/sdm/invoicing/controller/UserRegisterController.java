package com.sdm.invoicing.controller;

import com.sdm.invoicing.service.UserDetailsServiceImpl;
import com.sdm.invoicing.service.auth.AuthService;
import com.sdm.invoicing.dto.UserDto;
import com.sdm.invoicing.dto.UserRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class UserRegisterController {
    private final AuthService authService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public UserRegisterController(AuthService authService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.authService = authService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = authService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        UserDto userDto = authService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        if(authService.hasUserWithEmail(userRegisterRequest.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(userRegisterRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRegisterRequest userRegisterRequest) {
        UserDto userDto = authService.updateUser(id, userRegisterRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
