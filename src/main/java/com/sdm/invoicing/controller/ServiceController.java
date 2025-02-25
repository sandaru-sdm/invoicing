package com.sdm.invoicing.controller;

import com.sdm.invoicing.dto.ServiceDto;
import com.sdm.invoicing.dto.ServiceSaveRequest;
import com.sdm.invoicing.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test/api/service")
@Validated
public class ServiceController {
    private final ServiceService service;

    public ServiceController(ServiceService serviceService) {
        this.service = serviceService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceDto>> getAllServices(){
        List<ServiceDto> serviceDtos = service.getAllServices();
        return ResponseEntity.ok(serviceDtos);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveService(@Valid @RequestBody ServiceSaveRequest serviceSaveRequest){
        if(service.hasServiceWithName(serviceSaveRequest.getName())) {
            return new ResponseEntity<>("Service already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        ServiceDto serviceDto = service.saveService(serviceSaveRequest);
        return new ResponseEntity<>(serviceDto, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ServiceDto>> getAllServicesByName(@PathVariable String name){
        List<ServiceDto> serviceDtos = service.searchServiceByName(name);
        return ResponseEntity.ok(serviceDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable Long id, @RequestBody ServiceSaveRequest serviceSaveRequest) {
        ServiceDto serviceDto = service.updateService(id, serviceSaveRequest);
        return new ResponseEntity<>(serviceDto, HttpStatus.OK);
    }
}
