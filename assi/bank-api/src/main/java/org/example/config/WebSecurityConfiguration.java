package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration {

    @Value("${spring.web.security.ignored:/error,/ui/**,/favicon.ico,/swagger-ui/**,/v3/api-docs,/v3/api-docs/**}")
    private String[] ignored = { "/error", "/ui/**", "/favicon.ico", "/swagger-ui/**", "/v3/api-docs",
            "/v3/api-docs/**" };

    @Value("${spring.web.security.ignored.get:/api}")
    private String[] ignoredGet = { "/accounts" };

    @Bean

    @Order(1)
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            for (String ignore : ignored) {
                web.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher(ignore));
            }
            for (String ignore : ignoredGet) {
                web.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, ignore));
            }
        };
    }


    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(config -> config.successHandler((request, response, auth) -> {
        }));

        http.logout(config -> config.logoutSuccessHandler((request, response, auth) -> {
        }));

        CookieCsrfTokenRepository csrfRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrfRepository.setCookiePath("/");
        http.csrf(config -> config.csrfTokenRepository(csrfRepository)
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

        http.authorizeHttpRequests(config -> config
                .requestMatchers(AntPathRequestMatcher.antMatcher("/actuator/**")).hasAnyAuthority("ACTUATOR")
                .anyRequest().authenticated());

        return http.build();
    }
}
