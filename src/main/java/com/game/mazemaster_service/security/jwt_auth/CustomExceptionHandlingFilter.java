package com.nepal.collegehub.security.jwt_auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nepal.collegehub.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomExceptionHandlingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ResponseStatusException ex) {
            log.error("ResponseStatusException caught in filter: {}", ex.getMessage(), ex);
            setErrorResponse(response, HttpStatus.valueOf(ex.getStatusCode().value()), ex.getReason(), ex.getMessage());
        } catch (Exception ex) {
            log.error("Exception caught in filter: {}", ex.getMessage(), ex);
            setErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex.getMessage());
        }
    }

    private void setErrorResponse(HttpServletResponse response, HttpStatus status, String message, String details) throws IOException {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(false);
        apiResponse.setStatusCode(status.value());
        apiResponse.setMessage(message);
        apiResponse.setError(new ApiResponse.ErrorDetails("ERR-" + status.value(), details));

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}