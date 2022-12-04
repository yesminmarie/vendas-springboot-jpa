<h1 align="center">:rocket: Vendas Application :rocket:</h1>

<p>I developed this project in the UDEMY course 
"Spring Boot Expert: JPA, RESTFul API, Security, JWT e Mais" 
taught by professor Dougllas Sousa. </p>

<p>In this project you can create a new user and authenticated it. 
The endpoints of customers(clientes), products(produtos) 
and orders(pedidos) only worked if the user is authenticated 
and use the token. There are two roles in the application: 
ADMIN and USER. The user with role ADMIN can do everything 
in the system. The role USER can only manipulate the customers 
and orders endpoints.</p>

<p>In addition to the subjects seen in the course, I decided to 
create unit tests with JUnit and Mockito to increment the project.</p>

<p>You can use the Swagger to manipulate the endpoints:
<a href = "http://localhost:8080/swagger-ui.html">
http://localhost:8080/swagger-ui.html</a> </p>

<h1 id="technologies">:rocket: Technologies</h1>

<p>It was used these technologies in this project.</p>

- [Java](https://www.oracle.com/java/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT](https://jwt.io)
- [Lombok](https://projectlombok.org/)
- [MySQL](https://www.mysql.com/)
- [H2](https://www.h2database.com/html/main.html)
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org/)
- [Swagger](https://swagger.io/)
- [Maven](https://maven.apache.org/)

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

<h3> Usuarios: </h3>

- POST - localhost:8080/api/usuarios (Save a new user)

<p><strong>Example:</strong></p>

```bash
{
  "admin": true,
  "login": "maria",
  "senha": "teste123"
}

```

- POST - localhost:8080/api/usuarios/auth (Authenticate a user)

<p><strong>Example:</strong></p>

```bash
{
  "login": "maria",
  "senha": "teste123"
}

```

<h3> Clientes: </h3>

- GET - localhost:8080/api/clientes/{id} (Gets details of a specific customer)

- GET - localhost:8080/api/clientes (Filter by any property)
<p><strong> Example: <a href = "http://localhost:8080/api/clientes?cpf=123&nome=maria">
http://localhost:8080/api/clientes?cpf=123&nome=maria </a> </strong></p>

- POST - localhost:8080/api/clientes (Save a new customer)
<p><strong>Example:</strong></p>

```bash

{
  "cpf": "94666871071",
  "nome": "Maria"
}

```

- PUT - localhost:8080/api/clientes/{id} (update a specific customer)
<p><strong>Example:</strong></p>

```bash
{
  "cpf": "94666871071",
  "nome": "Maria"
}
```
- DELETE - localhost:8080/api/clientes/{id} (Delete a customer by id)

<h3> Produtos: </h3>

- GET - localhost:8080/api/produtos/{id} (Get details for a specific product)

- GET - localhost:8080/api/produtos (Filter by any property)
<p><strong> Example: <a href = "http://localhost:8080/api/produtos?descricao=mouse&preco=25">
http://localhost:8080/api/produtos?descricao=mouse&preco=25 </a></strong></p>

- POST - localhost:8080/api/produtos (Save a new product)
<p><strong>Example:</strong></p>

```bash

{
  "descricao": "Mouse",
  "preco": 40.00
}

```

- PUT - localhost:8080/api/produtos/{id} (update a specific product)

<p><strong>Example:</strong></p>

```bash
{
  "descricao": "Mouse",
  "preco": 40.00
}
```
- DELETE - localhost:8080/api/{id} (Delete a product by id)

<h3> Pedidos: </h3>

- GET - localhost:8080/api/pedidos/{id} (Gets details of a specific order)

- PATCH - localhost:8080/api/pedidos/{id} (Update order status)

- POST - localhost:8080/api/pedidos (Save a new order)

<p><strong>Example:</strong></p>

```bash

{
  "cliente": 1,
  "itens": [
    {
      "produto": 1,
      "quantidade": 5
    }
  ],
  "total": 100
}

```

<h4>Made with ❤️ by Yesmin Marie</h4>
