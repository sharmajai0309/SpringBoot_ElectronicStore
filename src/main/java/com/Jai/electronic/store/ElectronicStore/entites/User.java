package com.Jai.electronic.store.ElectronicStore.entites;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;


    @Column(name = "User_name")
    private String name;
    @Column(name = "User_email",unique = true)
    private String email;
    @Column(name = "User_password",length = 500)
    private String password;

    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name = "User_image_name")
    private String imageName;

}
