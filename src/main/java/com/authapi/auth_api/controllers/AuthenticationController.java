package com.authapi.auth_api.controllers;

import com.authapi.auth_api.domain.user.AuthenticationDTO;
import com.authapi.auth_api.domain.user.RegisterDTO;
import com.authapi.auth_api.domain.user.User;
import com.authapi.auth_api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    //@Autowired
    //private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data) {
        
        /*System.out.println("Testando envio de dados de - User login data:");
        System.out.println("Login: " + data.login());
        System.out.println("Password: " + data.password());
        System.out.println(usernamePassword);*/
        

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        //var token = tokenService.generateToken((User) auth.getPrincipal());
        
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        /*System.out.println("User registration data:");
        System.out.println("Login: " + data.login());
        System.out.println("Registering senha: " + data.password());
        System.out.println("Role: " + data.role()); */

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
