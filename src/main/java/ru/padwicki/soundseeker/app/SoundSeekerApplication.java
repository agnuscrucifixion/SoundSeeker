package ru.padwicki.soundseeker.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ru.padwicki.soundseeker")
public class SoundSeekerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoundSeekerApplication.class, args);
    }

}
