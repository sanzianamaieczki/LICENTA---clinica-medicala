package com.example.ClinicaMedicala.dto;

import com.example.ClinicaMedicala.entity.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Id
    private int id_user;
    private String email;
    private String password;
    private String user_role;
    private Date created_at;
    private Date updated_at;

    public UserDTO(User user) {
        this.id_user= user.getId_user();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.user_role = user.getUser_role().name();
        this.created_at = user.getCreated_at();
        this.updated_at = user.getUpdated_at();
    }

}
