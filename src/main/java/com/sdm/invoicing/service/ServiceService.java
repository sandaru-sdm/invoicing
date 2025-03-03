package com.sdm.invoicing.service;

import com.sdm.invoicing.dto.ServiceDto;
import com.sdm.invoicing.dto.ServiceSaveRequest;
import com.sdm.invoicing.repository.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceDto> getAllServices() {
        List<com.sdm.invoicing.entity.Service> services = serviceRepository.findAll();
        return services.stream().map(com.sdm.invoicing.entity.Service::getDto).collect(Collectors.toList());
    }

    public boolean hasServiceWithName(String name) {
        return serviceRepository.findByName(name).isPresent();
    }

    public ServiceDto saveService(ServiceSaveRequest serviceSaveRequest) {
        com.sdm.invoicing.entity.Service service = new com.sdm.invoicing.entity.Service();
        service.setName(serviceSaveRequest.getName());
        com.sdm.invoicing.entity.Service saveService = serviceRepository.save(service);

        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(saveService.getId());
        serviceDto.setName(saveService.getName());

        return serviceDto;
    }

    public List<ServiceDto> searchServiceByName(String name) {
        List<com.sdm.invoicing.entity.Service> services = serviceRepository.findAllByNameContaining(name);
        return services.stream().map(com.sdm.invoicing.entity.Service::getDto).collect(Collectors.toList());
    }

    public ServiceDto updateService(Long id, ServiceSaveRequest serviceSaveRequest) {
        com.sdm.invoicing.entity.Service service = serviceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Service found with the ID :: " + id));
        service.setName(serviceSaveRequest.getName());
        service = serviceRepository.save(service);
        return service.getDto();
    }

    public ServiceDto getService(Long id) {
        com.sdm.invoicing.entity.Service service = serviceRepository.findById(id).isPresent() ? serviceRepository.findById(id).get() : null;
        assert service != null;
        return service.getDto();
    }
}
