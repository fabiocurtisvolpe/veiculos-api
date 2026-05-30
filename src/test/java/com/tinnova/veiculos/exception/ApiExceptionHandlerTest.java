package com.tinnova.veiculos.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ApiExceptionHandlerTest {

    private ApiExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ApiExceptionHandler();
    }

    @Test
    void handleNotFound_deveRetornar404() {
        NotFoundException ex = new NotFoundException("não encontrado");
        ResponseEntity<?> response = handler.handleNotFound(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("não encontrado");
    }

    @Test
    void handleConflict_deveRetornar409() {
        ConflictException ex = new ConflictException("conflito");
        ResponseEntity<?> response = handler.handleConflict(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isEqualTo("conflito");
    }

    @Test
    void handleBusiness_deveRetornar400() {
        BusinessException ex = new BusinessException("regra de negócio");
        ResponseEntity<?> response = handler.handleBusiness(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("regra de negócio");
    }

    @Test
    void handleUnauthorized_deveRetornar401() {
        UnauthorizedException ex = new UnauthorizedException("não autorizado");
        ResponseEntity<?> response = handler.handleUnauthorized(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isEqualTo("não autorizado");
    }

    @Test
    void handleForbidden_deveRetornar403() {
        ForbiddenException ex = new ForbiddenException("proibido");
        ResponseEntity<?> response = handler.handleForbidden(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isEqualTo("proibido");
    }

    @Test
    void handleBadRequest_deveRetornar400() {
        BadRequestException ex = new BadRequestException("requisição inválida");
        ResponseEntity<?> response = handler.handleBadRequest(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("requisição inválida");
    }

    @Test
    void handleAccessDenied_deveRetornar403ComMensagemPadrao() {
        AccessDeniedException ex = new AccessDeniedException("acesso negado");
        ResponseEntity<?> response = handler.handleAccessDenied(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isEqualTo(ErrorMessage.ACESSO_NEGADO.get());
    }

    @Test
    void handleGeneric_deveRetornar500ComMensagemPadrao() {
        Exception ex = new RuntimeException("erro inesperado");
        ResponseEntity<?> response = handler.handleGeneric(ex);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo(ErrorMessage.ERRO_INTERNO.get());
    }
}