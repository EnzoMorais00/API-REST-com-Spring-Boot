# Validação realizada

O JAR da API foi empacotado e executado com Java 17. As 11 consultas HTTP abaixo passaram, verificando tanto o status quanto o conteúdo das respostas. O processo da aplicação foi encerrado ao terminar a verificação.

| Consulta | Resultado |
|---|---|
| GET /alunos | 200; cinco alunos |
| GET /alunos/1 | 200; dados de Ana Silva |
| GET /alunos/1/boletim | 200; aprovada |
| GET /alunos/2/boletim | 200; reprovado por média |
| GET /alunos/3/boletim | 200; reprovada por frequência |
| GET /alunos/4/boletim | 200; aprovado nos limites de 6 e 75% |
| GET /alunos/5/boletim | 200; reprovada pelos dois critérios |
| GET /alunos/999 | 404 |
| GET /alunos/999/boletim | 404 |
| GET /alunos/abc | 400 |
| GET /alunos/abc/boletim | 400 |

## Limitação da validação

Foram incluídos 30 casos de teste automatizados em `src/test/java`. **A suíte JUnit não foi executada com sucesso neste ambiente.** O comando `clean verify` encontrou `AccessDeniedException` durante a resolução de caminhos do Windows. O compilador também reportou `Cannot close compiler resources` ao lidar com esses caminhos. As permissões solicitadas não resolveram a restrição do executor.

A verificação HTTP do JAR confirma o comportamento das consultas listadas; ela não substitui a execução da suíte de testes. Para concluir essa etapa, abra um terminal local com o JDK configurado, na pasta do `pom.xml`, e execute:

```powershell
.\mvnw.cmd clean verify
```

No Linux ou macOS, use `bash ./mvnw clean verify`. Confira `BUILD SUCCESS` e o resumo dos testes antes da entrega.
