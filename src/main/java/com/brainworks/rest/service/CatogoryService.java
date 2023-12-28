package com.brainworks.rest.service;

import com.brainworks.rest.payloads.response.CatogoryDto;
import java.util.List;

public interface CatogoryService {

    CatogoryDto createCatogory(CatogoryDto catogoryDto);

    CatogoryDto getCatogoryById(Integer catogoryId);

    CatogoryDto updateCatogory(CatogoryDto catogoryDto,Integer catogoryId);

    void deleteCatogory(Integer catogoryId);

    List <CatogoryDto> getAllCatogory();

    CatogoryDto updateSpecificField(CatogoryDto catogoryDto, Integer catogoryId);

}
