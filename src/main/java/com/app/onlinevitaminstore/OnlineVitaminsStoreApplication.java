package com.app.onlinevitaminstore;

import com.app.onlinevitaminstore.model.Role;
import com.app.onlinevitaminstore.model.User;
import com.app.onlinevitaminstore.repository.RoleRepository;
import com.app.onlinevitaminstore.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class OnlineVitaminsStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineVitaminsStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        return args -> {
            Role role = new Role();
            role.setId(2L);
            role.setName("ROLE_ADMIN");
            Role savedRole = roleRepository.save(role);

            Optional<User> optionalUser = userRepository.findUserByUsername("admin");
            if (!optionalUser.isPresent()) {
                User user = User.builder()
                        .firstName("Admin")
                        .lastName("Account")
                        .address("Islamabad, Pakistan")
                        .email("admin@gmail.com")
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(savedRole)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
