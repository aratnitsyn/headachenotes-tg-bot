package org.github.aratnitsyn.headachenotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.github.aratnitsyn.headachenotes")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
