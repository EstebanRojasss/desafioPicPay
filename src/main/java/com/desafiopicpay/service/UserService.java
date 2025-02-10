package com.desafiopicpay.service;

import com.desafiopicpay.domain.dtos.UserDto;
import com.desafiopicpay.domain.users.User;
import com.desafiopicpay.mapper.UserDtoToUser;
import com.desafiopicpay.mapper.UserToUserDto;
import com.desafiopicpay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        } catch (DataIntegrityViolationException uniqueValueException) {
            throw new Exception("A ingresado un valor que no puede ser duplicado. Documento/Email");
        } catch (HttpMessageNotReadableException valueParserError) {
            throw new Exception("Ocurrió un error al ingresar uno o mas valores. No coinciden con el tipo de valor requerido.");
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
}