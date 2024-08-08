package com.example.huko.mongoDB.controllers;

import com.example.huko.mongoDB.CustomizedResponse;
import com.example.huko.mongoDB.models.AuthenticationRequest;
import com.example.huko.mongoDB.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    //This tells Spring to automatically inject an instance of UserService into this controller.
    @Autowired
    private UserService userService;



    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        String token = userService.authenticateUser(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        if (token != null) {
            var response = new CustomizedResponse<String>("Authentication Successful", Collections.singletonList(token));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            var response = new CustomizedResponse<String>("Invalid email or password", null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

}
