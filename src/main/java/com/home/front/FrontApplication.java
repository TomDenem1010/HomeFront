package com.home.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FrontApplication {

  public static void main(String[] args) {
    SpringApplication.run(FrontApplication.class, args);
  }
}
