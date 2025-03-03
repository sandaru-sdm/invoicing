package com.sdm.invoicing.service;

import com.sdm.invoicing.dto.DetailDto;
import com.sdm.invoicing.dto.DetailSaveRequest;
import com.sdm.invoicing.entity.Detail;
import com.sdm.invoicing.repository.DetailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailService {
    private final DetailRepository detailRepository;

    public DetailService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public List<DetailDto> getAllDetails() {
        List<Detail> details = detailRepository.findAll();
        return details.stream().map(Detail::getDto).collect(Collectors.toList());
    }

    public boolean hasDetailWithName(String name) {
        return detailRepository.findByName(name).isPresent();
    }

    public DetailDto saveDetail(DetailSaveRequest detailSaveRequest) {
        Detail detail = new Detail();
        detail.setName(detailSaveRequest.getName());
        Detail saveDetail = detailRepository.save(detail);

        DetailDto detailDto = new DetailDto();
        detailDto.setId(saveDetail.getId());
        detailDto.setName(saveDetail.getName());

        return detailDto;
    }

    public List<DetailDto> searchDetailByName(String name) {
        List<Detail> details = detailRepository.findAllByNameContaining(name);
        return details.stream().map(Detail::getDto).collect(Collectors.toList());
    }

    public DetailDto updateDetail(Long id, DetailSaveRequest detailSaveRequest) {
        Detail detail = detailRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No Detail found with the ID :: " + id));
        detail.setName(detailSaveRequest.getName());
        detail = detailRepository.save(detail);
        return detail.getDto();
    }

    public DetailDto getDetail(Long id) {
        Detail detail = detailRepository.findById(id).isPresent() ? detailRepository.findById(id).get() : null;
        assert detail != null;
        return detail.getDto();
    }
}
