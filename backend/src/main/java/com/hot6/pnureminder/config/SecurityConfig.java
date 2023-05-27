package com.hot6.pnureminder.config;

import com.hot6.pnureminder.jwt.JwtAccessDeniedHandler;
import com.hot6.pnureminder.jwt.JwtAuthenticationEntryPoint;
import com.hot6.pnureminder.jwt.JwtSecurityConfig;
import com.hot6.pnureminder.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
            .addFilter(corsFilter)
            .csrf().disable()
            .headers()
            .frameOptions()
            .disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/**").permitAll()

            .and()
            .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}
