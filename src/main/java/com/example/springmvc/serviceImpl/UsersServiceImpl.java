package com.example.springmvc.serviceImpl;

import com.example.springmvc.dtos.PasswordDTO;
import com.example.springmvc.models.Users;
import com.example.springmvc.reposetories.UsersRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
// Beans are object but in different form
@Service
public class UsersServiceImpl {

    private UsersRepositories usersRepositories;
//    HOVER ON THE RED LINE TO GENERATE CONSTRUCTOR METHOD
    @Autowired
    public UsersServiceImpl(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }


    public Function<String, Users> findUserByUsername = (username)->
        usersRepositories.findByUsername(username)
                .orElseThrow(()->new NullPointerException("User not found"));

    public Function<Users, Users> saveUser = (user) -> usersRepositories.save(user);

    public Function<PasswordDTO, Boolean> verifyUserPassword = passwordDTO -> BCrypt.verifyer()
            .verify(passwordDTO.getPassword().toCharArray(),
                    passwordDTO.getHashPassword().toCharArray())
            .verified;

}
