package com.example.ClinicaMedicala.entity;

import com.example.ClinicaMedicala.dto.UserDTO;
import com.example.ClinicaMedicala.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole user_role = UserRole.patient;

    @Column(nullable = false)
    private Date created_at;

    private Date updated_at;

    private User(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.user_role = UserRole.valueOf(userDTO.getUser_role());
        this.created_at = userDTO.getCreated_at();
        this.updated_at = userDTO.getUpdated_at();
    }
}
