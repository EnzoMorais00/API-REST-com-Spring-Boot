# Roteiro de apresentação — API de alunos

Duração sugerida: **5 a 7 minutos**. Use este roteiro como apoio: execute o projeto, compreenda cada classe e personalize a explicação com suas palavras antes de gravar. Substitua a identificação inicial pelo seu nome.

## Preparação

1. Abra a pasta `api-alunos` na IDE e deixe visíveis os pacotes de `src/main/java/com/exemplo/apialunos`.
2. Confira se um JDK 17 está instalado e se o terminal reconhece `java -version`.
3. Execute os testes no terminal, dentro da pasta do projeto:

   ```powershell
   .\mvnw.cmd test
   ```

   No Linux ou macOS, use `bash ./mvnw test`.

4. Deixe um navegador aberto para fazer as requisições GET durante a demonstração.
5. A primeira execução do Maven Wrapper pode baixar dependências. Faça essa etapa antes de gravar.

## 0:00–0:35 — Apresentar a proposta

Fala sugerida:

> Meu nome é [seu nome]. Esta API consulta alunos e calcula a situação de aprovação. Desenvolvi o projeto com Java 17 e Spring Boot. Ela possui três endpoints GET, dois Services com funções diferentes e um Repository que guarda dados em memória.

Explique o critério escolhido: o aluno precisa ter **média maior ou igual a 6** e **frequência maior ou igual a 75%**. As duas condições precisam ser atendidas.

## 0:35–1:25 — Mostrar a organização do projeto

Abra os arquivos e explique brevemente:

| Arquivo ou pacote | Responsabilidade |
| --- | --- |
| `controller/AlunoController` | Recebe as requisições GET, lê o ID da URL e chama o Service. |
| `service/AlunoService` | Lista e busca alunos; coordena a consulta do boletim e devolve o resultado da avaliação. |
| `service/AvaliacaoService` | Calcula a média das duas notas, aplica os critérios de aprovação e monta o boletim. |
| `repository/AlunoRepository` | Mantém os alunos em uma lista em memória e permite consultá-los. |
| `model/Aluno` | Representa os dados de um aluno: ID, nome, duas notas e frequência. |
| `dto/BoletimResponse` | Define os campos devolvidos na consulta do boletim. |
| `exception` | Trata situações como aluno inexistente e ID em formato inválido. |

Fala sugerida:

> O Controller cuida da entrada HTTP. O AlunoService coordena a consulta. O AvaliacaoService concentra os cálculos e a decisão de aprovação. O Repository cuida do acesso aos dados. Assim, cada classe tem uma responsabilidade clara.

## 1:25–2:15 — Explicar o Controller e o caminho da requisição

Mostre `AlunoController` e identifique os três endpoints:

| Método e caminho | Resultado |
| --- | --- |
| `GET /alunos` | Lista os alunos cadastrados em memória. |
| `GET /alunos/{id}` | Busca um aluno pelo ID. |
| `GET /alunos/{id}/boletim` | Retorna a média, a frequência e a situação do aluno. |

Explique que `@RestController` permite devolver os objetos como corpo da resposta, normalmente em JSON, e que `@GetMapping` associa o método Java a uma rota GET.

Ao mostrar `@PathVariable`, use um exemplo concreto:

> Na URL `/alunos/1/boletim`, o número 1 ocupa o lugar de `{id}`. O Spring converte esse trecho para o tipo `Long` e o entrega ao método do Controller.

Descreva o fluxo do boletim:

```text
Navegador faz GET /alunos/1/boletim
    ↓
AlunoController chama AlunoService
    ↓
AlunoService busca o aluno no AlunoRepository
    ↓
AvaliacaoService calcula, avalia e monta BoletimResponse
    ↓
AlunoService devolve o boletim recebido do AvaliacaoService
    ↓
Controller devolve a resposta HTTP em JSON
```

## 2:15–3:15 — Explicar os Services e as regras

Abra `AlunoService` e mostre que ele consulta o Repository. No método `consultarBoletim`, ele busca o aluno e passa esse objeto para `avaliacaoService.avaliar(aluno)`. O `AvaliacaoService` calcula o resultado e cria o `BoletimResponse`; o `AlunoService` devolve essa resposta. A colaboração entre os dois Services é a razão de ambos existirem.

