package com.brainworks.rest.service.impl;

import com.brainworks.rest.entities.Catogory;
import com.brainworks.rest.exceptions.ResourceNotFoundException;
import com.brainworks.rest.payloads.response.CatogoryDto;
import com.brainworks.rest.repositories.CatogoryRepository;
import com.brainworks.rest.service.CatogoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CatogoryServiceImpl implements CatogoryService {
    @Autowired
    CatogoryRepository catogoryRepo;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public CatogoryDto createCatogory(CatogoryDto catogoryDto) {
        Catogory catogory=this.DtoToCatogory (catogoryDto);
        Catogory savedCatogory=this.catogoryRepo.save (catogory);
        return this.catogoryToDto (savedCatogory);
    }

    @Override
    public CatogoryDto getCatogoryById(Integer catogoryId) {
        Catogory catogory= this.catogoryRepo.findById (catogoryId)
                .orElseThrow (()->new ResourceNotFoundException ("Catogory","CatogoryId",catogoryId));
        return this.catogoryToDto (catogory);
    }

    @Override
    public CatogoryDto updateCatogory(CatogoryDto catogoryDto, Integer catogoryId) {
        Catogory catogory=this.catogoryRepo.findById (catogoryId)
                .orElseThrow (()->new ResourceNotFoundException ("Catogory","CatogoryId",catogoryId));
            catogory.setCatogoryTitle (catogoryDto.getCatogoryTitle());
            catogory.setCatogoryDiscription (catogoryDto.getCatogoryDiscription ());
            this.catogoryRepo.save (catogory);
        return this.catogoryToDto (catogory);
    }

    @Override
    public void deleteCatogory(Integer catogoryId) {
        this.catogoryRepo.findById(catogoryId)
                .orElseThrow (()->new ResourceNotFoundException ("Catogory","CatogoryId",catogoryId));
        this.catogoryRepo.deleteById(catogoryId);

    }

    @Override
    public List<CatogoryDto> getAllCatogory() {
        List<CatogoryDto> collect = this.catogoryRepo.findAll ().stream()
                .map ((this::catogoryToDto))
                .collect (Collectors.toList ());
        return collect;
    }

    @Override
    public CatogoryDto updateSpecificField(CatogoryDto catogoryDto, Integer catogoryId) {
        Catogory catogory=this.catogoryRepo.findById (catogoryId)
                .orElseThrow (()->new ResourceNotFoundException ("Catogory","CatogoryId",catogoryId));

        catogory.setCatogoryTitle (catogoryDto.getCatogoryTitle ());
        catogory.setCatogoryDiscription (catogoryDto.getCatogoryDiscription ());
        this.catogoryRepo.save (catogory);
        return this.catogoryToDto (catogory);
    }

    public CatogoryDto catogoryToDto (Catogory catogory) {
      return this.modelMapper.map (catogory,CatogoryDto.class);
    }

    public Catogory DtoToCatogory (CatogoryDto catogoryDto) {
        return this.modelMapper.map (catogoryDto,Catogory.class);
    }
}
