package com.brainworks.rest.service.impl;

import com.brainworks.rest.configuration.AppConstants;
import com.brainworks.rest.entities.Role;
import com.brainworks.rest.entities.User;
import com.brainworks.rest.exceptions.ResourceNotFoundException;
import com.brainworks.rest.payloads.response.UserDto;
import com.brainworks.rest.repositories.RoleRepository;
import com.brainworks.rest.repositories.UserRepository;
import com.brainworks.rest.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder paswordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        user.setPassword (paswordEncoder.encode (user.getPassword ()));
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    public UserDto registration(UserDto userDto) {
        System.out.println (userDto);
        User user=this.dtoToUser(userDto);
        user.setPassword (paswordEncoder.encode (user.getPassword ()));
        Role role = this.roleRepository.findById (AppConstants.ROLE_USER).get ();
        user.getRole ().add (role);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public List<UserDto> getAlluser(){
        List<UserDto> allUser=this.userRepository.findAll().stream()
                //.map(x->new UserDto(x.getUserId(), x.getName(), x.getEmail(), x.getPassword(), x.getAbout()))
                //.map(x->this.userToDto(x))
                .map(this::userToDto)
                .collect(Collectors.toList());
        return allUser;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","uId",userId));
        user.setUserId (userDto.getUserId());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        User savedUser =userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateSpecificField(Integer userId, Map<String, Object> fields) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "uId", userId));
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            Optional<Field> optionalField = Optional.ofNullable (field);
            if (optionalField.isPresent ()) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            } else {
                // Handle the case where the field is not found.
                // You may want to log a warning or throw an exception.
                System.err.println("Field not found: " + key);
            }
        });
        User savedUser = userRepository.save(user);
        return this.userToDto(savedUser);
    }
    @Override
    public UserDto getUserById(Integer userId) {
        User user =this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        return this.userToDto(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user =this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        this.userRepository.deleteById(userId);
    }

    public User dtoToUser (UserDto userDto) {
        return this.modelMapper.map(userDto,User.class);
    }

    public UserDto userToDto (User user) {
        UserDto userDto=new UserDto();
        return this.modelMapper.map(user,UserDto.class);
    }
}