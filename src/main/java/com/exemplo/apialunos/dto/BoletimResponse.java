package com.exemplo.apialunos.dto;

// Um record representa os dados da resposta e gera os métodos de acesso.
public record BoletimResponse(
        Long alunoId,
        String nome,
        double media,
        double frequencia,
        boolean aprovado,
        String situacao
) {
}
