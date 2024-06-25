package com.Jai.electronic.store.ElectronicStore.services.impl;

import com.Jai.electronic.store.ElectronicStore.dtos.PageableResponse;
import com.Jai.electronic.store.ElectronicStore.dtos.ProductDto;
import com.Jai.electronic.store.ElectronicStore.entites.Product;
import com.Jai.electronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.Jai.electronic.store.ElectronicStore.helper.Helper;
import com.Jai.electronic.store.ElectronicStore.repositories.ProductRepository;
import com.Jai.electronic.store.ElectronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper Mapper;
    @Override
    public ProductDto create(ProductDto productDto) {
      Product product =   Mapper.map(productDto,Product.class);
      Product saveproduct = productRepository.save(product);
       return Mapper.map(saveproduct,ProductDto.class );
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        // Fetch the existing product by ID or throw a ResourceNotFoundException if not found
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        // Update the product's fields with the values from the provided ProductDto
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        // Save the updated product back to the repository
        Product updatedProduct = productRepository.save(product);

        // Map the updated product entity back to a ProductDto and return it
        return Mapper.map(updatedProduct, ProductDto.class);
    }


    @Override
    public void delete(String productId) {
        // Attempt to find the product by its ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        // If found, delete the product from the repository
        productRepository.delete(product);
    }


    @Override
    public ProductDto get(String productId) {
        // Fetch the product by ID or throw a ResourceNotFoundException if not found
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        // Map the product entity to a ProductDto
        return Mapper.map(product, ProductDto.class);
    }


    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }
}
