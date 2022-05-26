package org.farid.controller;


import org.farid.model.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.farid.model.domain.UserNewRequest;
import org.farid.service.UserService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("v1/users")
public class UserController {

    final UserService userService;
    final UserMapper userMapper;

    public UserController(UserService userService,UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(value = "")
    public ResponseEntity<?>  getAllUsers(){
        return new ResponseEntity<>(userService.userList(), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?>  newUser(@Valid @RequestBody UserNewRequest userNewRequest) throws Exception {
        return new ResponseEntity<>(userService.newUser(userNewRequest), HttpStatus.CREATED);
    }

}
