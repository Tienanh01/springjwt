package com.example.springjwt.Controller;

import com.example.springjwt.Dto.UserRequest;
import com.example.springjwt.Model.Role;
import com.example.springjwt.Model.User;
import com.example.springjwt.UserDetailCustom.Repository.RoleRepository;
import com.example.springjwt.UserDetailCustom.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/get-list")
    public List<User> getListUser(){
        return userRepository.findAll();
    }
    @PostMapping("/create_user")
    public String create_User(@RequestBody UserRequest userRequest){

        Set<Role> roleSet = new HashSet<>();
        for(String e : userRequest.getListRoleName()){
            Role role = roleRepository.findByName(e);
           if(role != null) {
               roleSet.add(role);
           }

        }
        User user = User.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .roles(roleSet).build() ;
      try {
          userRepository.save(user) ;
                  return "Save User success";
      }catch (Exception e){
          return "False to save user";
      }


    }
}
