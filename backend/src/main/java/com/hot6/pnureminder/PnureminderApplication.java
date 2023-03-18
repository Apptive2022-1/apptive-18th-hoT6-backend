package com.hot6.pnureminder;


import com.hot6.pnureminder.Dto.UserDto;
import com.hot6.pnureminder.domain.user.User;
import com.hot6.pnureminder.repository.UserRepository;
import com.hot6.pnureminder.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableJpaAuditing
@RestController
@SpringBootApplication
public class PnureminderApplication {

	@RequestMapping("/")
	String main(){
		return "Main page test";
	}
	public static void main(String[] args) {

		new SpringApplicationBuilder(PnureminderApplication.class)
				.properties(PROPERTIES)
				.run(args);
	}

	private static final String PROPERTIES =
			"spring.config.location="
					+ "classpath:/application.yml";
					//+ ",classpath:/googleapi.yml";


}
