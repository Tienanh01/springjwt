package com.example.springjwt.Dto;

import com.example.springjwt.Model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
@Builder

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private List<String> listRoleName;
}
