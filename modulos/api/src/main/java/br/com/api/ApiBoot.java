package br.com.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "br.com.api" })
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ApiBoot {

    public static void main(String[] args) {
        SpringApplication.run(ApiBoot.class, args);
    }

}
