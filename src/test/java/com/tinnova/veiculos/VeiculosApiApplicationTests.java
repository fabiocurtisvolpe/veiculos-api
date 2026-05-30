package com.tinnova.veiculos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VeiculosApiApplicationTest {

	@Test
	void contextLoads() {
		// contexto sobe sem banco real
	}
}