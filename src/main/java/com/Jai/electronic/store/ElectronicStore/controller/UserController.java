package com.Jai.electronic.store.ElectronicStore.controller;

import com.Jai.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.Jai.electronic.store.ElectronicStore.dtos.ImageResponse;
import com.Jai.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.Jai.electronic.store.ElectronicStore.dtos.UserDto;
import com.Jai.electronic.store.ElectronicStore.services.FileService;
import com.Jai.electronic.store.ElectronicStore.services.UserService;
import com.Jai.electronic.store.ElectronicStore.services.impl.FileServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    private Logger logger = LoggerFactory.getLogger(UserController.class);



    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }




    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId
            ,@Valid @RequestBody UserDto userDto){

     UserDto updatedUserDto = userService.updateUser(userDto,userId);
     return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
    }





    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("user is deleted Successfully !!")
                .success(true).status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message,HttpStatus.OK);

    }



    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "ASC",required = false) String sortDir
    ){
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }




    //get single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        return new ResponseEntity<>(userService.getUser(userId),HttpStatus.OK);
    }




    //get by email
    @GetMapping("email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }


    //search user
    @GetMapping("search/{keyWords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyWords){
        return new ResponseEntity<>(userService.searchUser(keyWords),HttpStatus.OK);
    }

//    for uploading image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUSerImage(@RequestParam("userImage") MultipartFile image , @PathVariable String userId) throws IOException {
    String imageName = fileService.uploadImage(image,imageUploadPath);
    UserDto user = userService.getUser(userId);
    user.setImageName(imageName);
    UserDto userDto = userService.updateUser(user,userId);
    ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

//    image to show to client
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId , HttpServletResponse response) throws IOException {

        UserDto user = userService.getUser(userId);
        logger.info("user name : {}" , user.getImageName());
       InputStream resource =  fileService.getResources(imageUploadPath,user.getImageName());
       response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
