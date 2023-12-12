package com.kob.matchingSystem.config;

        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.http.HttpMethod;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(it -> it
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/matching/player/add/", "/matching/player/remove/").hasIpAddress("127.0.0.1")
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
