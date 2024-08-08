package com.example.huko.mongoDB.controllers;

import com.example.huko.mongoDB.CustomizedResponse;
import com.example.huko.mongoDB.models.UserModel;
import com.example.huko.mongoDB.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    //@RequestBody UserModel user: Binds the request body to the user parameter.
    @PostMapping("/register")
    public ResponseEntity<CustomizedResponse<UserModel>> registerUser(@RequestBody UserModel user) {
        UserModel newUser = userService.addUser(user);
        var response = new CustomizedResponse<UserModel>("User registered successfully", Collections.singletonList(newUser));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<CustomizedResponse<UserModel>> getAllUsers() {
        var customizedResponse = new CustomizedResponse<UserModel>("A List of Users", userService.getUsers());
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomizedResponse<UserModel>> getUserById(@PathVariable("id") String id) {
        CustomizedResponse<UserModel> customizedResponse;
        try {
            customizedResponse = new CustomizedResponse<UserModel>("User with id " + id, Collections.singletonList(userService.getAUser(id).orElse(null)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse<UserModel>(e.getMessage(), null);
            return new ResponseEntity<>(customizedResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    //user is an Optional<UserModel> object.
    //token.substring(7): This removes the "Bearer " prefix from the token string. For example, if the token is "Bearer abcdefg", this line extracts "abcdefg".
    @GetMapping("/me")
    public ResponseEntity<CustomizedResponse<UserModel>> getUserDetails(@RequestHeader("Authorization") String token) {
        String email = userService.getEmailFromToken(token.substring(7)); // Remove 'Bearer ' prefix
        Optional<UserModel> user = userService.getUserByEmail(email);

        if (!user.isPresent()) {
            var response = new CustomizedResponse<UserModel>("User not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        var response = new CustomizedResponse<UserModel>("User details", Collections.singletonList(user.get()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
