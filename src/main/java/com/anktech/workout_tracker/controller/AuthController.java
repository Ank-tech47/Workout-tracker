package com.anktech.workout_tracker.controller;

import com.anktech.workout_tracker.collection.LoginResponse;
import com.anktech.workout_tracker.collection.User;
import com.anktech.workout_tracker.collection.UserDetailsReposne;
import com.anktech.workout_tracker.collection.UserLogin;
import com.anktech.workout_tracker.repository.UserRepository;
import com.anktech.workout_tracker.sevice.MyUserDetailsService;

import com.anktech.workout_tracker.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MyUserDetailsService myUserDetailsService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, JwtUtil jwtUtil, MyUserDetailsService myUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Operation(summary = "User Signup", description = "Registers a new user into the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "409", description = "Error: Username already exists")
    })
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: Username already exists");
        }

        // Encode password and save user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(summary = "User Login", description = "Authenticates user and returns a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLogin userLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
        );
        
        UserDetailsReposne userDetailsReposne = myUserDetailsService.loadUserByUsername(userLogin.getUsername());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserToken(jwtUtil.generateToken(userDetailsReposne.getUsername()));
        loginResponse.setUserId(userDetailsReposne.getUserId());
        return ResponseEntity.ok(loginResponse);

    }
}

