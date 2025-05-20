package unam.fes.aragon.tienda_el_zorro.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import unam.fes.aragon.tienda_el_zorro.security.service.CustomUserDetailService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilita CSRF para APIs REST para simplificar las pruebas con Postman
                .csrf(csrf -> csrf.disable())

                // Configura CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Configura las reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/client-service/create",
                                "/client-service/delete**",
                                "/client-service/delete-id/*",
                                "/client-service/update/*",
                                "/product-service/create",
                                "/product-service/delete**",
                                "/product-service/products/*/image",
                                "/product-service/update/*",
                                "/supplier-service/create",
                                "/supplier-service/delete/*",
                                "/supplier-service/update/*",
                                "/user-service/**"
                        ).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(
                                "/auth/login",
                                "/auth/logout",
                                "/client-service/clients",
                                "/client-service/find-by-name/*",
                                "/audit-service/**",
                                "/export-service/send-invoice/*",
                                "/export-service/required-products",
                                "/invoice-service/create",
                                "/invoice-service/client-invoices**",
                                "/invoice-service/user-invoices**",
                                "/product-service/products",
                                "/product-service/find-by-name/*",
                                "/supplier-service/suppliers",
                                "/supplier-service/find-by-name/*",
                                "/supplier-service/find-by-id/*",
                                "/user-service/users"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // Configura el manejo de sesiones
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .maximumSessions(1)
                        .expiredUrl("/api/auth/login")
                )

                // Configura el servicio de usuario personalizado
                .userDetailsService(customUserDetailService)

                // Configura la redirección en caso de fallos de autenticación para APIs REST
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.getWriter().write("No autorizado");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(403);
                            response.getWriter().write("Acceso denegado");
                        })
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "https://your-production-domain.com"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // Importante para enviar cookies
        configuration.setExposedHeaders(List.of("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}