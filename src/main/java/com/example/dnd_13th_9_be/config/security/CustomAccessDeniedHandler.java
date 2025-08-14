package com.example.dnd_13th_9_be.config.security;


import com.example.dnd_13th_9_be.global.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.dnd_13th_9_be.global.error.ErrorCode.USER_ACCESS_DENIED;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String xhrHeader = request.getHeader("X-Requested-With");
        String accept = request.getHeader("Accept");

        boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(xhrHeader)
                || (accept != null && accept.contains("application/json"));

        if (isAjax) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");

            String apiResponse = objectMapper.writeValueAsString(ApiResponse.error(USER_ACCESS_DENIED));
            response.getWriter().write(apiResponse);
        }
    }
}
