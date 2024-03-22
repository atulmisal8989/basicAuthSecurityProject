package com.api.basicAuth.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Value("${user.admin.username}")
    private String adminUsername;

    @Value("${user.admin.password}")
    private String adminPassword;

    @Value("${user.admin.roles}")
    private String adminRoles;

    @Bean
    //This method configures the security filters for HTTP requests.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception 
    {

    	//sets the session creation policy to STATELESS,
    	//which means no session will be created or used by Spring Security
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).cors(cors -> {
            cors.configurationSource(new CorsConfigurationSource() 
            {
                @Override
                //configures CORS (Cross-Origin Resource Sharing) to allow requests from all origins (*),
                //sets allowed methods and headers, and exposes the Authorization header.
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration cfg = new CorsConfiguration();
                    cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));
                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(List.of("Authorization"));

                    return cfg;
                }
            });
        }).authorizeHttpRequests((authz) -> authz
                .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/getAllUsers", "/users/getUser/{username}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/users/addUser").hasRole("ADMIN")
                .anyRequest().authenticated()
        ).csrf(c -> c.disable())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() 
    {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        UserDetails admin = User.withUsername(adminUsername)
                .password(passwordEncoder().encode(adminPassword))
                .authorities("ADMIN")
                .roles("ADMIN")
                .build();
        userDetailsService.createUser(admin);
        return userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder(10);
    }

}