Em seguida, abra `AvaliacaoService` e localize as duas regras:

1. A média é `(nota1 + nota2) / 2.0` e precisa ser maior ou igual a `6.0`.
2. A frequência precisa ser maior ou igual a `75.0`.

Explique que a aprovação exige as duas condições ao mesmo tempo. A média calculada é usada diretamente na decisão, sem arredondá-la para aprovar alguém que ficou abaixo do limite.

| Média | Frequência | Situação |
| --- | --- | --- |
| ≥ 6 | ≥ 75% | `APROVADO` |
| < 6 | ≥ 75% | `REPROVADO_POR_MEDIA` |
| ≥ 6 | < 75% | `REPROVADO_POR_FREQUENCIA` |
| < 6 | < 75% | `REPROVADO_POR_MEDIA_E_FREQUENCIA` |

Fala sugerida:

> Essas regras ficam no Service porque são decisões do sistema. O Controller apenas recebe a requisição e encaminha o trabalho. Por exemplo, uma média alta não compensa frequência abaixo de 75%.

## 3:15–3:45 — Explicar o Repository e iniciar a aplicação

Mostre `AlunoRepository` e os cinco alunos.

> Os dados ficam em uma lista criada com `List.of`. Neste projeto ela é fixa e não pode ser alterada por operações de inclusão ou remoção. Não há banco de dados. Ao reiniciar a aplicação, os mesmos dados definidos no código são carregados novamente.

Mostre a aplicação iniciando no terminal:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
bash ./mvnw spring-boot:run
```

Espere o log de inicialização terminar antes de acessar os endpoints. A aplicação usa `http://localhost:8080`.

## 3:45–5:45 — Demonstrar as requisições

Abra os links abaixo no navegador. Mostre o endereço e o JSON retornado em cada etapa.

