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
@AllArgsConstructor
public class StartApplication implements ApplicationRunner {
	private final UsersRepo usersRepo;
	private final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		User admin = usersRepo.findByUsername("skelorc");
		User andrew = usersRepo.findByUsername("andrew");
		if(admin == null) {
			admin = new User();
			admin.setFullName("Petrov Ivan");
			admin.setUsername("skelorc");
			admin.setPassword(passwordEncoder.encode("123"));
			admin.getRoles().addAll(Collections.singleton(Roles.ADMIN));
			usersRepo.save(admin);
		}
		if (andrew == null)
		{
			andrew = new User();
			andrew.setFullName("andrew");
			andrew.setUsername("andrew");
			andrew.setPassword(passwordEncoder.encode("123"));
			andrew.getRoles().addAll(Collections.singleton(Roles.ADMIN));
			usersRepo.save(andrew);
		}
	}
}
