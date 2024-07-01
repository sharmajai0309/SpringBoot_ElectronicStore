package com.Jai.electronic.store.ElectronicStore.dtos;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductDto {
    private String productId;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;
    private CatergoryDto category;

}
