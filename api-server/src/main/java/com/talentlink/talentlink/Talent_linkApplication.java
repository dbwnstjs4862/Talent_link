package com.talentlink.talentlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Talent_linkApplication {
    public static void main(String[] args) {
        SpringApplication.run(Talent_linkApplication.class, args);
    }
}
