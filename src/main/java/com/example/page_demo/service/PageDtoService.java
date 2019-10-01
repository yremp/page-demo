package com.example.page_demo.service;

import com.example.page_demo.dto.PageDTO;
import org.springframework.stereotype.Service;

@Service
public class PageDtoService {

    public PageDTO index(Integer totalPage,Integer page) {
        PageDTO pageDto = new PageDTO();
        pageDto.setPagenation(totalPage, page);
        return pageDto;
    }
}
