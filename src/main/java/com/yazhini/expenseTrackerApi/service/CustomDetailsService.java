package com.yazhini.expenseTrackerApi.service;

import com.yazhini.expenseTrackerApi.entity.UserEntity;
import com.yazhini.expenseTrackerApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomDetailsService implements UserDetailsService {



    @Autowired
    private UserRepository uRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity existingUser= uRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found :"+email));
        return  new org.springframework.security.core.userdetails.User(existingUser.getEmail(),existingUser.getPassword(),new ArrayList<>());
}}
