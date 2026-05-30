package com.tinnova.veiculos.service;

import com.tinnova.veiculos.config.JwtConfig;
import com.tinnova.veiculos.dto.request.LoginRequest;
import com.tinnova.veiculos.dto.response.TokenResponse;
import com.tinnova.veiculos.entity.UserEntity;
import com.tinnova.veiculos.exception.ErrorMessage;
import com.tinnova.veiculos.exception.UnauthorizedException;
import com.tinnova.veiculos.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    public AuthService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtConfig jwtConfig) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
    }

    public TokenResponse login(LoginRequest request) {

        UserEntity user = repository.findByEmail(request.email())
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.CREDENCIAIS_INVALIDAS.get()));

        if (!passwordEncoder.matches(request.senha(), user.getSenha())) {
            throw new UnauthorizedException(ErrorMessage.CREDENCIAIS_INVALIDAS.get());
        }

        String token = gerarToken(user.getEmail(), user.getRole());

        return new TokenResponse(token, "Bearer");
    }

    private String gerarToken(String email, String role) {

        Key key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));

        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + jwtConfig.getExpiration());

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
