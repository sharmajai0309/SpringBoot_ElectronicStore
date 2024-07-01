package com.Jai.electronic.store.ElectronicStore.entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


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

    @Column(name = "category_title", length = 60, nullable = false)
    private String title;

    @Column(name = "category_desc", length = 500)
    private String description;

    private String coverImage;
    // other attributes if you have...

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();







}
