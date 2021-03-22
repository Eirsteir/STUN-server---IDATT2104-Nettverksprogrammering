package com.nettverksprog.stun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StunApplication {

    public static void main(String[] args) {
        SpringApplication.run(StunApplication.class, args);
    }

    @Bean(initMethod = "start")
    public StunServer onApplicationStartupStunServerRunner() {
        return new StunServer();
    }

}
