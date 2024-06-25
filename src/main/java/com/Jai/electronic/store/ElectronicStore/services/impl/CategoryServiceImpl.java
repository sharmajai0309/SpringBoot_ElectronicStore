package com.Jai.electronic.store.ElectronicStore.services.impl;

import com.Jai.electronic.store.ElectronicStore.dtos.CatergoryDto;
import com.Jai.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.Jai.electronic.store.ElectronicStore.entites.Category;
import com.Jai.electronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.Jai.electronic.store.ElectronicStore.helper.Helper;
import com.Jai.electronic.store.ElectronicStore.repositories.CategoryRepository;
import com.Jai.electronic.store.ElectronicStore.services.CategoryService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public CatergoryDto create(CatergoryDto catergoryDto) {
        // creating category id
        String categoryId =UUID.randomUUID().toString();
        catergoryDto.setCategoryId(categoryId);
        Category category = mapper.map(catergoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return mapper.map(savedCategory, CatergoryDto.class);
    }

    @Override
    public CatergoryDto update(CatergoryDto catergoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setTitle(catergoryDto.getTitle());
        category.setDescription(catergoryDto.getDescription());
        category.setCoverImage(catergoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        return mapper.map(updatedCategory,CatergoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CatergoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Determine the sort direction
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        // Create pageable object with page number, page size, and sort criteria
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetch the page of Category entities
        Page<Category> page = categoryRepository.findAll(pageable);

        // Convert Page<Category> to PageableResponse<CatergoryDto> using the Helper method
        PageableResponse<CatergoryDto> pageableResponse = Helper.getPageableResponse(page, CatergoryDto.class);

        return pageableResponse;
    }


    @Override
    public CatergoryDto get(String categoryId) {
        // Find the category by its ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        // Convert Category entity to CategoryDto
        return mapper.map(category, CatergoryDto.class);
    }
}
