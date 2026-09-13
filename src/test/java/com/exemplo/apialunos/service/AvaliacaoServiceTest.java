package com.exemplo.apialunos.service;

import com.exemplo.apialunos.dto.BoletimResponse;
import com.exemplo.apialunos.model.Aluno;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AvaliacaoServiceTest {

    private final AvaliacaoService service = new AvaliacaoService();

    @ParameterizedTest(name = "notas {0} e {1}, frequência {2}: {5}")
    @CsvSource({
            "8.0, 7.0, 90.0, 7.5, true, APROVADO",
            "6.0, 6.0, 75.0, 6.0, true, APROVADO",
            "5.0, 7.0, 75.0, 6.0, true, APROVADO",
            "5.99, 5.99, 75.0, 5.99, false, REPROVADO_POR_MEDIA",
            "6.0, 6.0, 74.99, 6.0, false, REPROVADO_POR_FREQUENCIA",
            "5.99, 5.99, 74.99, 5.99, false, REPROVADO_POR_MEDIA_E_FREQUENCIA",
            "10.0, 10.0, 0.0, 10.0, false, REPROVADO_POR_FREQUENCIA",
            "0.0, 0.0, 100.0, 0.0, false, REPROVADO_POR_MEDIA",
            "10.0, 10.0, 100.0, 10.0, true, APROVADO"
    })
    void avaliaMediaEFrequenciaEmConjunto(
            double nota1, double nota2, double frequencia,
            double mediaEsperada, boolean aprovadoEsperado, String situacaoEsperada) {
        Aluno aluno = new Aluno(42L, "Aluno de teste", nota1, nota2, frequencia);

        BoletimResponse boletim = service.avaliar(aluno);

        assertAll(
                () -> assertEquals(42L, boletim.alunoId()),
                () -> assertEquals("Aluno de teste", boletim.nome()),
                () -> assertEquals(mediaEsperada, boletim.media(), 1e-12),
                () -> assertEquals(frequencia, boletim.frequencia(), 1e-12),
                () -> assertEquals(aprovadoEsperado, boletim.aprovado()),
                () -> assertEquals(situacaoEsperada, boletim.situacao())
        );
    }

    @Test
    void naoArredondaMediaParaAprovarAlunoAbaixoDeSeis() {
        Aluno aluno = new Aluno(1L, "Aluno no limite", 5.99, 6.0, 100.0);

        BoletimResponse boletim = service.avaliar(aluno);

        assertAll(
                () -> assertEquals(5.995, boletim.media(), 1e-12),
                () -> assertFalse(boletim.aprovado()),
                () -> assertEquals("REPROVADO_POR_MEDIA", boletim.situacao())
        );
    }
}
