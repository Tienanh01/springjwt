package com.example.springjwt.UserDetailCustom.Repository;

import com.example.springjwt.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);
}
