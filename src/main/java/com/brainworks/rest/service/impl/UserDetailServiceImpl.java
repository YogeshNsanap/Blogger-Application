package com.brainworks.rest.service.impl;

import com.brainworks.rest.entities.User;
import com.brainworks.rest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= this.userRepository.findByEmail (username).orElseThrow(()-> new RuntimeException("invalid username....!"));
        return user;
}
}