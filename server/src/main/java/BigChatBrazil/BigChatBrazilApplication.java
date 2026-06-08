package BigChatBrazil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BigChatBrazilApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigChatBrazilApplication.class, args);
	}

}
