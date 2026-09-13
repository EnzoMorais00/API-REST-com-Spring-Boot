# Entrega da atividade — API REST com Spring Boot

O projeto e este material servem de base para a entrega. **Preencha sua identificação e grave a apresentação antes de enviar os links no Genera.** O repositório está em [https://github.com/EnzoMorais00/API-REST-com-Spring-Boot](https://github.com/EnzoMorais00/API-REST-com-Spring-Boot). O vídeo ainda precisa ser gravado e publicado.

Antes de apresentar, execute o código, compreenda as responsabilidades de cada classe e personalize a explicação com suas palavras. O [roteiro de apresentação](ROTEIRO-APRESENTACAO.md) propõe uma demonstração de 5 a 7 minutos e reúne perguntas para praticar.

## Conferência do projeto

Use esta lista para conferir sua cópia antes da entrega:

- [ ] O projeto abre corretamente na IDE e utiliza um JDK 17.
- [ ] O README explica a proposta, as regras, a execução e os endpoints.
- [ ] O código-fonte está presente em `src/main/java` e os testes em `src/test/java`.
- [ ] O projeto contém `pom.xml`, `mvnw`, `mvnw.cmd` e a pasta `.mvn/wrapper`.
- [ ] O Controller possui três endpoints GET e utiliza `@PathVariable`.
- [ ] `AlunoService` e `AvaliacaoService` possuem responsabilidades diferentes e colaboram no boletim.
- [ ] O Repository mantém os cinco alunos em memória.
- [ ] As regras de média mínima 6 e frequência mínima 75% funcionam, incluindo os valores exatos dos limites.
- [ ] As consultas a aluno inexistente e ID textual devolvem, respectivamente, HTTP 404 e 400.
- [ ] Os testes e o empacotamento terminam sem falhas na sua máquina.

Comandos para a última conferência, executados dentro da pasta do projeto no Windows:

```powershell
.\mvnw.cmd clean verify
java -jar target/api-alunos-1.0.0.jar
```

No Linux ou macOS:

```bash
bash ./mvnw clean verify
java -jar target/api-alunos-1.0.0.jar
```

Com a aplicação iniciada, confira [a lista de alunos](http://localhost:8080/alunos), [a busca do aluno 1](http://localhost:8080/alunos/1) e [o boletim do aluno 1](http://localhost:8080/alunos/1/boletim). Ao terminar, encerre com `Ctrl+C`.

## Repositório no GitHub

- [x] Repositório criado: [https://github.com/EnzoMorais00/API-REST-com-Spring-Boot](https://github.com/EnzoMorais00/API-REST-com-Spring-Boot).
- [ ] Publique o conteúdo da pasta `api-alunos`, mantendo `README.md`, `pom.xml` e `src` na raiz do repositório.
- [ ] Inclua também os arquivos do Maven Wrapper, inclusive a pasta `.mvn`, que começa com ponto.
- [ ] Confira se as pastas geradas `target` e arquivos pessoais da IDE estão excluídos conforme o `.gitignore`.
- [ ] Abra o repositório no navegador e confirme que o README e os arquivos foram publicados.
- [ ] Garanta o acesso do avaliador: use um repositório público ou conceda acesso conforme as orientações do professor.
- [ ] Copie a URL real do repositório para o modelo ao final deste documento.

## Vídeo de apresentação

- [ ] Apresente rapidamente o tema da API e identifique-se.
- [ ] Mostre a organização dos pacotes e explique o Controller.
- [ ] Explique o papel de cada um dos dois Services e do Repository.
- [ ] Mostre as duas regras de negócio no código e explique os limites.
- [ ] Execute a aplicação e faça requisições aos três endpoints GET.
- [ ] Demonstre aprovação, reprovação por média, por frequência e pelos dois critérios.
- [ ] Mostre o aluno com média 6 e frequência 75% para explicar os limites inclusivos.
- [ ] Explique o caminho de uma requisição do navegador até a resposta JSON.
- [ ] Confira se o áudio e o código estão compreensíveis na gravação.
- [ ] Publique o vídeo na plataforma escolhida e habilite o acesso para o avaliador.
- [ ] Copie a URL real do vídeo para o modelo abaixo.

## Modelo para enviar no Genera

Substitua todos os campos entre colchetes. Envie o link do repositório e o link do vídeo, conforme solicitado na atividade.

```text
Nome: [PREENCHER COM SEU NOME]

GitHub:
https://github.com/EnzoMorais00/API-REST-com-Spring-Boot

Vídeo:
[PREENCHER COM O LINK REAL DO VÍDEO]
```

Antes do envio, teste os links com uma conta que tenha as mesmas permissões do avaliador. Para conteúdo público ou disponível a qualquer pessoa com o link, uma janela anônima ajuda a verificar o acesso.
