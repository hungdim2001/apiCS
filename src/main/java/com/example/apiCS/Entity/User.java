package com.example.apiCS.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 6, max = 32)
    private String userName;
    @NotBlank
    private String password;
    private String avatarUrl;
    @Email
    @NotBlank
    private String email;
    @OneToOne
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private  Role role;
    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems;
}
