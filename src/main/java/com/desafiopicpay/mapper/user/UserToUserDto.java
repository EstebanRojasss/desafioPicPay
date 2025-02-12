package com.desafiopicpay.mapper.user;

import com.desafiopicpay.domain.dtos.UserDto;
import com.desafiopicpay.domain.users.User;
import com.desafiopicpay.mapper.IMapper;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDto implements IMapper<User, UserDto> {


    @Override
    public UserDto map(User in) {
        UserDto userDto = new UserDto(
                in.getName(),
                in.getLastName(),
                in.getEmail(),
                in.getDocument(),
                in.getUserType(),
                in.getBalance()
                );
        return userDto;
    }
}
