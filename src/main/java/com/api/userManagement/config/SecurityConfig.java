package com.api.userManagement.config;

import com.api.userManagement.security.JwtAuthenticationEntryPoint;
import com.api.userManagement.security.JwtAuthenticationFilter;
import com.api.userManagement.security.JwtTokenProvider;
import com.api.userManagement.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtAuthenticationEntryPoint unauthorizedHandler,
                          UserDetailsServiceImpl userDetailsService,
                          JwtTokenProvider tokenProvider,
                          PasswordEncoder passwordEncoder) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable()) // Désactive la gestion des CORS (ajuster si nécessaire)
                .csrf(csrf -> csrf.disable()) // Désactive CSRF car on utilise JWT
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler)) // Gestion des erreurs d'authentification
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT étant stateless, on ne stocke pas de session
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**", // Autorise Swagger API Docs
                                "/swagger-ui/**", // Autorise Swagger UI
                                "/swagger-ui.html",
                                "/swagger-resources/**", // Autorise Swagger Resources
                                "/webjars/**" // Autorise les fichiers statiques Swagger
                        ).permitAll()
                        .requestMatchers("/api/users/**").authenticated() // Accès protégé aux utilisateurs
                        .anyRequest().denyAll() // Bloque toute autre requête non explicitement autorisée
                );

        // Ajoute le filtre JWT pour valider les tokens avec chaque requête
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder); // Utilise l'encodeur de mot de passe injecté
    }

    //swagger configuration
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .version("1.0")
                        .description("API pour la gestion des utilisateurs avec authentification JWT"))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)  // <-- Ajout pour s'assurer que le token est bien envoyé dans le header
                                .name("Authorization")  // <-- Ajout du nom exact du header
                                .in(SecurityScheme.In.HEADER)  // Assure l'envoi du token dans le header
                        ));

    }

}
