package com.exemplo.apialunos.controller;

import com.exemplo.apialunos.dto.BoletimResponse;
import com.exemplo.apialunos.model.Aluno;
import com.exemplo.apialunos.service.AlunoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public List<Aluno> listar() {
        return alunoService.listar();
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable("id") Long id) {
        return alunoService.buscarPorId(id);
    }

    @GetMapping("/{id}/boletim")
    public BoletimResponse consultarBoletim(@PathVariable("id") Long id) {
        return alunoService.consultarBoletim(id);
    }
}
