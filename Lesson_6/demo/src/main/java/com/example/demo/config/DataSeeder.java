package com.example.demo.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.AppRole;
import com.example.demo.model.AppUser;
import com.example.demo.model.Person;
import com.example.demo.repository.AppRoleRepository;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.PersonRepository;

// Fyller databasen med startdata. Körs vid varje uppstart men lägger bara in data
// om tabellerna är tomma, eftersom ddl-auto=update behåller datan mellan omstarter.
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(AppRoleRepository roles,
                               AppUserRepository users,
                               PersonRepository persons,
                               PasswordEncoder passwordEncoder) {
        return args -> {

            if(roles.count() == 0){
                roles.save(role("ROLE_USER"));
                roles.save(role("ROLE_ADMIN"));
            }

            if (users.count() == 0) {
                AppRole userRole = roles.findByName("ROLE_USER");
                AppRole adminRole = roles.findByName("ROLE_ADMIN");
                users.save(user("user", passwordEncoder.encode("password"), Set.of(userRole)));
                users.save(user("admin", passwordEncoder.encode("admin"), Set.of(userRole, adminRole)));
            }

            if (persons.count() == 0) {
                persons.save(new Person(null, "Anna Andersson", "+46701234567"));
                persons.save(new Person(null, "Bertil Berg", "+46709876543"));
            }
        };
    }

    private static AppRole role(String name) {
        AppRole role = new AppRole();
        role.setName(name);
        return role;
    }

    private static AppUser user(String username, String hashedPassword, Set<AppRole> roles) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRoles(roles);
        return user;
    }
}
