package com.tech_bit.tech_bit.configuration;
import com.tech_bit.tech_bit.entity.User;
import com.tech_bit.tech_bit.enums.Role;
import com.tech_bit.tech_bit.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
           if (userRepository.findByUsername("admin").isEmpty()){
               Set<String> roles = new HashSet<>();
               roles.add(Role.ADMIN.name());
               User user = User.builder()
                       .password(passwordEncoder().encode("admin"))
                       .username("admin")
                       .email("admin@gmail.com")
                       .roles(roles)
                       .build();
               userRepository.save(user);
           }
        };
    }
    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder(10);
    }
}
