package de.eisingerf.elp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ElpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElpApplication.class, args);
    }
}
