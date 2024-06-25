package com.Jai.electronic.store.ElectronicStore.services.impl;

import com.Jai.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.Jai.electronic.store.ElectronicStore.dtos.UserDto;
import com.Jai.electronic.store.ElectronicStore.entites.User;
import com.Jai.electronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.Jai.electronic.store.ElectronicStore.helper.Helper;
import com.Jai.electronic.store.ElectronicStore.repositories.UserRepository;
import com.Jai.electronic.store.ElectronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("@{user.profile.image.path}")
    private String imagepath;
    @Override
    public UserDto createUser(UserDto userDto) {

//        generate unique id
        String userid = UUID.randomUUID().toString();
        userDto.setUserId(userid);


//        entity to dto
        User user =  dtoToEntity(userDto);
       User savedUser =  userRepository.save(user);

//      dto to entity
        UserDto newDto = entitytodto(savedUser);
        return newDto;

    }



    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found With given ID"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        user.setGender(userDto.getGender());

//       coverting entity to dto
        User updateduser = userRepository.save(user);
        UserDto updatedDto = entitytodto(updateduser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found With given ID"));
        //delete user profile image
        String fullPath = imagepath + user.getImageName();

        try {
           Path path = Paths.get(fullPath);
           Files.delete(path);
            logger.info("user image Deleted");


        }
        catch (NoSuchFileException ex) {
            logger.info("user image not found in folder");
            ex.printStackTrace();

        }
        catch (IOException e) {
            e.printStackTrace();
        }


        // Delete the user
        userRepository.delete(user);



    }


    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize ,sort); // First page with 10 items per page

        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);



       return response;
    }

    @Override
    public UserDto getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found With given ID"));
        return entitytodto(user);

    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email not Found "));
        return entitytodto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
       List<User> users  = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList =  users.stream().map(user -> entitytodto((user))).collect(Collectors.toList());


        return dtoList;
    }




    private UserDto entitytodto(User savedUser) {

        return mapper.map(savedUser,UserDto.class);


    }
    private User dtoToEntity(UserDto userDto) {

        return mapper.map(userDto,User.class);
    }

}
