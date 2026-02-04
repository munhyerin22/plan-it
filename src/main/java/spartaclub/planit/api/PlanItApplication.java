package spartaclub.planit.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 추가 필수!
@SpringBootApplication
public class PlanItApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanItApplication.class, args);
    }
}