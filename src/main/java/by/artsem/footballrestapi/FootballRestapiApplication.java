package by.artsem.footballrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FootballRestapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FootballRestapiApplication.class, args);
    }

}
