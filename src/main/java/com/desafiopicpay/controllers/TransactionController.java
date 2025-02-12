package com.desafiopicpay.controllers;

import com.desafiopicpay.domain.dtos.TransactionDto;
import com.desafiopicpay.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(description = "Endpoint encargado de crear una transaccion.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Éxito en la creación de la transacción."),
                    @ApiResponse(responseCode = "403", description = "Error en la autenticacion de la transaccion."),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
            }
    )
    public ResponseEntity<TransactionDto> crearTransaccion(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transactionDto));
    }
}
