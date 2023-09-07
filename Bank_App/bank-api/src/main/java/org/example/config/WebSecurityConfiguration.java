package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@EnableMethodSecurity
@Configuration
public class WebSecurityConfiguration {
    @Value("${spring.web.security.ignored:/error,/ui/**,/favicon.ico,/swagger-ui/**,/v3/api-docs,/v3/api-docs/**}")
    private String[] ignored = { "/error", "/ui/**", "/favicon.ico", "/swagger-ui/**", "/v3/api-docs",
            "/v3/api-docs/**" };
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            for (String ignore : ignored) {
                web.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher(ignore));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //http.formLogin(Customizer.withDefaults());
        http.formLogin(config -> config.successHandler((request, response, auth) -> {
        }));

        http.logout(config -> config.logoutSuccessHandler((request, response, auth) -> {
        }));

        CookieCsrfTokenRepository csrfRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrfRepository.setCookiePath("/");
        http.csrf(config -> config.csrfTokenRepository(csrfRepository)
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
        //http.csrf(config->config.disable());

        http.authorizeHttpRequests(config -> config
                .requestMatchers(AntPathRequestMatcher.antMatcher("/actuator/**")).hasAnyAuthority("ACTUATOR")
                .anyRequest().authenticated());

        return http.build();
    }

    /*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8080"); // Allow requests from your frontend
        configuration.addAllowedMethod(""); // Allow all HTTP methods
        configuration.addAllowedHeader(""); // Allow all headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

}