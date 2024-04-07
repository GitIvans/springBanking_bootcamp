package com.example.demo.account;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AccountConfig {

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository repository) {
        return args -> {
            Account ivans = new Account( "Ivans", 3441);
            Account janis = new Account( "Janis", 3443);

            repository.saveAll(
                    List.of(ivans, janis)
            );
        };

    }
}
