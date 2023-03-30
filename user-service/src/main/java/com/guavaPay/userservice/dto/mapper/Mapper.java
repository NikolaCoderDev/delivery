package com.guavaPay.userservice.dto.mapper;

import com.guavaPay.userservice.dto.UserDto;
import com.guavaPay.userservice.dto.UserDtoReq;
import com.guavaPay.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public User mapToUser(UserDto dto) {

        Long id = null;

        if (dto.getId() != null) {
            id = Long.valueOf(dto.getId());
        }

        return User.builder()
                .id(id)
                .role(dto.getRole())
                .password(dto.getPassword())
                .login(dto.getLogin())
                .accessToken(dto.getAccessToken())
                .build();
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId().toString())
                .login(user.getLogin())
                .accessToken(user.getAccessToken())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }

    public User mapFromUserDtoReq(UserDtoReq user) {
        return User.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }

}
