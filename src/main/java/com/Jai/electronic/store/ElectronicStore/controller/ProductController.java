package com.Jai.electronic.store.ElectronicStore.controller;

import com.Jai.electronic.store.ElectronicStore.dtos.*;
import com.Jai.electronic.store.ElectronicStore.services.FileService;
import com.Jai.electronic.store.ElectronicStore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;

//create----------------------------------------->

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.create(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }


    // update----------------------------------------->
    @PutMapping("{productId}")
    public ResponseEntity<ProductDto> UpdateProduct(@PathVariable String productId,@RequestBody ProductDto productDto,String Id) {

        ProductDto UpdateProduct = productService.update(productDto,productId);
        return new ResponseEntity<>(UpdateProduct, HttpStatus.OK);
    }



    //delete----------------------------------------->
    @DeleteMapping("{productId}")
    public ResponseEntity<ApiResponseMessage>deleteProduct(@PathVariable String productId){
        productService.delete(productId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("product is SuccessFully deleted from catalog").success(true).status(HttpStatus.OK).build();
       return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }


    // get single----------------------------------------->
    @GetMapping("{productId}")
    public ResponseEntity<ProductDto>GetProduct( @PathVariable String productId) {
        ProductDto productDto = productService.get(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }



    //get all----------------------------------------->
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortSize,
            @RequestParam(value = "sortDir",defaultValue = "ASC",required = false) String sortDir){
        PageableResponse<ProductDto> pageableResponse  = productService.getAll(pageNumber,pageSize,sortSize,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }




    //get all live----------------------------------------->
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortSize,
            @RequestParam(value = "sortDir",defaultValue = "ASC",required = false) String sortDir){
        PageableResponse<ProductDto> pageableResponse  = productService.getAllLive(pageNumber,pageSize,sortSize,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }


    //search all----------------------------------------->
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchUser(
            @PathVariable String query,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortSize,
            @RequestParam(value = "sortDir",defaultValue = "ASC",required = false) String sortDir){
        PageableResponse<ProductDto> pageableResponse  = productService.searchByTitle(query,pageNumber,pageSize,sortSize,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
//upload image

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage (
            @PathVariable String productId,
            @RequestParam("productImage")MultipartFile image
            ) throws ImagingOpException, IOException {

        String fileName = fileService.uploadImage(image,imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImage(fileName);
        ProductDto updatedProduct = productService.update(productDto,productId);

        ImageResponse Respose = ImageResponse.builder().imageName(updatedProduct.getProductId()).message("product image is successfully Uploaded").status(HttpStatus.CREATED).success(true).build();
         return new ResponseEntity<>(Respose,HttpStatus.CREATED);
    }

    //serve image
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId , HttpServletResponse response) throws IOException {

        ProductDto productDto = productService.get(productId);
        InputStream resource =  fileService.getResources(imagePath, productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }



}
