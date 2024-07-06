package com.yazhini.expenseTrackerApi.service;

import com.yazhini.expenseTrackerApi.Exception.ItemAlreadyExistsException;
import com.yazhini.expenseTrackerApi.Exception.ResourceNotFoundException;
import com.yazhini.expenseTrackerApi.entity.UserEntity;
import com.yazhini.expenseTrackerApi.entity.UserModel;
import com.yazhini.expenseTrackerApi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepo;


    private PasswordEncoder encoder;



    @Override
    public UserEntity createUser(UserModel user) {
        if(userRepo.existsByEmail(user.getEmail()))
            throw new ItemAlreadyExistsException("User already regesitered:"+user.getEmail());
        UserEntity userEntity= new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        userEntity.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity read(Long id) throws ResourceNotFoundException {
        return userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found:"+id));

    }

    @Override
    public UserEntity update(UserEntity user, Long id)throws ResourceNotFoundException {
        UserEntity oUser=read(id);
        oUser.setName(user.getName()!=null? user.getName() : oUser.getName());
        oUser.setEmail(user.getEmail()!=null? user.getEmail() : oUser.getEmail());
        oUser.setPassword(user.getPassword()!=null? encoder.encode(user.getPassword()) : oUser.getPassword());
        oUser.setAge(user.getAge()!=null? user.getAge() : oUser.getAge());
        return userRepo.save(oUser);


    }

    @Override
    public void delete(Long id) {
        UserEntity user= read(id);
        userRepo.delete(user);

    }

    @Override
    public UserEntity getLoggedInUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found:"+email));
    }
}
