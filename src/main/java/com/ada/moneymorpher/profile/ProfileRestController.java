package com.ada.moneymorpher.profile;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-morpher")
@RequiredArgsConstructor
public class ProfileRestController {
    private final ProfileService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<ProfileResponse> listAll(){

        return this.service.listAll().stream()
                .map(this::convertResponse)
                .toList();
    }
    @GetMapping("/{username}")
    public ProfileResponse getByUserName(@PathVariable String username){
        ProfileDto profileDto = this.service.getByUsername(username);
        return convertResponse(profileDto);
    }

    private ProfileResponse convertResponse(ProfileDto profile) {
        return this.modelMapper.map(profile, ProfileResponse.class);
    }

    private ProfileDto convertRequest(ProfileRequest profile){
        return this.modelMapper.map(profile, ProfileDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@RequestBody @Valid ProfileRequest profile){
        ProfileDto profileDto = this.convertRequest(profile);
        ProfileDto savedProfile = this.service.register(profileDto);
        return this.convertResponse(savedProfile);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole(T(com.ada.moneymorpher.profile.Role).ADMIN.name())")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable String username){
        this.service.deactivate(username);
    }

}
