package com.desafiopicpay.mapper;

import com.desafiopicpay.domain.dtos.UserDto;
import com.desafiopicpay.domain.users.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUser implements IMapper<UserDto, User>{

    @Override
    public User map(UserDto in) {
        User user = new User();
        user.setName(in.name());
        user.setLastName(in.lastName());
        user.setDocument(in.document());
        user.setEmail(in.email());
        user.setPassword(in.password());
        user.setBalance(in.balance());
        user.setUserType(in.userType());
        return user;
    }
}
