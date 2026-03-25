package userProfile.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import userProfile.demo.Repository.ProfileRepository;
import userProfile.demo.Repository.UserRepository;
import userProfile.demo.dto.ProfileRequestDto;
import userProfile.demo.dto.ProfileResponseDto;
import userProfile.demo.model.Profile;
import userProfile.demo.model.Users;


@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository,UserRepository userRepository)
    {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public ProfileResponseDto create(ProfileRequestDto profileRequestDto, UserDetails userDetails)
    {
        Profile profile = new Profile();

        Users user = userRepository.findByUsername(userDetails.getUsername());

        profile.setFullName(profileRequestDto.getFullName());
        profile.setEmail(profileRequestDto.getEmail());
        profile.setPhoneNumber(profileRequestDto.getPhoneNumber());
        profile.setAddress(profileRequestDto.getAddress());
        profile.setUsers(user);

        return mapToResponseDto(profileRepository.save(profile));

    }

    public List<Profile> getAll()
    {
        return profileRepository.findAll();
    }



    public ProfileResponseDto getMyProfile(UserDetails userDetails)
    {
        Users users = userRepository.findByUsername(userDetails.getUsername());
        Profile profile = profileRepository.findByUsers(users);
        return mapToResponseDto(profile);
    }

    public ProfileResponseDto mapToResponseDto(Profile profile)
    {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();

        profileResponseDto.setId(profile.getId());
        profileResponseDto.setFullName(profile.getFullName());
        profileResponseDto.setPhoneNumber(profile.getPhoneNumber());
        profileResponseDto.setEmail(profile.getEmail());
        profileResponseDto.setAddress(profile.getAddress());
        
        return profileResponseDto;
    }


}
