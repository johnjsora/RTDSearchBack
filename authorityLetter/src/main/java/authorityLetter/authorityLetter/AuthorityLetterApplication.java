package authorityLetter.authorityLetter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AuthorityLetterApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorityLetterApplication.class, args);
	}

}
