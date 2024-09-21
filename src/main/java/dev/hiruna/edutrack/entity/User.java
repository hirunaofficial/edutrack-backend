package dev.hiruna.edutrack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private Integer id;
    private String email;
    private String password;
    private String role;

    public User(String email, String password, String telephone) {
        this.email = email;
        this.password = password;
        this.role = telephone;
    }

    public String getUserType() {
        return role;
    }
}