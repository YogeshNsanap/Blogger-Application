package com.brainworks.rest.restControllers;

import com.brainworks.rest.payloads.response.ApiResponseStatus;
import com.brainworks.rest.payloads.response.CatogoryDto;
import com.brainworks.rest.service.impl.CatogoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/catogories")
public class CatogoryController {
    @Autowired
    CatogoryServiceImpl catogoryService;

    //get
    @GetMapping("/")
    public ResponseEntity <List<CatogoryDto>> getAllCatogory() {
        List<CatogoryDto> catogoryDto = this.catogoryService.getAllCatogory ();
        return ResponseEntity.ok (catogoryDto);
    }

    //get
    @GetMapping("/{catogoryId}")
    public ResponseEntity<CatogoryDto> getCatogoryBYId(@PathVariable("catogoryId") Integer cId) {
        CatogoryDto catogoryDto = this.catogoryService.getCatogoryById (cId);
        return ResponseEntity.ok (catogoryDto);

    }


    //put
    @PutMapping("/{catogoryId}")
    public ResponseEntity<CatogoryDto> updateCatogory(@Valid @RequestBody CatogoryDto catogoryDto , @PathVariable("catogoryId") Integer cId){
        System.out.println (catogoryDto);
        CatogoryDto updatedCatogoryDto=this.catogoryService.updateCatogory (catogoryDto,cId);
        return ResponseEntity.ok (updatedCatogoryDto);

    }


    //post
    @PostMapping("/")
    public ResponseEntity<CatogoryDto> createCatogory(@Valid @RequestBody CatogoryDto catogoryDto){
        CatogoryDto createdCatogoryDto=this.catogoryService.createCatogory (catogoryDto);
        return ResponseEntity.ok (createdCatogoryDto);

    }

    //delete
    @DeleteMapping ("/{catogoryId}")
    public ResponseEntity<ApiResponseStatus> deleteCatogory(@PathVariable("catogoryId") Integer cId){
        this.catogoryService.deleteCatogory (cId);
        return new ResponseEntity<>(new ApiResponseStatus (("Account deleted Successfully"),true),HttpStatus.OK);
    }

    //patch
    @PatchMapping("/{catogoryId}")
    public ResponseEntity<CatogoryDto> updateSpecificField(@Valid @RequestBody CatogoryDto catogoryDto ,@PathVariable("catogoryId") Integer cId){
        CatogoryDto createdCatogoryDto=this.catogoryService.updateSpecificField (catogoryDto,cId);
        return ResponseEntity.ok (createdCatogoryDto);
    }
}