1. **Listagem:** [GET /alunos](http://localhost:8080/alunos). Mostre que existem cinco alunos e aponte os campos de um deles.
2. **Busca por ID:** [GET /alunos/1](http://localhost:8080/alunos/1). Explique que a URL seleciona apenas Ana Silva.
3. **Boletim aprovado:** [GET /alunos/1/boletim](http://localhost:8080/alunos/1/boletim). As notas 8 e 7 produzem média 7,5; a frequência é 90%. As duas condições são atendidas.
4. **Média insuficiente:** [GET /alunos/2/boletim](http://localhost:8080/alunos/2/boletim). Bruno tem média 4,5 e frequência 90%. A situação é `REPROVADO_POR_MEDIA`.
5. **Frequência insuficiente:** [GET /alunos/3/boletim](http://localhost:8080/alunos/3/boletim). Carla tem média 8,5 e frequência 70%. A situação é `REPROVADO_POR_FREQUENCIA`.
6. **Valores no limite:** [GET /alunos/4/boletim](http://localhost:8080/alunos/4/boletim). Diego tem média 6 e frequência 75%. Ele é aprovado porque os limites são inclusivos.
7. **Duas condições insuficientes:** [GET /alunos/5/boletim](http://localhost:8080/alunos/5/boletim). Elisa tem média 5 e frequência 60%. A situação é `REPROVADO_POR_MEDIA_E_FREQUENCIA`.
8. **Aluno inexistente:** [GET /alunos/999](http://localhost:8080/alunos/999). Mostre a mensagem de erro. A resposta HTTP é `404 Not Found`.
9. **ID inválido:** [GET /alunos/abc](http://localhost:8080/alunos/abc). Um texto não pode ser convertido para o ID numérico esperado. A resposta HTTP é `400 Bad Request`.

O navegador mostra o corpo da resposta. Para evidenciar também os códigos HTTP, use a aba **Network/Rede** das ferramentas do desenvolvedor e recarregue a página, ou execute no Windows:

```powershell
curl.exe -i http://localhost:8080/alunos/999
curl.exe -i http://localhost:8080/alunos/abc
```

No Linux ou macOS, use `curl -i` nas mesmas URLs.

## 5:45–6:30 — Explicar os erros e encerrar

Mostre rapidamente `AlunoNaoEncontradoException` e `ApiExceptionHandler`.

> Se não houver aluno com o ID informado, a aplicação sinaliza essa situação e devolve um erro 404 em JSON. Se o ID não for numérico, o tratamento devolve 400. Isso evita tratar uma consulta inválida como se fosse um boletim válido.

Se houver tempo, mostre o resultado dos testes e explique que eles verificam as regras, inclusive os limites de média 6 e frequência 75, e o comportamento das rotas.

Encerre com uma frase própria que conecte o funcionamento à organização das classes.

## Perguntas prováveis na avaliação

**Por que existem dois Services?**

`AlunoService` cuida das operações relacionadas aos alunos e coordena a consulta do boletim. `AvaliacaoService` cuida do cálculo, das regras de aprovação e da montagem da resposta do boletim. Isso permite compreender e testar essas responsabilidades separadamente.

**Por que as regras não estão no Controller?**

O Controller cuida da comunicação HTTP. A aprovação é uma decisão do domínio da aplicação e fica no Service, onde pode ser utilizada sem depender de uma URL.

**O que o Repository faz?**

Ele concentra o acesso aos alunos guardados na lista. Os Services pedem os dados ao Repository, sem precisar manter essa lista por conta própria.

**Como os dados chegam até o Repository?**

Neste projeto, os objetos `Aluno` são definidos no próprio código do Repository. Uma requisição GET consulta esses objetos; não cadastra novos alunos. O ID recebido na URL é repassado pelo Controller ao Service e usado na busca no Repository.

**O que é `@PathVariable`?**

É a anotação que vincula um trecho variável do caminho da URL a um parâmetro Java. Em `/alunos/2`, o valor de `id` é 2.

**O que acontece ao chamar `/alunos/3/boletim`?**

O Controller recebe o ID 3. O AlunoService busca Carla no Repository e passa seus dados ao AvaliacaoService, que calcula e monta o boletim. O resultado volta pelo AlunoService e pelo Controller. A média é 8,5, mas a frequência é 70%, então a situação é `REPROVADO_POR_FREQUENCIA`.

**E se a média for exatamente 6 e a frequência exatamente 75%?**

O aluno é aprovado. As comparações usam “maior ou igual”, portanto os limites fazem parte da condição de aprovação.

**E se a média for 5,99 e a frequência 100%?**

O aluno é reprovado por média. A decisão não arredonda 5,99 para 6 e uma frequência alta não substitui a nota mínima.

**E se as duas condições forem insuficientes?**

A resposta informa `REPROVADO_POR_MEDIA_E_FREQUENCIA`, deixando explícitos os dois motivos.

**Por que usar um DTO no boletim?**

`BoletimResponse` organiza os campos que essa consulta deve devolver, como média e situação. O modelo `Aluno` guarda os dados do aluno; o DTO representa a resposta específica do boletim.

**Qual é a diferença entre o modelo e o `record` de resposta?**

`Aluno` é uma classe imutável com getters para seus dados. `BoletimResponse` é um `record`, recurso do Java que define uma estrutura compacta para carregar os dados da resposta.

**Qual é a diferença entre 400 e 404 aqui?**

`400` significa que o ID recebido tem formato inválido, como `abc`. `404` significa que o ID é numérico, mas nenhum aluno correspondente foi encontrado, como `999`.

**Por que a busca no Repository retorna `Optional<Aluno>`?**

Uma busca pode encontrar um aluno ou não encontrar ninguém. O `Optional` representa essas duas possibilidades. No AlunoService, `orElseThrow` devolve o aluno encontrado ou lança `AlunoNaoEncontradoException` quando o resultado está vazio. O ApiExceptionHandler transforma essa exceção na resposta HTTP 404.

**O que ocorre quando a aplicação é reiniciada?**

A lista fixa é criada novamente a partir do código. Não há persistência em banco de dados nem endpoint de cadastro neste projeto.

**O que faz o Spring conectar Controller, Services e Repository?**

O Spring reconhece as classes anotadas como componentes e fornece suas dependências por injeção via construtor. Assim, uma classe recebe os objetos de que precisa sem criá-los manualmente a cada requisição.

**Como gerar e executar um arquivo JAR?**

Na raiz do projeto, execute:

```powershell
.\mvnw.cmd clean verify
java -jar target/api-alunos-1.0.0.jar
```

No Linux ou macOS, troque o primeiro comando por `bash ./mvnw clean verify`. Encerre a instância anterior com `Ctrl+C` antes de iniciar o JAR na mesma porta.
