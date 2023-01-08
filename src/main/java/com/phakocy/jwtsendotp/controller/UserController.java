package com.phakocy.jwtsendotp.controller;

import com.phakocy.jwtsendotp.entity.User;
import com.phakocy.jwtsendotp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserRepository userRepository;
//    @GetMapping
//    public ResponseEntity<User> fetchAllUser(){
//        List<User> users = userRepository.findAll();
//        return ResponseEntity.ok((User) users);
//    }

    @GetMapping
    public ResponseEntity<String> test(){
        System.out.println("I'm Heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeere");
        return ResponseEntity.ok("Security Perfect");
    }
}
