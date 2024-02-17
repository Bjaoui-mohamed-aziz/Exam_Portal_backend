package com.exam.controller;

import com.exam.Model.JwtRequest;
import com.exam.Model.JwtResponse;
import com.exam.Model.User;
import com.exam.config.JwtUtils;
import com.exam.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@CrossOrigin("*")
@RestController
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService ;
    @Autowired
    private JwtUtils jwtUtils ;

    //generate token

    @PostMapping("/generate-token")
    @CrossOrigin("*")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        try
        {

            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        }catch(UsernameNotFoundException e)
        {
            e.printStackTrace();
            throw new Exception("User not found ");
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token =  this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));

    }





    private void authenticate(String username, String password) throws Exception {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }catch(DisabledException e){

            throw new Exception("USER DISABLED" + e.getMessage());
        } catch (BadCredentialsException e){
            throw new Exception("Invaled Credentials" + e.getMessage());

            }


    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){
        return ((User) this.userDetailsService.loadUserByUsername(principal.getName()));
    }
}
