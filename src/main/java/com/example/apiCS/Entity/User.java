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
    private String username;
    @NotBlank
    private String password;
    @Email
    @NotBlank
    private String email;
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "userId", referencedColumnName = "id")
    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems;


}
