package com.desafiopicpay.mapper.user;

import com.desafiopicpay.domain.dtos.UserDto;
import com.desafiopicpay.domain.users.User;
import com.desafiopicpay.mapper.IMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUser implements IMapper<UserDto, User> {

    @Override
    public User map(UserDto in) {
        User user = new User();
        user.setName(in.firstName());
        user.setLastName(in.lastName());
        user.setDocument(in.document());
        user.setEmail(in.email());
        user.setPassword(in.password());
        user.setBalance(in.balance());
        user.setUserType(in.userType());
        return user;
    }
}
