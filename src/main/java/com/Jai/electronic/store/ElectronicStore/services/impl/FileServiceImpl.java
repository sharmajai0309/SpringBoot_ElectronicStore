package com.Jai.electronic.store.ElectronicStore.services.impl;

import com.Jai.electronic.store.ElectronicStore.exception.BadAptRequest;
import com.Jai.electronic.store.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
       String originalFileName = file.getOriginalFilename();
       logger.info("FileName : {}" ,originalFileName);
       String filename = UUID.randomUUID().toString();
       String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
       String fileNameWithExtension = filename + extension;
       String fullPathWithFileName = path + File.separator + fileNameWithExtension;
      if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){
          // file save
          File folder = new File(path);
          if(!folder.exists()){
              //create Folder
              folder.mkdirs();
          }
          //upload
          Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
          return fileNameWithExtension;

      }
      else {
          throw new BadAptRequest("File With this"+extension+"not allowed !!");

      }

    }

    @Override
    public InputStream getResources(String path, String name) throws FileNotFoundException {
        String fullPath  = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
