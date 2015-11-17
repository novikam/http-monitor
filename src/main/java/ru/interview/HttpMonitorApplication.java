package ru.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.interview.config.JpaConfig;

@SpringBootApplication
@ComponentScan("ru.interview")
public class HttpMonitorApplication {


    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[] {HttpMonitorApplication.class, JpaConfig.class}, args);
    }
}
