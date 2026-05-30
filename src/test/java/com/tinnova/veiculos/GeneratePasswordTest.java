package com.tinnova.veiculos;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePasswordTest {

    @Test
    void gerarSenhas() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String admin = encoder.encode("admin123");
        String user = encoder.encode("user123");

        System.out.println("Senha ADMIN (admin123): " + admin);
        System.out.println("Senha USER  (user123): " + user);
    }
}
