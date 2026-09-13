package com.exemplo.apialunos.service;

import com.exemplo.apialunos.dto.BoletimResponse;
import com.exemplo.apialunos.model.Aluno;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    private static final double MEDIA_MINIMA = 6.0;
    private static final double FREQUENCIA_MINIMA = 75.0;

    public BoletimResponse avaliar(Aluno aluno) {
        double media = (aluno.getNota1() + aluno.getNota2()) / 2.0;

        // A média é comparada sem arredondamento para não aprovar notas abaixo de 6.
        boolean mediaSuficiente = media >= MEDIA_MINIMA;
        boolean frequenciaSuficiente = aluno.getFrequencia() >= FREQUENCIA_MINIMA;
        boolean aprovado = mediaSuficiente && frequenciaSuficiente;

        String situacao;
        if (aprovado) {
            situacao = "APROVADO";
        } else if (!mediaSuficiente && !frequenciaSuficiente) {
            situacao = "REPROVADO_POR_MEDIA_E_FREQUENCIA";
        } else if (!mediaSuficiente) {
            situacao = "REPROVADO_POR_MEDIA";
        } else {
            situacao = "REPROVADO_POR_FREQUENCIA";
        }

        return new BoletimResponse(
                aluno.getId(), aluno.getNome(), media,
                aluno.getFrequencia(), aprovado, situacao
        );
    }
}
