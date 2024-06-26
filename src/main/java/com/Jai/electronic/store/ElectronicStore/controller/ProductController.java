package com.Jai.electronic.store.ElectronicStore.controller;

import com.Jai.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.Jai.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.Jai.electronic.store.ElectronicStore.dtos.ProductDto;
import com.Jai.electronic.store.ElectronicStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

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




}
