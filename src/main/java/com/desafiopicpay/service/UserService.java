package com.desafiopicpay.service;

import com.desafiopicpay.domain.dtos.UserDto;
import com.desafiopicpay.domain.users.User;
import com.desafiopicpay.domain.users.UserType;
import com.desafiopicpay.exceptions.DoYourExceptions;
import com.desafiopicpay.mapper.UserDtoToUser;
import com.desafiopicpay.mapper.UserToUserDto;
import com.desafiopicpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserDtoToUser mapUserDtoToUser;

    private final UserToUserDto mapUserToUserDto;

    @Transactional
    public void createUser(UserDto userDTO) throws Exception {
        log.info("Iniciando proceso de mapeo y creación de usuario.");
        try {
            User user = mapUserDtoToUser.map(userDTO);
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<UserDto> usersList() {
        try {
            return userRepository.findAll()
                    .stream()
                    .map(mapUserToUserDto::map)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>() {
        };
    }

    public void authenticateTransaction(UserDto sender, BigDecimal value){
        if(sender.userType() == UserType.MERCHANT){
            throw new DoYourExceptions("El ususario " + UserType.MERCHANT +" no puede realizar transferencias.",
                    HttpStatus.FORBIDDEN);
        }

        if(sender.balance().compareTo(value) < 0){
            throw new DoYourExceptions("El usuario no cuenta con el saldo suficiente para realizar transferencias.",
                    HttpStatus.FORBIDDEN);
        }
    }
}