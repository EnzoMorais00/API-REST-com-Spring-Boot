package com.exemplo.apialunos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AlunoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> tratarAlunoNaoEncontrado(
            AlunoNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", 404, "mensagem", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> tratarIdInvalido() {
        return ResponseEntity.badRequest()
                .body(Map.of("status", 400, "mensagem", "O ID do aluno deve ser um número inteiro."));
    }
}
