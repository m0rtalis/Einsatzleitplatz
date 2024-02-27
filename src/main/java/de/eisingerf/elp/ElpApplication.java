package de.eisingerf.elp;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElpApplication.class, args);
    }

}
