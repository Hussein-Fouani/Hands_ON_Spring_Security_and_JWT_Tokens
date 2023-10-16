package com.example.awt;

import com.example.awt.entities.Role;
import com.example.awt.entities.User;
import com.example.awt.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@Configuration
public class AwtApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repository;
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(AwtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = repository.findByRole(Role.ADMIN);
        if(user==null){
            user.setEmail("admin@gmail.com");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setRole(Role.ADMIN);
            repository.save(user);
        }

    }
}
