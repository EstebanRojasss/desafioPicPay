package com.desafiopicpay.service;

import com.desafiopicpay.domain.dtos.UserDto;
import com.desafiopicpay.domain.users.User;
import com.desafiopicpay.mapper.UserDtoToUser;
import com.desafiopicpay.mapper.UserToUserDto;
import com.desafiopicpay.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDtoToUser mapUserDtoToUser;
    @Autowired
    private UserToUserDto mapUserToUserDto;


    public void createUserCommon(UserDto userDTO) {
        log.info("Iniciando proceso de mapeo y creación de usuario.");
        try {
            User user = mapUserDtoToUser.map(userDTO);
            userRepository.save(user);
        } catch (Exception e) {
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
        return null;
    }
}