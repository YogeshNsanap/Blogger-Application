package com.brainworks.rest.service;

import com.brainworks.rest.payloads.response.UserDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Integer userId);

    UserDto updateUser(UserDto userDto,Integer userId);

    void deleteUser(Integer userId);

    List<UserDto> getAlluser();

    UserDto updateSpecificField(Integer userId, Map<String, Object> fields);
    UserDto registration(UserDto userDto);
}
