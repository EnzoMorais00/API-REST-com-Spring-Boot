package com.exemplo.apialunos.service;

import com.exemplo.apialunos.dto.BoletimResponse;
import com.exemplo.apialunos.exception.AlunoNaoEncontradoException;
import com.exemplo.apialunos.model.Aluno;
import com.exemplo.apialunos.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AvaliacaoService avaliacaoService;

    public AlunoService(AlunoRepository alunoRepository, AvaliacaoService avaliacaoService) {
        this.alunoRepository = alunoRepository;
        this.avaliacaoService = avaliacaoService;
    }

    public List<Aluno> listar() {
        return alunoRepository.listar();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.buscarPorId(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));
    }

    public BoletimResponse consultarBoletim(Long id) {
        Aluno aluno = buscarPorId(id);
        return avaliacaoService.avaliar(aluno);
    }
}
