package com.hot6.pnureminder;

import com.hot6.pnureminder.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;



@SpringBootTest
@Transactional
class UserRepositoryTest {
	@Autowired
    MemberRepository memberRepository;

}

