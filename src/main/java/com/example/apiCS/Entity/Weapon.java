package com.example.apiCS.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Weapons")
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageUrl;
    @OneToMany
    @JoinTable(	name = "weapon_prouduct",
            joinColumns = @JoinColumn(name = "weapon_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> listProduct;

}
