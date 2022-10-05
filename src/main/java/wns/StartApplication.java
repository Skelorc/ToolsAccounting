package wns;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import wns.constants.Roles;
import wns.entity.User;
import wns.repo.UsersRepo;

import java.util.Collections;

@SpringBootApplication

public class StartApplication{

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}


}
