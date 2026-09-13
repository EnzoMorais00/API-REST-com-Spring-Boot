package com.exemplo.apialunos.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listaOsCincoAlunosCadastrados() throws Exception {
        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Ana Silva"))
                .andExpect(jsonPath("$[0].nota1").value(8.0))
                .andExpect(jsonPath("$[0].nota2").value(7.0))
                .andExpect(jsonPath("$[0].frequencia").value(90.0))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[4].nome").value("Elisa Costa"));
    }

    @ParameterizedTest(name = "GET /alunos/{0}: {1}")
    @CsvSource({
            "1, Ana Silva, 8.0, 7.0, 90.0",
            "2, Bruno Souza, 5.0, 4.0, 90.0",
            "3, Carla Lima, 9.0, 8.0, 70.0",
            "4, Diego Santos, 6.0, 6.0, 75.0",
            "5, Elisa Costa, 5.0, 5.0, 60.0"
    })
    void buscaAlunoPeloId(int id, String nome, double nota1, double nota2, double frequencia)
            throws Exception {
        mockMvc.perform(get("/alunos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(nome))
                .andExpect(jsonPath("$.nota1").value(nota1))
                .andExpect(jsonPath("$.nota2").value(nota2))
                .andExpect(jsonPath("$.frequencia").value(frequencia));
    }

    @ParameterizedTest(name = "GET /alunos/{0}/boletim: {5}")
    @CsvSource({
            "1, Ana Silva, 7.5, 90.0, true, APROVADO",
            "2, Bruno Souza, 4.5, 90.0, false, REPROVADO_POR_MEDIA",
            "3, Carla Lima, 8.5, 70.0, false, REPROVADO_POR_FREQUENCIA",
            "4, Diego Santos, 6.0, 75.0, true, APROVADO",
            "5, Elisa Costa, 5.0, 60.0, false, REPROVADO_POR_MEDIA_E_FREQUENCIA"
    })
    void retornaBoletimComResultadoDasDuasRegras(
            int id, String nome, double media, double frequencia, boolean aprovado, String situacao)
            throws Exception {
        mockMvc.perform(get("/alunos/{id}/boletim", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.alunoId").value(id))
                .andExpect(jsonPath("$.nome").value(nome))
                .andExpect(jsonPath("$.media").value(media))
                .andExpect(jsonPath("$.frequencia").value(frequencia))
                .andExpect(jsonPath("$.aprovado").value(aprovado))
                .andExpect(jsonPath("$.situacao").value(situacao));
    }

    @ParameterizedTest(name = "GET {0}: aluno {1} inexistente")
    @CsvSource({
            "/alunos/999, 999",
            "/alunos/999/boletim, 999",
            "/alunos/0, 0",
            "/alunos/0/boletim, 0",
            "/alunos/-1, -1",
            "/alunos/-1/boletim, -1"
    })
    void retorna404QuandoAlunoNaoExiste(String caminho, long id) throws Exception {
        mockMvc.perform(get(caminho))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.mensagem").value("Aluno com ID " + id + " não encontrado."));
    }

    @ParameterizedTest(name = "GET {0}: ID inválido")
    @ValueSource(strings = {"/alunos/abc", "/alunos/abc/boletim"})
    void retorna400QuandoIdNaoENumeroInteiro(String caminho) throws Exception {
        mockMvc.perform(get(caminho))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.mensagem").value("O ID do aluno deve ser um número inteiro."));
    }

    @Test
    void rejeitaCadastroPorPost() throws Exception {
        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id":6,"nome":"Novo aluno","nota1":8,"nota2":8,"frequencia":90}
                                """))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(get("/alunos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }
}
