package com.myportfolio.backend.servicesImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.MyServicesRequestDto;
import com.myportfolio.backend.dto.MyServicesResponseDto;
import com.myportfolio.backend.exception.ResourceNotFoundException;

import com.myportfolio.backend.model.MyServices;
import com.myportfolio.backend.repository.MyServicesRepository;
import com.myportfolio.backend.services.MyServicesServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyServicesServicesImpl implements MyServicesServices {

    private final MyServicesRepository myServicesRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MyServicesResponseDto> getAllServices() {
        List<MyServices> myServices = myServicesRepository.findAll();
        return myServices.stream().map(se -> modelMapper.map(se, MyServicesResponseDto.class)).toList();
    }

    @Override
    public MyServicesResponseDto getServicesById(Long id) {
        MyServices myServices = myServicesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Services Id not found"));
        return modelMapper.map(myServices, MyServicesResponseDto.class);
    }

    @Override
    public MyServicesResponseDto createServices(MyServicesRequestDto myServicesRequestDto) {
        MyServices myServices = modelMapper.map(myServicesRequestDto, MyServices.class);
        MyServices saveMyServices = myServicesRepository.save(myServices);
        return modelMapper.map(saveMyServices, MyServicesResponseDto.class);
    }

    @Override
    public MyServicesResponseDto updateServices(Long id, MyServicesRequestDto myServicesRequestDto) {
        MyServices myServices = myServicesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Services id not found with id : " + id));
        modelMapper.map(myServicesRequestDto, myServices);
        MyServices saveMyServices = myServicesRepository.save(myServices);
        return modelMapper.map(saveMyServices, MyServicesResponseDto.class);
    }

    @Override
    public void deleteServicesById(Long id) {
        if (!myServicesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Services id not found with id : " + id);
        }
        myServicesRepository.deleteById(id);
    }

}
