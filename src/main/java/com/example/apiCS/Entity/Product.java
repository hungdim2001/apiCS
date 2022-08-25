package com.example.apiCS.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(columnDefinition = "TEXT", length = 2048)
    private String image;
    @NotNull
    private Float price;
    @NotNull
    @JsonProperty(value = "weapon_id")
    private Long weaponId;
//    @OneToOne(mappedBy = "product")
//    private CartItem cartItem;
}
