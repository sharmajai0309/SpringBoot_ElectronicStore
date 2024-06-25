package com.Jai.electronic.store.ElectronicStore.helper;

import com.Jai.electronic.store.ElectronicStore.dtos.PageableResponse;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        List<U> entityList = page.getContent();
        ModelMapper modelMapper = new ModelMapper();
        List<V> dtoList = entityList.stream().map(entity -> modelMapper.map(entity, type)).collect(Collectors.toList());

        PageableResponse<V> response = new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber()+1);
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;

    }
}
