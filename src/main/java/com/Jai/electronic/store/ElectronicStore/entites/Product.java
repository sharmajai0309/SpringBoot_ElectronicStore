package com.Jai.electronic.store.ElectronicStore.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    private String productId ;
    private String title;
    @Column(length = 10000)
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="category_id")
    private Category category;

}
