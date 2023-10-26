package com.ada.moneymorpher.profile;

import com.ada.moneymorpher.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repository;
    private final ModelMapper modelMapper;

    public List<ProfileDto> listAll() {
        return this.repository.findAll().stream()
                .map(this::convertDto).toList();
    }

    private ProfileDto convertDto(Profile profile) {
        return this.modelMapper.map(profile, ProfileDto.class);
    }

    private Profile convertFromDto(ProfileDto profileDto){
        return this.modelMapper.map(profileDto, Profile.class);
    }

    public ProfileDto getByUsername(String username) {
        return this.repository.findByUsername(username)
                .map(this::convertDto)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    public Profile getByUsernameEntity(String username){
        return this.repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    public ProfileDto register(ProfileDto profileDto) {
        Profile profile = this.convertFromDto(profileDto);
        final var save = this.repository.save(profile);
        return this.convertDto(save);
    }

    public void deactivate(String username) {
        final var profile = this.repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found!"));
        profile.setActive(false);
        this.repository.save(profile);
    }
}
