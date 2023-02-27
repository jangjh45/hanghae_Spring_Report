package com.report.hanghae_spring_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaeSpringReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeSpringReportApplication.class, args);
    }

}
