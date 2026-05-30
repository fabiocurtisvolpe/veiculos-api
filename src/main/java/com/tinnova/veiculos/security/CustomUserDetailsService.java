package com.tinnova.veiculos.security;

import com.tinnova.veiculos.entity.UserEntity;
import com.tinnova.veiculos.exception.ErrorMessage;
import com.tinnova.veiculos.exception.NotFoundException;
import com.tinnova.veiculos.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = repository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USUARIO_NAO_ENCONTRADO.get()));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getSenha())
                .authorities(user.getRole()) // ex: ROLE_ADMIN, ROLE_USER
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.getAtivo())
                .build();
    }
}
