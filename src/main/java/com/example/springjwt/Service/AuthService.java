package com.example.springjwt.Service;

import com.example.springjwt.Dto.RequestLogin;

public interface AuthService {
    String login(RequestLogin loginDto );
}
