package com.creditcore.creditcore.config;

import com.creditcore.creditcore.user.Role;
import com.creditcore.creditcore.user.User;
import com.creditcore.creditcore.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail("admin@creditcore.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setActive(true);

            userRepository.save(admin);

            System.out.println("✅ Admin user created: admin@creditcore.com / admin123");
        }
    }
}