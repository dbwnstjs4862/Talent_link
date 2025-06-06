package com.talentlink.talentlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class TalentlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalentlinkApplication.class, args);
	}

}