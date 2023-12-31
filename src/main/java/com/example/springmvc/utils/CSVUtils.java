package com.example.springmvc.utils;

import com.example.springmvc.models.Users;
import com.example.springmvc.reposetories.UsersRepositories;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CSVUtils {
    private UsersRepositories usersRepositories;

    @Autowired
    public CSVUtils(UsersRepositories usersRepositories) {
        this.usersRepositories = usersRepositories;
    }

    @PostConstruct
    public void readUserCSV(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/com/decagon/springmvcsq019/users.csv"))) {
            String line;
            boolean lineOne = false;
            while ((line=bufferedReader.readLine())!=null){
                String[]user = line.split(",");
                if (lineOne) {
                    Users user1 = Users.builder().fullName(user[1])
                            .imageUrl(user[3])
                            .password(BCrypt.withDefaults()
                                    .hashToString(12, user[2].trim().toCharArray()))
                            .username(user[0])
                            .build();
                    usersRepositories.save(user1);
                }
                lineOne = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
