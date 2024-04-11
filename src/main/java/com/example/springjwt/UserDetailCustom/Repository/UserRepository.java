package com.example.springjwt.UserDetailCustom.Repository;

import com.example.springjwt.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long > {

    Optional<User> findUserByUsernameOrEmail(String username , String email);
}
