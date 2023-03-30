package com.pnuagent.demo;

import com.hot6.pnureminder.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;



@SpringBootTest
<<<<<<< Updated upstream:backend/src/test/java/com/pnuagent/demo/DemoApplicationTests.java
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
=======
@Transactional
class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;
>>>>>>> Stashed changes:backend/src/test/java/com/hot6/pnureminder/PnureminderApplicationTests.java

}

