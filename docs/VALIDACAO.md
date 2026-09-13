# Validação realizada

Em 13/09/2026, o GitHub Actions executou a compilação, os testes e o empacotamento com Java 17 e Maven Wrapper. O resultado foi **BUILD SUCCESS**.

[Ver a execução concluída](https://github.com/EnzoMorais00/API-REST-com-Spring-Boot/actions/runs/34777651776)

Código verificado no commit: 4f3cc923401fabe063c6bfaa029be7d4c30c0e1a.

| Suíte | Testes | Falhas | Erros | Ignorados |
|---|---:|---:|---:|---:|
| AlunoControllerTest | 20 | 0 | 0 | 0 |
| AvaliacaoServiceTest | 10 | 0 | 0 | 0 |
| Total | 30 | 0 | 0 | 0 |

Também foram verificadas 11 consultas HTTP contra o JAR executável local: listagem dos cinco alunos, busca por ID, os cinco boletins, aluno inexistente nas duas rotas (404) e ID textual nas duas rotas (400). Todas passaram, incluindo a conferência do JSON.

## Repetir a validação

No Windows, execute na pasta do pom.xml:

    .\mvnw.cmd clean verify

No Linux ou macOS:

    bash ./mvnw clean verify

A aba Actions executa esse procedimento a cada envio para main. Confira o resultado da execução correspondente à versão que será entregue.

## Histórico do ambiente local

A tentativa inicial de executar a suíte no ambiente restrito do Windows encontrou falhas de permissão na resolução de caminhos. A validação completa foi posteriormente concluída no GitHub Actions, conforme o resultado acima.
