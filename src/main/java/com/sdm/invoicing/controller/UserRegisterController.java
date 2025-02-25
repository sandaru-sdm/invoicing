package com.sdm.invoicing.controller;

import com.sdm.invoicing.service.auth.AuthService;
import com.sdm.invoicing.dto.UserDto;
import com.sdm.invoicing.dto.UserRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class UserRegisterController {
    private final AuthService authService;

    public UserRegisterController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest){
        if(authService.hasUserWithEmail(userRegisterRequest.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(userRegisterRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
