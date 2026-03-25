package userProfile.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import userProfile.demo.service.CustomeUserDetailsService;



@Configuration
public class WebSecurityConfig {

    @Autowired
    CustomeUserDetailsService customeUserDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public AuthTokenFilter authTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
    .csrf(AbstractHttpConfigurer::disable)
    .cors(AbstractHttpConfigurer::disable)
    .exceptionHandling(e ->
        e.authenticationEntryPoint(unauthorizedHandler)
    )
    .sessionManagement(s ->
        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    )
    .authorizeHttpRequests(auth -> auth

        // Public authentication endpoints
        .requestMatchers("/api/v1/auth/signin").permitAll()
        .requestMatchers("/api/v1/auth/signup").permitAll()

        // Admin-only endpoints
        .requestMatchers(HttpMethod.GET, "/profiles/all").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/v1/auth/all").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/v1/auth/no-profile").hasRole("ADMIN")
        
    

        // User endpoints
        .requestMatchers(HttpMethod.POST, "/profiles/create").hasRole("USER")
        .requestMatchers(HttpMethod.GET, "/profiles/me").hasRole("USER")

        // Any other request must be authenticated
        .anyRequest().authenticated()
    );

        http.addFilterBefore(authTokenFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
         
    }   
    
}
