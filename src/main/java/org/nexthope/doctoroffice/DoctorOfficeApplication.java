package org.nexthope.doctoroffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DoctorOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoctorOfficeApplication.class, args);
    }

}
