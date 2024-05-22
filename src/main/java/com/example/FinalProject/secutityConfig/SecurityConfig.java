//package com.example.FinalProject.config;
//
//import com.example.FinalProject.entity.Role;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAutenticationFilter jwtAuthFilter ;
//    private final AuthenticationProvider authenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/api/v1/auth/**").permitAll()
//                                .requestMatchers("/actuator/**").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
//                                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasRole(Role.ADMIN.name())
//                                .requestMatchers(HttpMethod.GET, "/api/v1/social-tasks/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
//                                .requestMatchers(HttpMethod.POST, "/api/v1/social-tasks/**").hasRole(Role.ADMIN.name())
//                                .requestMatchers(HttpMethod.GET, "/api/v1/organizations/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
//                                .requestMatchers(HttpMethod.POST, "/api/v1/organizations/**").hasRole(Role.ADMIN.name())
//                                .anyRequest().authenticated()
//                )
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}
package com.example.FinalProject.secutityConfig;

import com.example.FinalProject.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAutenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/api/v1/social-tasks/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/social-tasks/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/api/v1/organizations/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/organizations/**").hasAnyAuthority(Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
