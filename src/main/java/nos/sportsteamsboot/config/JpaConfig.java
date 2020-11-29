package nos.sportsteamsboot.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.TimeZone;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("nos.sportsteamsboot.repository")
public class JpaConfig {

}
