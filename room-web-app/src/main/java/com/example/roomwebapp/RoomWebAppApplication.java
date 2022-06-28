package com.example.roomwebapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RoomWebAppApplication extends SpringBootServletInitializer {

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(RoomWebAppApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RoomWebAppApplication.class, args);
    }

}
