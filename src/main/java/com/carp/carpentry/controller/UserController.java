package com.carp.carpentry.controller;

import Dto.UserRequestDto;
import Dto.UserResponseDto;
import com.carp.carpentry.entity.User;
import com.carp.carpentry.repository.UserRepository;
import com.carp.carpentry.services.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserDetailService userDetailService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailService userDetailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto userRequestDto) {
        // Validate the request data (optional)
        if (userRequestDto == null || StringUtils.isEmpty(userRequestDto.getUsername()) || StringUtils.isEmpty(userRequestDto.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid user data");
        }

        // Check if the user already exists (optional)
        if (userRepository.findByUsername(userRequestDto.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        // Create the user entity and save it to the database
//        User user = new User();
//        user.setUsername(userRequestDto.getUsername());
//        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
//        // Set other user details as needed
//
//        userRepository.save(user);

        userDetailService.CreateUser(
                userRequestDto.getUsername(),
                userRequestDto.getPassword(),
                userRequestDto.getAddress(),
                userRequestDto.getDepartment()
        );



        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping("/getUsers")
    public List<UserResponseDto> getUsers(){
        return userDetailService.getUsers();
    }
}
