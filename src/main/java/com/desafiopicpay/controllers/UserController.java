package com.desafiopicpay.controllers;

import com.desafiopicpay.domain.dtos.UserDto;
import com.desafiopicpay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void>createUser(@RequestBody UserDto userDto){
        userService.createUserCommon(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>>getAllUsers(){
        if(userService.usersList().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userService.usersList());
    }
}
