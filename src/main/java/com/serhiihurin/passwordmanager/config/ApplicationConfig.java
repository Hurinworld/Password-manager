package com.serhiihurin.passwordmanager.config;

import com.serhiihurin.passwordmanager.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.getByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
