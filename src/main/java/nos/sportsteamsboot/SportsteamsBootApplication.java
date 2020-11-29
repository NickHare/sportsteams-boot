package nos.sportsteamsboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
public class SportsteamsBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsteamsBootApplication.class, args);
	}
//
//	@PostConstruct
//	public void init() {
//		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
//	}

}
