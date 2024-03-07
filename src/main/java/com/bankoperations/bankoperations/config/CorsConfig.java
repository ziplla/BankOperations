package com.bankoperations.bankoperations.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
@Configuration
public class CorsConfig extends WebMvcConfigurationSupport
        implements Filter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "PUT", "POST", "DELETE")
                .allowedHeaders("Content-Type", "Authorization");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Request-Method", "Vary");
        httpResponse.setHeader("Access-Control-Request-Headers", "Vary");
        chain.doFilter(request, response);
    }
}
