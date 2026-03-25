package userProfile.demo.service;

import java.util.Collections;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import userProfile.demo.Repository.UserRepository;
import userProfile.demo.model.Users;





@Service
public class CustomeUserDetailsService implements UserDetailsService 
{
    private  UserRepository userRepository;   
    
 
    public CustomeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
     {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }


         List<GrantedAuthority> authorities = Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_" + user.getRole())
    );
        
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPassword(),
            authorities
        );
    }
}
