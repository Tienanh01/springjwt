package com.example.springjwt.Controller;

import com.example.springjwt.Dto.RequestLogin;
import com.example.springjwt.Dto.ResponseLogin;
import com.example.springjwt.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping(value = "/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService ;

        @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login (@RequestBody RequestLogin loginDto){
        String token = authService.login(loginDto);
        ResponseLogin jwtResponse = new ResponseLogin();

        jwtResponse.setAccessToken(token);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }
    @GetMapping("/hello")
    public String hello(){

            return "Hello";
    }


}
