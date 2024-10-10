package com.example.exodia.common.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.example.exodia.common.auth.JwtAuthFilter;
import com.example.exodia.common.service.CustomSessionExpiredStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomSessionExpiredStrategy customSessionExpiredStrategy;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, CustomSessionExpiredStrategy customSessionExpiredStrategy) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.customSessionExpiredStrategy = customSessionExpiredStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.csrf().disable()
    //             .authorizeRequests()
    //             .antMatchers("**").permitAll()
    //             .anyRequest().authenticated()
    //             .and()
    //             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    //
    //     http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    //
    //     http.sessionManagement()
    //             .sessionFixation().changeSessionId()
    //             .maximumSessions(1)
    //             .expiredSessionStrategy(customSessionExpiredStrategy)
    //             .maxSessionsPreventsLogin(false)
    //             .sessionRegistry(sessionRegistry());
    //
    //     return http.build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().changeSessionId()
                .maximumSessions(1)  // 한 사용자는 한 번에 하나의 세션만 유지 가능
                .expiredSessionStrategy(customSessionExpiredStrategy)
                .maxSessionsPreventsLogin(false)  // 중복 로그인 허용
                .sessionRegistry(sessionRegistry())
            );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}