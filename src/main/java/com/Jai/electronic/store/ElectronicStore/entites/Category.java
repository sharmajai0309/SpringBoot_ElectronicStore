package com.Jai.electronic.store.ElectronicStore.entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.HashSet;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    private String categoryId;
    @Column(name ="category_Id",length = 60 , nullable = false)
     private String title;
    @Column(name = "category_desc",length = 500)
     private String description;
     private String coverImage;
     // update category from here
    @OneToMany(mappedBy ="category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private HashSet<Product> products = new HashSet<>();







}
