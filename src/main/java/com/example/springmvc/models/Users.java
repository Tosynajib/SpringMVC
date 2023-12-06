package com.example.springmvc.models;

import com.example.springmvc.dtos.UsersDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
//    TO REMOVE THE RED LINE UNDER USERS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String imageUrl;
    private String password;
    private String fullName;

    public Users(UsersDTO usersDTO) {
        this.username = usersDTO.getUsername();
        this.password = BCrypt.withDefaults()
                .hasToString(12, usersDTO.getPassword().toCharArray());
        this.username = usersDTO.getFullName();
    }

}
