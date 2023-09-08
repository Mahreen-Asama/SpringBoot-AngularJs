package com.redmath.studentapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity
@Configuration
public class WebSecurityConfiguration {

    //one way of ignoring login on some request
    /*@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/student?*", "GET"))
                .requestMatchers(new AntPathRequestMatcher("/actuator", "GET"))
                .requestMatchers(new AntPathRequestMatcher("/actuator/*", "GET"));
                 //allowing all GET methods
    }*/
    //another way of ignoring login on a request
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());
        /*http.authorizeHttpRequests(
                config -> config.requestMatchers(new AntPathRequestMatcher("/api/v1/student/*", "GET")).permitAll()
                        .anyRequest().authenticated()
        );*/
        http.csrf(config->config.disable());
        return http.build();
    }

}
