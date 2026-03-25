package userProfile.demo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import userProfile.demo.dto.ProfileRequestDto;
import userProfile.demo.dto.ProfileResponseDto;
import userProfile.demo.model.Profile;

import userProfile.demo.service.ProfileService;

@RequestMapping("/profiles")
@RestController
public class ProfileController {
   
    private final ProfileService profileService;
  

    public ProfileController(ProfileService profileService)
    {
        this.profileService = profileService;
        
       
    }


    @PostMapping("/create")
    public String create(@RequestBody ProfileRequestDto profileRequestDto, @AuthenticationPrincipal UserDetails userDetails)
    {
        profileService.create(profileRequestDto, userDetails);
        return "Profile Created successfully";

    }

    @GetMapping("/all")
    public List<Profile> getAll()
    {
        return profileService.getAll();
    }

     @GetMapping("/me")
    public ProfileResponseDto getMyProfile(@AuthenticationPrincipal UserDetails userDetails) 
    {
         System.out.println("Current user: " + (userDetails != null ? userDetails.getUsername() : "null"));
          System.out.println("UserDetails: " + userDetails);

       
        return profileService.getMyProfile(userDetails);
    }

}
