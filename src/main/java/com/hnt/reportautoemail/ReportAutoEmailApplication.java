package com.hnt.reportautoemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReportAutoEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportAutoEmailApplication.class, args);
    }

}
