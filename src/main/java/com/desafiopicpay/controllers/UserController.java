package com.desafiopicpay.controllers;

import com.desafiopicpay.domain.dtos.UserDto;
import com.desafiopicpay.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    @PostMapping
    @Operation(description = "Endpoint encargado de crear usuario.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Éxito en la creación de usuario."),
                    @ApiResponse(responseCode = "400", description = "Error en la creacion."),
                    @ApiResponse(responseCode = "500",description = "Error interno del servidor.")
            }
    )
    public ResponseEntity<Void>createUser(@RequestBody UserDto userDto)throws Exception{
        userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(description = "Endpoint encargado de listar todos los usuarios.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Éxito en listar los usuarios."),
                    @ApiResponse(responseCode = "400", description = "Error en en listado."),
                    @ApiResponse(responseCode = "500",description = "Error interno del servidor.")
            }
    )
    public ResponseEntity<List<UserDto>>getAllUsers(){
        if(userService.usersList().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userService.usersList());
    }
}
