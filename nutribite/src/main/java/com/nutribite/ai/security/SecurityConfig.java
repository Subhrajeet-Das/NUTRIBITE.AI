package com.nutribite.ai.security;

import com.nutribite.ai.config.CorsConfig;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import java.util.List;

@Configuration
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter; private final CorsConfig corsConfig;
    public SecurityConfig(JwtAuthenticationFilter f,CorsConfig c){this.jwtAuthenticationFilter=f;this.corsConfig=c;}
    @Bean public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.cors(c->c.configurationSource(corsConfigurationSource())).csrf(c->c.disable())
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/api-docs/**","/actuator/health").permitAll().anyRequest().authenticated())
            .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean CorsConfigurationSource corsConfigurationSource(){CorsConfiguration c=new CorsConfiguration();c.setAllowedOrigins(corsConfig.origins());c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("Authorization","Content-Type","Accept"));c.setExposedHeaders(List.of("Authorization"));c.setAllowCredentials(true);UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
}
