package com.Jai.electronic.store.ElectronicStore.dtos;

import com.Jai.electronic.store.ElectronicStore.validate.ImageNameValid;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDto {

    private String userId;

    @Size(min= 3,max = 25,message = "Name should be more tha 3 and less then 15 words!!!")
    private String name;

    //  @Email(message = "Invalid User Email !!")
    //custom pattern
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Password is Required")
    private String password;

    @Size(min = 3 ,max = 6 ,message = "Write Either Male or Female")
    private String gender;

    @NotBlank(message = "write something yourself")
    private String about;

    @ImageNameValid
    private String imageName;
}
