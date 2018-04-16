# ProjetoTwitter
   
 A aplicação consiste em um sistema composto por dois módulos: um para autenticação, para permitir a autenticação dos usuários; e, um módulo web, para listagem do ranking dos dez mais.

 
Concebido para resolução de atividade final da disciplina de POS   
Curso **Análise e Desenvolvimento de Sistemas**   
IFPB Campus Cajazerias   
Professor da disciplina Ricardo Job   



## Pré-requisitos
* Java instalado
* Maven instalado
* Docker instalado

Uma vez com os itens acima em sua maquina acredito que já saiba usar o docker, caso não saiba veja aqui como [fazer](https://github.com/WellingtonLins/CRUD-Docker).


## Implantando a aplicação

Primeiro faça o download dessa aplicação e abra  o terminal do docker e navegue ate a pasta onde esta o codigo da aplicação.  Lá estão o arquivo `docker-compose.yml`, no terminal docker digite :    
```
docker-compose up -d
```
Com esse comando o docker vai criar as imagens e o containers da aplicação.
Em seguida abra o browser no endereço:    


Como nós estamos usando o Windows(home) temos que abrir nesse endereço:


[http://192.168.99.100:8080/ProjetoTwitter/](http://192.168.99.100:8080/ProjetoTwitter/)



Bem a aplicação criada no Twitter para obter as credenciais vai redirecionar para o
seguinte url apos o usuario se autenticar:

[http://192.168.99.100:8080/ProjetoTwitter/twitter](http://192.168.99.100:8080/ProjetoTwitter/twitter)

Essa url é devido ao fato do projeto esta sendo criado em um ambiente com o ssitema
operacional Windows(home).

Caso fosse em um  SO Linux ou mesmo o Windows(Professional) ambas as urls acima seriam:

[http://localhost:8080/ProjetoTwitter/](http://localhost:8080/ProjetoTwitter/)


[http://localhost:8080/ProjetoTwitter/twitter](http://localhost:8080/ProjetoTwitter/twitter)


## Documentação Docker
[Docker referências](https://docs.docker.com/reference/ )

## Construído com 

* [Java](http://www.dropwizard.io/1.0.2/docs/) - Lingugem de programação
* [Postgres](https://www.postgresql.org) - Banco de dados 
* [Maven](https://maven.apache.org/) - Gerenciador de dependencias
* [Payara](www.payara.org/) - Servidor Web usado para a implantação do projeto
* [Docker](https://www.docker.com) - Gerenciador de containers onde podemos usar o container do Tomcat... 
* [NetBeans](https://netbeans.org/downloads/) - Usado para escrever o codigo fonte do projeto
* [SOAP](https://pt.wikipedia.org/wiki/SOAP) - Protocolo usado para fazer a comunicação entre as aplicações cliente e servidor.

## Controle de versão

Nós usamos o [Git](https://git-scm.com/) . 




## Agradecimentos

* Ao professor Ricardo Job 
