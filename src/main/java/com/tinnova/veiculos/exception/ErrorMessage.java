package com.tinnova.veiculos.exception;

public enum ErrorMessage {

    VEICULO_NAO_ENCONTRADO("Veículo não encontrado"),
    PLACA_JA_CADASTRADA("Placa já cadastrada"),
    ERRO_INTERNO("Ocorreu um erro interno no servidor"),
    ACESSO_NEGADO("Acesso negado"),
    DADOS_INVALIDOS("Dados inválidos fornecidos"),
    FALHA_CONSULTA_DOLAR("Não foi possível obter a cotação do dólar"),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado"),
    CREDENCIAIS_INVALIDAS("Credenciais inválidas");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}