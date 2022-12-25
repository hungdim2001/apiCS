package com.example.apiCS.Entity;

import lombok.*;

import javax.persistence.*;

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
    private String password;
    private String avatarUrl;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private boolean isActive;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "role_id")
    private  Role role;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "auth_provider")
    private String provider;
//    private String providerId;
}
