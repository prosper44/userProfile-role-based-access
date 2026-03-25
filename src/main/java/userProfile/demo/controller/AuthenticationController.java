package userProfile.demo.controller;



import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import userProfile.demo.Repository.UserRepository;

import userProfile.demo.model.Users;
import userProfile.demo.security.JwtUtil;



@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;    
  
    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        
    }

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody Users user) 
    {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(), 
                user.getPassword())
        );

         final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody Users user)
    {
        if(userRepository.existsByUsername(user.getUsername()))
        {
            return "Error: Username is already taken!";
        }

        

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @GetMapping("/all")
    public List<Users> getAll()
    {
        return userRepository.findAll();
    }

    
    @GetMapping("/no-profile")
    public List<Users> getUsersWithoutProfile()
    {
        return userRepository.findByProfileIsNull();
    }



   

    
}
