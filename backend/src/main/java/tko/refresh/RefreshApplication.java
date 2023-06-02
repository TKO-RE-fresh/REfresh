package tko.refresh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableAspectJAutoProxy // 오토 프록싱(AOP)
@EnableScheduling
@EnableCaching // 추가할 부분
@EnableJpaRepositories(basePackages = "tko.refresh.repository.*")
public class RefreshApplication {
	public static void main(String[] args) {
		SpringApplication.run(RefreshApplication.class, args);
	}

}
