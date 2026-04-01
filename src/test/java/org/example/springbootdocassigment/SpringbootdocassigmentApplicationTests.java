package org.example.springbootdocassigment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringbootdocassigmentApplicationTests {

	@Test
	void contextLoads() {
		//test
	}

	@Test
	void dummyTestShouldPassWithTrueCondition() {
		assertTrue(true);
	}

	@Test
	void dummyMathTestShouldPass() {
		assertEquals(4, 2 + 2);
	}

}
