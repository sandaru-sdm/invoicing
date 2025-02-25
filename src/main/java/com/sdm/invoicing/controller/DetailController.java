package com.sdm.invoicing.controller;

import com.sdm.invoicing.dto.DetailDto;
import com.sdm.invoicing.dto.DetailSaveRequest;
import com.sdm.invoicing.service.DetailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test/api/detail")
@Validated
public class DetailController {
    private final DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping
    public ResponseEntity<List<DetailDto>> getAllDetails(){
        List<DetailDto> detailDtos = detailService.getAllDetails();
        return ResponseEntity.ok(detailDtos);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveDetail(@Valid @RequestBody DetailSaveRequest detailSaveRequest){
        if(detailService.hasDetailWithName(detailSaveRequest.getName())) {
            return new ResponseEntity<>("Detail already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        DetailDto detailDto = detailService.saveDetail(detailSaveRequest);
        return new ResponseEntity<>(detailDto, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<DetailDto>> getAllDetailsByName(@PathVariable String name){
        List<DetailDto> detailDtos = detailService.searchDetailByName(name);
        return ResponseEntity.ok(detailDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDetail(@PathVariable Long id, @RequestBody DetailSaveRequest detailSaveRequest) {
        DetailDto detailDto = detailService.updateDetail(id, detailSaveRequest);
        return new ResponseEntity<>(detailDto, HttpStatus.OK);
    }
}
