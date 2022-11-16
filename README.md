# ApiRest - Desafio TexoIT
API para possibilitar a leitura da lista de indicados e vencedores da categoria Pior Filme do Golden Raspberry Awards.

Requisitos:
- JDK
- Arquivo csv nomeado como "movielist.csv", conforme exemplo abaixo:

| year | title                | studios                             | producers          | winner |
|------|----------------------|-------------------------------------|--------------------|--------|
| 1980 | Can't Stop the Music | Associated Film Distribution        | Allan Carr         | yes    |
| 1980 | Cruising             | Lorimar Productions, United Artists | Jerry Weintraub    |        |
| 1980 | The Formula          | MGM, United Artists                 | Steve Shagan       |        |
| 1980 | Friday the 13th      | Paramount Pictures                  | Sean S. Cunningham |        |

Tópicos:
- Java (11)
- Projeto Maven
- Spring Boot(v2.7.5)
- Database H2
- Junit

**Como compilar:**

Após abrir o projeto, a IDE irá baixar todas as dependências e instalá-las automaticamente.
Copiar o arquivo movielist.csv no caminho "src/main/resources/"

Após concluir as instalações de depedências e certificar que o arquivo movielist.csv está na pasta especificada 
basta clicar com o botão direito sobre o arquivo "PiorFilmeApiApplication.java" no package "br/com/desafiotexoit/" e clicar em (run as) Java application.
Por padrão o servidor ficará disponível na porta 8080.


Para obter a lista de intervalo de prêmios acessar o link: "http://localhost:8080/awardInterval"


A api irá retorna um json contendo o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido, conforme o exemplo abaixo:

```
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
    "producer": "Matthew Vaughn",
    "interval": 13,
    "previousWin": 2002,
    "followingWin": 2015
  }
  ]
}
```

**Teste de integração:**

Para executar os testes unitario, pelo intellij, basta clicar com o botão direito sobre o diretorio "/src/test/java".
Selecionar Run "All tests"