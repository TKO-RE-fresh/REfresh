package tko.refresh;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.RequiredArgsConstructor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tko.refresh.domain.Department;
import tko.refresh.domain.Member;
import tko.refresh.repository.MemberRepository;
import tko.refresh.service.MemberService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableAspectJAutoProxy // 오토 프록싱(AOP)
@EnableScheduling
@EnableJpaRepositories
public class RefreshApplication {


	public static void main(String[] args) {
		SpringApplication.run(RefreshApplication.class, args);
	}

}
