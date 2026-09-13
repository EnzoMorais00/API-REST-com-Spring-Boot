package com.exemplo.apialunos.repository;

import com.exemplo.apialunos.model.Aluno;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlunoRepository {

    // Dados fictícios em memória. A aplicação não depende de um banco de dados.
    private final List<Aluno> alunos = List.of(
            new Aluno(1L, "Ana Silva", 8.0, 7.0, 90.0),
            new Aluno(2L, "Bruno Souza", 5.0, 4.0, 90.0),
            new Aluno(3L, "Carla Lima", 9.0, 8.0, 70.0),
            new Aluno(4L, "Diego Santos", 6.0, 6.0, 75.0),
            new Aluno(5L, "Elisa Costa", 5.0, 5.0, 60.0)
    );

    public List<Aluno> listar() {
        return alunos;
    }

    public Optional<Aluno> buscarPorId(Long id) {
        for (Aluno aluno : alunos) {
            if (aluno.getId().equals(id)) {
                return Optional.of(aluno);
            }
        }
        return Optional.empty();
    }
}
