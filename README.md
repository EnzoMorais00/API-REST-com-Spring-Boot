# API de Alunos

API REST feita com Java e Spring Boot para consultar alunos e seus boletins. A aprovação depende de duas regras: média mínima de **6,0** e frequência mínima de **75%**. As duas condições precisam ser atendidas ao mesmo tempo.

O projeto usa cinco alunos fictícios, armazenados em uma lista em memória. Não precisa de banco de dados. Os endpoints são de consulta: não cadastram nem alteram alunos.

## Executar o projeto

### Requisitos

- JDK 17 ou JDK 21, com `JAVA_HOME` configurado para a pasta do JDK e `java` disponível no terminal.
- Internet na primeira execução para baixar o Maven e as dependências.
- Não é necessário instalar Maven: o Maven Wrapper está incluído.

Confira o Java:

```text
java -version
javac -version
```

Se os comandos não forem reconhecidos, instale um JDK, por exemplo pelo [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=17), e abra um novo terminal. O projeto usa Spring Boot 3.5.16 e Maven 3.9.9. Veja os [requisitos oficiais do Spring Boot](https://docs.spring.io/spring-boot/3.5/system-requirements.html) e a [documentação do Maven Wrapper](https://maven.apache.org/wrapper/).

### Windows — PowerShell ou Prompt de Comando

Extraia o ZIP e abra o terminal na pasta `api-alunos`, onde está o arquivo `pom.xml`:

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux ou macOS

Na pasta do `pom.xml`:

```bash
bash ./mvnw spring-boot:run
```

Quando aparecer a mensagem `Started ApiAlunosApplication`, acesse:

[http://localhost:8080/alunos](http://localhost:8080/alunos)

Mantenha o terminal aberto enquanto usa a API. Para encerrar, pressione `Ctrl+C` nele. A API retorna JSON; não há uma página HTML. O caminho `/` não possui endpoint: use `/alunos`.

Se a porta 8080 estiver ocupada:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

Nesse caso, troque 8080 por 8081 nas URLs. No Linux/macOS, use `bash ./mvnw` no lugar de `.\mvnw.cmd`.

## Endpoints

| Método | Caminho | Finalidade |
|---|---|---|
| GET | `/alunos` | Lista os cinco alunos e seus dados. |
| GET | `/alunos/{id}` | Busca um aluno pelo ID. |
| GET | `/alunos/{id}/boletim` | Calcula a média e informa a situação acadêmica. |

Todos podem ser testados no navegador, Postman ou Insomnia. O arquivo [requisicoes.http](requisicoes.http) também contém as consultas para clientes HTTP de IDEs.

### Buscar um aluno

Requisição: `GET http://localhost:8080/alunos/1`

Resposta `200 OK`:

```json
{
  "id": 1,
  "nome": "Ana Silva",
  "nota1": 8.0,
  "nota2": 7.0,
  "frequencia": 90.0
}
```

### Consultar o boletim

Requisição: `GET http://localhost:8080/alunos/1/boletim`

Resposta `200 OK`:

```json
{
  "alunoId": 1,
  "nome": "Ana Silva",
  "media": 7.5,
  "frequencia": 90.0,
  "aprovado": true,
  "situacao": "APROVADO"
}
```

### Testar pelo terminal

Windows:

```powershell
curl.exe -i http://localhost:8080/alunos
curl.exe -i http://localhost:8080/alunos/1
curl.exe -i http://localhost:8080/alunos/3/boletim
curl.exe -i http://localhost:8080/alunos/999
```

Linux/macOS: use `curl` no lugar de `curl.exe`. A opção `-i` mostra o código HTTP e os cabeçalhos, além do JSON.

### Erros esperados

| Consulta | Status | Motivo |
|---|---|---|
| `/alunos/999` | 404 | Não há aluno com esse ID. |
| `/alunos/999/boletim` | 404 | Não é possível avaliar um aluno inexistente. |
| `/alunos/abc` | 400 | O ID precisa ser um número inteiro representável por `Long`. |
| `/alunos/abc/boletim` | 400 | O mesmo requisito de ID vale para o boletim. |

Exemplo de resposta para `/alunos/999`:

```json
{
  "status": 404,
  "mensagem": "Aluno com ID 999 não encontrado."
}
```

Exemplo para `/alunos/abc`:

```json
{
  "status": 400,
  "mensagem": "O ID do aluno deve ser um número inteiro."
}
```

IDs como 0 e -1 são inteiros, mas não existem no repositório e retornam 404. Um `POST /alunos` retorna 405, pois o projeto oferece somente consultas.

## Regras de negócio

As regras ficam em `AvaliacaoService`:

1. A média aritmética das duas notas deve ser maior ou igual a 6,0: `(nota1 + nota2) / 2.0`.
2. A frequência deve ser maior ou igual a 75%. Mesmo com média alta, uma frequência inferior a 75% impede a aprovação.

A média não é arredondada antes da comparação. Uma média de 5,995 continua abaixo de 6 e reprova. Notas são consideradas na escala de 0 a 10; frequência é um percentual entre 0 e 100. Como não há entrada de notas ou frequência pela API, esses valores são definidos diretamente nos dados de exemplo.

| ID | Aluno | Nota 1 | Nota 2 | Média | Frequência | Situação |
|---|---|---:|---:|---:|---:|---|
| 1 | Ana Silva | 8,0 | 7,0 | 7,5 | 90% | APROVADO |
| 2 | Bruno Souza | 5,0 | 4,0 | 4,5 | 90% | REPROVADO_POR_MEDIA |
| 3 | Carla Lima | 9,0 | 8,0 | 8,5 | 70% | REPROVADO_POR_FREQUENCIA |
| 4 | Diego Santos | 6,0 | 6,0 | 6,0 | 75% | APROVADO |
| 5 | Elisa Costa | 5,0 | 5,0 | 5,0 | 60% | REPROVADO_POR_MEDIA_E_FREQUENCIA |

Diego demonstra que os limites são inclusivos. Elisa demonstra o resultado quando nenhuma condição é atendida.

## Organização e responsabilidades

```text
src/main/java/com/exemplo/apialunos/
├── ApiAlunosApplication.java
├── controller/
│   └── AlunoController.java
├── service/
│   ├── AlunoService.java
│   └── AvaliacaoService.java
├── repository/
│   └── AlunoRepository.java
├── model/
│   └── Aluno.java
├── dto/
│   └── BoletimResponse.java
└── exception/
    ├── AlunoNaoEncontradoException.java
    └── ApiExceptionHandler.java
```

| Classe | Responsabilidade |
|---|---|
| `ApiAlunosApplication` | Inicializa a aplicação Spring Boot. |
| `AlunoController` | Recebe os GET, extrai o ID da URL e chama `AlunoService`. |
| `AlunoService` | Coordena as consultas; busca o aluno e solicita sua avaliação. |
| `AvaliacaoService` | Calcula a média, aplica as regras de aprovação e monta o boletim. |
| `AlunoRepository` | Armazena a lista em memória e localiza alunos por ID. |
| `Aluno` | Representa os dados de um aluno; os getters permitem sua leitura. |
| `BoletimResponse` | Define os campos do JSON de boletim usando um `record` do Java. |
| `AlunoNaoEncontradoException` | Sinaliza que a busca não encontrou o aluno. |
| `ApiExceptionHandler` | Converte erros esperados em respostas HTTP 400 e 404. |

O Controller não calcula notas nem acessa a lista diretamente. O Repository não decide aprovação. As dependências são recebidas pelo construtor: o Spring cria as classes anotadas e fornece as instâncias necessárias.

### O que acontece em `GET /alunos/3/boletim`

1. `AlunoController` recebe a requisição. `@PathVariable("id")` captura o número 3.
2. O Controller chama `AlunoService.consultarBoletim(3L)`.
3. O Service usa `buscarPorId`, que consulta `AlunoRepository`.
4. O Repository encontra Carla e devolve seus dados.
5. `AlunoService` passa Carla para `AvaliacaoService.avaliar`.
6. `AvaliacaoService` calcula média 8,5 e verifica frequência 70%. Monta o boletim com `REPROVADO_POR_FREQUENCIA`.
7. O resultado volta ao Controller e o Spring o serializa em JSON com status 200.

Se o aluno não existir, a exceção é lançada na busca, antes da avaliação. O tratamento HTTP fica em `ApiExceptionHandler`.

`List.of` cria uma lista que não pode ser alterada. Os objetos `Aluno` têm campos `final` e não têm setters. Os dados ficam em memória durante a execução e são reconstruídos quando a aplicação inicia. Para mudar os exemplos, edite `AlunoRepository` e reinicie.

## Testar e gerar o executável

Windows:

```powershell
.\mvnw.cmd test
.\mvnw.cmd clean verify
java -jar target/api-alunos-1.0.0.jar
```

Linux/macOS:

```bash
bash ./mvnw test
bash ./mvnw clean verify
java -jar target/api-alunos-1.0.0.jar
```

O comando `test` roda os testes. `clean verify` limpa os arquivos gerados, executa os testes e cria o JAR em `target/`. Execute o JAR depois de encerrar qualquer outra instância na porta 8080.

- `AvaliacaoServiceTest`: testa média, frequência, combinações, limites e ausência de arredondamento.
- `AlunoControllerTest`: carrega a aplicação e usa MockMvc para testar as rotas, JSON, status 400/404 e a rejeição de POST.

A suíte contém 30 casos de teste ao todo. O teste com MockMvc exercita as camadas integradas sem abrir uma porta de rede.

O [registro de validação](docs/VALIDACAO.md) descreve as 11 consultas HTTP verificadas e a limitação que impediu executar a suíte automatizada neste ambiente.

## Repositório no GitHub

[EnzoMorais00/API-REST-com-Spring-Boot](https://github.com/EnzoMorais00/API-REST-com-Spring-Boot)

Para obter uma cópia do projeto, com Git instalado:

```bash
git clone https://github.com/EnzoMorais00/API-REST-com-Spring-Boot.git
cd API-REST-com-Spring-Boot
```

Depois, siga as instruções de execução deste README. O Maven Wrapper, incluindo a pasta `.mvn`, faz parte do repositório. Arquivos gerados em `target/` ficam fora do controle de versão.

A aba [Actions](https://github.com/EnzoMorais00/API-REST-com-Spring-Boot/actions) executa a compilação e os testes com Java 17 a cada envio para `main`. Consulte o resultado da execução; a presença do workflow não significa que os testes já passaram.

## Preparar a apresentação e a entrega

- Use o [roteiro da apresentação](docs/ROTEIRO-APRESENTACAO.md) para estudar o fluxo e praticar a demonstração.
- Preencha o [modelo de entrega no Genera](docs/ENTREGA.md) com seu nome e os links reais.

Antes de gravar, execute o projeto e explique cada camada com suas próprias palavras. Personalize os exemplos depois de compreender como as duas regras afetam o resultado. A gravação deve mostrar a aplicação funcionando e sua explicação do código.

Use o link do repositório acima na entrega. Grave sua apresentação e insira o link do vídeo junto ao link do GitHub no Genera.
