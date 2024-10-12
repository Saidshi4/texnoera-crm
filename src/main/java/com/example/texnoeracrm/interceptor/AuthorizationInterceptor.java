package com.example.texnoeracrm.interceptor;

import com.example.texnoeracrm.enums.ExceptionEnum;
import com.example.texnoeracrm.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.List;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/v1/auth/public",
            "/v3/api-docs",
            "/swagger-ui/",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/webjars/**"
    );
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestURI = request.getRequestURI();

        if (EXCLUDED_PATHS.stream().anyMatch(requestURI::startsWith)) {
            return true;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException(
                    ExceptionEnum.INVALID_TOKEN.name(),
                    ExceptionEnum.INVALID_TOKEN.getLog()
            );
        }
        String token = authorizationHeader.substring(7);
        request.setAttribute("token", token);
        return true;
    }
}

