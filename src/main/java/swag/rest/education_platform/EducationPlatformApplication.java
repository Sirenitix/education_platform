package swag.rest.education_platform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Locale;

@SpringBootApplication
@EnableJpaRepositories("swag.rest.education_platform")
public class EducationPlatformApplication  {

    public static void main(String[] args) {
        SpringApplication.run(EducationPlatformApplication.class);
        Locale.setDefault(new Locale("en", "US"));
    }



}

