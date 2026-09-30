package com.tecsup.util;

import com.tecsup.model.Role;
import com.tecsup.model.User;
import com.tecsup.repository.RoleRepository;
import com.tecsup.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UserRepository userRepo,
                               RoleRepository roleRepo,
                               PasswordEncoder encoder) {
        return args -> {
            // 🔹 1. Crear o recuperar Roles
            Role roleUser = roleRepo.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ROLE_USER");
                        return roleRepo.save(r);
                    });

            Role roleAdmin = roleRepo.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ROLE_ADMIN");
                        return roleRepo.save(r);
                    });

            Role roleManager = roleRepo.findByName("ROLE_MANAGER")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ROLE_MANAGER");
                        return roleRepo.save(r);
                    });

            // 🔹 2. Crear / Actualizar usuario USER
            User user = userRepo.findByUsername("user").orElse(new User());
            user.setUsername("user");
            user.setPassword(encoder.encode("user1234"));
            user.setRoles(Set.of(roleUser));
            userRepo.save(user);

            // 🔹 3. Crear / Actualizar usuario ADMIN
            User admin = userRepo.findByUsername("admin").orElse(new User());
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin1234"));
            admin.setRoles(Set.of(roleAdmin));
            userRepo.save(admin);

            // 🔹 4. Crear / Actualizar usuario MANAGER
            User manager = userRepo.findByUsername("manager").orElse(new User());
            manager.setUsername("manager");
            manager.setPassword(encoder.encode("manager1234"));
            manager.setRoles(Set.of(roleManager));
            userRepo.save(manager);

            System.out.println("✔ Datos iniciales cargados y actualizados correctamente");
        };
    }
}