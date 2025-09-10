package com.supermarket.food_test.config;

import com.supermarket.food_test.security.JWTAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/register").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/order").hasRole("USER"))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.PUT,"/auth").authenticated())
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.DELETE,"/auth").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/users/**").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/users").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,"/food").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/images/**").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST,"/food/**").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.PUT,"/food/**").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.DELETE,"/food/**").hasRole("ADMIN"))
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,"/food/**").permitAll())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(authProvider);
    }
}
