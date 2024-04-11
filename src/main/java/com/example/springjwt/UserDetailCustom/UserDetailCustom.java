package com.example.springjwt.UserDetailCustom;

import com.example.springjwt.Model.User;
import com.example.springjwt.UserDetailCustom.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailCustom implements UserDetailsService {
        @Autowired
        private UserRepository userRepository ;

        // load UserBy user name
    /*   lấy thông tin từ database gắn vào dối tượng User của spring security
    * trả về đối tượng này
    *
    *  */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not exists by Username or Email")
        );


        Collection<GrantedAuthority> authorities =  user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(), authorities
        );
    }
}
