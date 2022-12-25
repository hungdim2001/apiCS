package com.example.apiCS.Entity;


import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

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
    private String name;
    private Float price;
    private Float priceSale;
    @Column(columnDefinition = "TEXT", length = 2048)
    private String description;
    private String thumbnail;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    private List<ImageProducts> imageProductsList;
}
