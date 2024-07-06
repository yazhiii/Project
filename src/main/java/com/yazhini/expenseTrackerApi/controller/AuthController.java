package com.yazhini.expenseTrackerApi.controller;

import com.yazhini.expenseTrackerApi.entity.JwtResponse;
import com.yazhini.expenseTrackerApi.entity.LoginModel;
import com.yazhini.expenseTrackerApi.entity.UserEntity;
import com.yazhini.expenseTrackerApi.entity.UserModel;
import com.yazhini.expenseTrackerApi.service.CustomDetailsService;
import com.yazhini.expenseTrackerApi.service.UserService;
import com.yazhini.expenseTrackerApi.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginModel login) throws Exception {

         authenticate(login.getEmail(),login.getPassword());
       final UserDetails userDetails= userDetailsService.loadUserByUsername(login.getEmail());
        final String token= jwtTokenUtil.generateToken(userDetails);


        return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<UserEntity> save(@Valid @RequestBody UserModel user)
    {
        return new ResponseEntity<UserEntity>(userService.createUser(user),HttpStatus.CREATED);
    }

    private void authenticate(String email,String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));


        }
        catch(DisabledException ex) {
            throw new Exception("User Disabled");

        }
        catch(BadCredentialsException ex)
        {
            throw new Exception("Bad Credentials");
        }




    }
}
