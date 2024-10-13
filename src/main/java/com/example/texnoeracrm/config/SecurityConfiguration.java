package com.example.texnoeracrm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v1/auth/public/").permitAll()
                                .requestMatchers("api/v1/auth/admin/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "api/v1/attendances/users/{groupId}").hasAnyRole("ADMIN", "TEACHER","MENTOR")
                                .requestMatchers(HttpMethod.PATCH, "api/v1/attendances/users/{groupId}").hasAnyRole("ADMIN", "TEACHER","MENTOR")
                                .requestMatchers("api/v1/attendances/groups/{groupId}/by-date").hasAnyRole("ADMIN", "TEACHER","MENTOR")
                                .requestMatchers("api/v1/attendances/groups/{groupId}/filter").hasAnyRole("ADMIN", "TEACHER","MENTOR")
                                .requestMatchers("api/v1/attendances/groups/{groupId}").hasAnyRole("TEACHER","MENTOR", "STUDENT")
                                .requestMatchers("/api/v1/comments").hasAnyRole("TEACHER","MENTOR", "STUDENT")
                                .requestMatchers("/api/v1/groups/{groupId}/add-users").hasRole("ADMIN")
                                .requestMatchers("/api/v1/groups/{groupId}/delete-users").hasRole("ADMIN")
                                .requestMatchers("/api/v1/groups/{groupId}/schedule").hasRole("ADMIN")
                                .requestMatchers("/api/v1/groups/get-groups").hasAnyRole("TEACHER","MENTOR", "STUDENT")
                                .requestMatchers("/api/v1/notifications").hasAnyRole("TEACHER","MENTOR", "STUDENT")
                                .requestMatchers("/api/v1/tasks/groups/{groupId}").hasAnyRole("MENTOR", "TEACHER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/tasks/groups/{groupId}").hasAnyRole("MENTOR", "TEACHER")
                                .requestMatchers("/api/v1/tasks/groups/{groupId}/by-student").hasRole("STUDENT")
                                .requestMatchers("/api/v1/users/get-user").hasAnyRole("ADMIN", "TEACHER","MENTOR", "STUDENT")
                                .requestMatchers("/api/v1/users/groups/{groupId}").hasAnyRole("ADMIN", "TEACHER","MENTOR", "STUDENT")
                                .requestMatchers("/api/v1/users/change-password").hasAnyRole("ADMIN", "TEACHER","MENTOR", "STUDENT")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users").hasRole("ADMIN")
                                .requestMatchers("/api/v1/users/find-users").hasRole("ADMIN")
                                .requestMatchers("/api/v1/users-tasks/{taskId}").hasRole("STUDENT")
                                .requestMatchers("/api/v1/users-tasks/do/").hasRole("STUDENT")
                                .requestMatchers("/api/v1/users-tasks/update/").hasRole("STUDENT")
                                .requestMatchers("/api/v1/users-tasks/groups/{groupId}/tasks/{taskId}").hasAnyRole("TEACHER", "MENTOR")
                                .requestMatchers("/api/v1/users-tasks/{taskId}/grade").hasAnyRole("TEACHER", "MENTOR")
                                .requestMatchers("/api/v1/users-tasks/{userTaskId}").hasRole("STUDENT")


                                .requestMatchers(permitSwagger).permitAll()
                                .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public static String[] permitSwagger = {
            "/api/v1/auth/**",
            "v3/api-docs/**",
            "v3/api-docs.yanl",
            "swagger-ui/**",
            "swagger-ui.html"
    };
}