<h1 align="center">:rocket: Vendas Application :rocket:</h1>
<p>I developed this project in the UDEMY course 
"Spring Boot Expert: JPA, RESTFul API, Security, JWT e Mais" 
taught by professor Dougllas Sousa. </p>
<p>In this project you can create a new user and authenticated it. 
The endpoints of customers(clientes), products(produtos) 
and orders(pedidos) only worked if the user is authenticated 
and use the token. There are two roles in the application: 
ADMIN and USER. The user with role Admin can do everything 
in the system. The role USER can only manipulate the customers 
and orders endpoints.</p>
<p>In addition to the subjects seen in the course, I decided to 
create unit tests to increment the project.</p>
<p>You can use the Swagger to manipulate the endpoints:
<a href = "http://localhost:8080/swagger-ui.html">
http://localhost:8080/swagger-ui.html</a> </p>
<h1 id="technologies">:rocket: Technologies</h1>
<p>It was used these technologies in this project.</p>

:ballot_box_with_check: [Java](https://www.oracle.com/java/)
:ballot_box_with_check: [Spring Boot](https://spring.io/projects/spring-boot)
:ballot_box_with_check: [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
:ballot_box_with_check: [Spring Security](https://spring.io/projects/spring-security)
:ballot_box_with_check: [JWT](https://jwt.io)
:ballot_box_with_check: [Lombok](https://projectlombok.org/)
:ballot_box_with_check: [MySQL](https://www.mysql.com/)
:ballot_box_with_check: [H2](https://www.h2database.com/html/main.html)
:ballot_box_with_check: [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
:ballot_box_with_check: [Mockito](https://site.mockito.org/)
:ballot_box_with_check: [Swagger](https://swagger.io/)
:ballot_box_with_check: [Maven](https://maven.apache.org/)

<h1 id="how-to-run">:computer: How to run the application</h1>

<h2>Pre Requiriments</h2>

<p>You will need these tools instaled in your machine:</p>

- Must have [Git](https://git-scm.com/ "Git") installed
- At least have Java 8 installed
- Must have Maven installed

```bash
# Clone this repository
git clone https://github.com/yesminmarie/vendas-springboot-jpa

# Go into the folder of the project
cd vendas-springboot-jpa

# execute the project
./mvn spring-boot:run
```
<h2> Endpoints: </h2>
<h3> Clientes: </h3>

:ballot_box_with_check: GET - localhost:8080/api/clientes/{id} (Obtem detalhes de um cliente específico)

:ballot_box_with_check: GET - localhost:8080/api/clientes (Faz um filtro por qualquer propriedade)
<p> Exemplo: http://localhost:8080/api/clientes?cpf=123&nome=maria  </p>

:ballot_box_with_check: POST - localhost:8080/api/clientes (Salva um novo cliente)
<p>Exemplo:</p>

```bash

{
  "cpf": "94666871071",
  "nome": "Maria"
}

```

:ballot_box_with_check: PUT - localhost:8080/api/clientes (atualizar um cliente específico)
<p>Exemplo:</p>

```bash
{
  "cpf": "94666871071",
  "nome": "Maria"
}
```
:ballot_box_with_check: DELETE - localhost:8080/api/{id} (Deleta um cliente pelo id)

<h3> Produtos: </h3>