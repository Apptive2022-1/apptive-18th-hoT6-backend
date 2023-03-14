package com.pnuagent.demo;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
<<<<<<< Updated upstream:backend/src/main/java/com/pnuagent/demo/DemoApplication.java
		SpringApplication.run(DemoApplication.class, args);
=======

		new SpringApplicationBuilder(PnureminderApplication.class)
				.properties(PROPERTIES)
				.run(args);
>>>>>>> Stashed changes:backend/src/main/java/com/hot6/pnureminder/PnureminderApplication.java
	}

	private static final String PROPERTIES =
			"spring.config.location="
					+ "classpath:/application.yml"
					+ ",classpath:/googleapi.yml";
}
