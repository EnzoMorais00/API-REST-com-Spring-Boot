package com.exemplo.apialunos.model;

public class Aluno {

    private final Long id;
    private final String nome;
    private final double nota1;
    private final double nota2;
    private final double frequencia;

    public Aluno(Long id, String nome, double nota1, double nota2, double frequencia) {
        this.id = id;
        this.nome = nome;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.frequencia = frequencia;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getNota1() {
        return nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public double getFrequencia() {
        return frequencia;
    }
}
