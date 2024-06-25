package com.Jai.electronic.store.ElectronicStore.services;

import com.Jai.electronic.store.ElectronicStore.dtos.CatergoryDto;
import com.Jai.electronic.store.ElectronicStore.dtos.PageableResponse;

public interface CategoryService {
    //create
    CatergoryDto create (CatergoryDto catergoryDto);


    //update
    CatergoryDto update (CatergoryDto catergoryDto ,String categoryId);


    //delete
    void delete(String categoryId);

    //get all
    PageableResponse<CatergoryDto> getAll(int pageNumber,int pageSize,String sortSize,String sortDir);
//    get single
    CatergoryDto get(String CategoryId);

}

