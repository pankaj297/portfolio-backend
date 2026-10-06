package com.myportfolio.backend.servicesImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.myportfolio.backend.dto.MyServicesRequestDto;
import com.myportfolio.backend.dto.MyServicesResponseDto;

import com.myportfolio.backend.exception.ResourceNotFoundException;
import com.myportfolio.backend.model.MyProfile;
import com.myportfolio.backend.model.MyServices;

import com.myportfolio.backend.repository.MyProfileRepository;
import com.myportfolio.backend.repository.MyServicesRepository;
import com.myportfolio.backend.services.MyServicesServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyServicesServicesImpl implements MyServicesServices {

    private final MyServicesRepository myServicesRepository;
    private final ModelMapper modelMapper;
    private final MyProfileRepository myProfileRepository;

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
        // ! MyServices myServices = modelMapper.map(myServicesRequestDto,
        // MyServices.class);

        MyServices services = new MyServices();
        
        services.setTitle(myServicesRequestDto.getTitle());
        services.setDescription(myServicesRequestDto.getDescription());
        services.setDisplayOrder(myServicesRequestDto.getDisplayOrder());
        services.setIsActive(myServicesRequestDto.getIsActive());
        
        // & for Relationship code
        // ^ find profile
        MyProfile profile = myProfileRepository.findById(myServicesRequestDto.getProfileId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ("Profile Not found with id : " + myServicesRequestDto.getProfileId())));

        // ^ set profile
        services.setProfile(profile);
        // & end Relationship code

        MyServices saveMyServices = myServicesRepository.save(services);
        return modelMapper.map(saveMyServices, MyServicesResponseDto.class);
    }

    @Override
    public MyServicesResponseDto updateServices(Long id, MyServicesRequestDto myServicesRequestDto) {
        MyServices myServices = myServicesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Services id not found with id : " + id));

        // ! modelMapper.map(myServicesRequestDto, myServices);
    
        myServices.setTitle(myServicesRequestDto.getTitle());
        myServices.setDescription(myServicesRequestDto.getDescription());
        myServices.setDisplayOrder(myServicesRequestDto.getDisplayOrder());
        myServices.setIsActive(myServicesRequestDto.getIsActive());

        // ? relationship code services and profile
        // check is id is present or not and find profile
        if (myServicesRequestDto.getProfileId() != null) {
            MyProfile profile = myProfileRepository.findById(myServicesRequestDto.getProfileId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Profile not found with id : " + myServicesRequestDto.getProfileId()));
            // set profile
            myServices.setProfile(profile);
        }
        // ? relationship code services and profile

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


    // ^ relation ship code my services and profile
    private MyServicesResponseDto mapToResponse(MyServices services) {

        MyServicesResponseDto response = modelMapper.map(services, MyServicesResponseDto.class);

        if (services.getProfile() != null) {
            response.setProfileId(services.getProfile().getId());
            response.setProfileName(services.getProfile().getFullName());
        }
        return response;
    }

}
