package com.Jai.electronic.store.ElectronicStore.controller;

import com.Jai.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import com.Jai.electronic.store.ElectronicStore.dtos.CatergoryDto;
import com.Jai.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.Jai.electronic.store.ElectronicStore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //create
    @PostMapping
    public ResponseEntity<CatergoryDto> createCategory(@RequestBody CatergoryDto catergoryDto){
       CatergoryDto catergoryDto1 =  categoryService.create(catergoryDto);
        return new ResponseEntity<>(catergoryDto1, HttpStatus.CREATED);
    }

    //update
@PutMapping("/{categoryId}")
    public ResponseEntity<CatergoryDto> updateCategory(
            @PathVariable String categoryId,
            @RequestBody CatergoryDto catergoryDto){
        CatergoryDto updatedcatergory = categoryService.update(catergoryDto,categoryId);

    return new ResponseEntity<>(updatedcatergory,HttpStatus.OK);
}


    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        ApiResponseMessage apiResponseMessage =  ApiResponseMessage.builder().message("Category is deleted Sucessfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity(apiResponseMessage, HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CatergoryDto>> getall(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortSize,
            @RequestParam(value = "sortDir",defaultValue = "ASC",required = false) String sortDir
    ){
       PageableResponse<CatergoryDto> pageableResponse =  categoryService.getAll(pageNumber,pageSize,sortSize,sortDir);
       return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    //get single
    @GetMapping("/{categoryId}")
    public ResponseEntity<CatergoryDto> getSingle(@PathVariable String categoryId) {
        CatergoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);
    }


}
