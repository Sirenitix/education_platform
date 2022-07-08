package swag.rest.education_platform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("swag.rest.education_platform")
public class BankAppDeliveryApplication  {

    public static void main(String[] args) {
        SpringApplication.run(BankAppDeliveryApplication.class);
    }


}

