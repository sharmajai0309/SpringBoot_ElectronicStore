package com.Jai.electronic.store.ElectronicStore.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatergoryDto {


    private String categoryId;
    @NotBlank
    @Min(value = 4 ,message = "title must be of minimum  character !")
    private String title;
    @NotBlank(message = "Description required ")
    private String description;
    @NotBlank
    private String coverImage;
    // update category from here



}
