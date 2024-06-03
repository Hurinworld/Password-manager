package com.serhiihurin.passwordmanager.config;

import com.serhiihurin.passwordmanager.filter.JWTAuthenticationFilter;
import com.serhiihurin.passwordmanager.filter.UserLogInSuccessHandler;
import com.serhiihurin.passwordmanager.views.list.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends VaadinWebSecurity {
    private final UserLogInSuccessHandler userLogInSuccessHandler;
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Value("${application.security.jwt.secret-key}")
    private String authSecret;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

//    @Bean
//    public SecretKeySpec secretKeySpec() {
//        return new SecretKeySpec(Base64.getDecoder().decode(authSecret), JwsAlgorithms.HS256);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .successHandler(userLogInSuccessHandler)
                                .failureUrl("/login?error=true")
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/images/*.png")
                        ).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/line-awesome/**/*.svg")
                        ).permitAll()
//                        .anyRequest().authenticated()
                )
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider);
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
        setLoginView(http, LoginView.class);
        setStatelessAuthentication(
                http,
                new SecretKeySpec(Base64.getDecoder().decode(authSecret), JwsAlgorithms.HS256),
                "com.serhiihurin.passwordmanager",
                jwtExpiration
        );
    }
}
