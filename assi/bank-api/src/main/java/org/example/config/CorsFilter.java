package org.example.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*") // Specify the URL pattern for which this filter should be applied
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Cast ServletResponse to HttpServletResponse
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Add CORS headers to the response
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(request, httpResponse); // Continue the filter chain
    }

    // Implement other methods (init, destroy) as needed
}