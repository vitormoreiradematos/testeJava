# testeJava

# Sobre o projeto
A aplicação é uma API que consiste em gerenciar eventos.

# Como executar o projeto
Configurar as variaveis de ambiente para o banco de dados. Para isso utilize o comando "Ctrl+Alt+Shift+B,L". Em Override properties insira as variaveis a seguir:
DB_URL=jdbc:h2:mem:testdb
DB_USERNAME=sa
DB_PASSWORD=
DB_H2_CONSOLE_ENABLE=true
DB_H2_CONSOLE_PATH=/h2-console
DB_JPA_SHOW-SQL=true
DB_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true

Após configurar as variaveis, execute o projeto com spring boot app.

## Requisitos
• Versão mínima do JRE: 11;
• Utilização do framework SpringBoot (Versão mínima 2.2);

# Como utilizar
Os recursos podem ser acessados em: http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config

Temos os seguinte controllers em http://localhost:8080:

UsuarioResource
  GET em: /usuarios/{id}
  PUT em: /usuarios/{id}
  DELETE em: /usuarios/{id}
  GET em: /usuarios
  POST em: /usuarios

UsuarioEvento
  GET em: /eventos/{id}
  PUT em: /eventos/{id}
  DELETE em: /eventos/{id}
  GET em: /eventos
  POST em: /eventos

UsuarioNotificacao
  GET em: /notificacoes/{id}
  GET em: /notificacoes
  
#Exemplos
____________________________________________________________________________
  POST em http://localhost:8080/usuarios
{
    "nome": "ze",
    "email": "ze@gmail.com",
    "telefone": "(16) 99127-3747"
}
____________________________________________________________________________
____________________________________________________________________________
  PUT em http://localhost:8080/usuarios/3
{
    "nome": "Maria",
    "email": "maria@gmail.com",
    "telefone": "(16) 99127-3747"
}
____________________________________________________________________________
____________________________________________________________________________
  POST em http://localhost:8080/eventos
{
    "data": "2021-07-27T14:30:00Z",
    "titulo": "Reunião com Thales",
    "descricao": "Reunião para conversar sobre o desenvolvimento do projeto",
    "organizador": {
        "id": 1
    },
    "convidados": [
        {
            "id": 2
        },
        {
            "id": 3
        },
        {
            "nome": "Thales",
            "email": "thales@gmail.com",
            "telefone": "(16) 99600-5011"
        }
    ]
}

____________________________________________________________________________
____________________________________________________________________________
  PUT em http://localhost:8080/eventos/3
{
    "data": "2021-07-27T14:30:00Z",
    "titulo": "Reunião com Thales",
    "descricao": "Reunião para conversar sobre o desenvolvimento do projeto",
    "organizador": {
        "id": 1
    },
    "convidados": [
        {
            "id": 1
        },
        {
            "id": 2
        },
        {
            "id": 3
        },
        {
            "nome": "Thales",
            "email": "thales@gmail.com",
            "telefone": "(16) 99600-5011"
        }
    ]
}
____________________________________________________________________________
  

# Autor
Vitor Moreira de matos

