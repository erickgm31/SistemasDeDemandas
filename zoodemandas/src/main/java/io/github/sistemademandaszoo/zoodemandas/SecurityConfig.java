package io.github.sistemademandaszoo.zoodemandas;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            SecurityContextRepository securityContextRepository) throws Exception {

        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
                .csrfTokenRequestHandler(
                    new CsrfTokenRequestAttributeHandler()
                )
            )

            .securityContext(context -> context
                .securityContextRepository(securityContextRepository)
            )

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/",
                    "/index.html",
                    "/login.html",
                    "/cadastro.html",
                    "/estilo.css",
                    "/api.js",
                    "/favicon.ico",
                    "/error"
                ).permitAll()

                .requestMatchers(
                    "/usuario/cadastro",
                    "/usuario/login",
                    "/usuario/csrf"
                ).permitAll()

                .anyRequest().authenticated()
            )

            .formLogin(form -> form.disable())

            .httpBasic(basic -> basic.disable())

            .logout(logout -> logout
                .logoutUrl("/usuario/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                .logoutSuccessHandler((request, response, authentication) ->
                    response.setStatus(HttpStatus.NO_CONTENT.value())
                )
            )

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {

                    String accept = request.getHeader("Accept");

                    if (accept != null && accept.contains("text/html")) {
                        response.sendRedirect("/login.html");
                    } else {
                        response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Autenticação necessária."
                        );
                    }
                })
            );

        return http.build();
    }
}