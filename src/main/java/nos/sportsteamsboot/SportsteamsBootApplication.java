package nos.sportsteamsboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
public class SportsteamsBootApplication {

	@Autowired
	ApplicationContext ac;

	public static void main(String[] args) {
		SpringApplication.run(SportsteamsBootApplication.class, args);
	}
//
//	@PostConstruct
//	public void init() {
//		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
//	}

}
