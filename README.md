# Spring-MVC


A arquitetura MVC (Model-View-Controller) é um padrão de arquitetura de software que divide uma aplicação em três componentes principais: o Modelo, a Visão e o Controlador. Essa divisão permite que cada componente seja desenvolvido, testado e mantido de forma independente, facilitando a manutenção do código. O Modelo é responsável pela lógica de negócios e pelos dados da aplicação, a Visão é responsável por apresentar os dados ao usuário e o Controlador é responsável pela interação entre o Modelo e a Visão. A arquitetura MVC é comumente utilizada em aplicações web, mas pode ser aplicada em outros tipos de aplicação também.

Pré-requisitos
Antes de executar o projeto, certifique-se de ter instalado o Java JDK 8 e o Apache Maven em sua máquina.

##Executando o projeto
Para executar o projeto, siga os seguintes passos:

### Clone o repositório para sua máquina local:
```git clone https://github.com/arthurqueiroz4/Spring-MVC.git```
### Navegue até a pasta raiz do projeto:
```cd Spring-MVC```
### Execute o comando Maven para compilar e empacotar o projeto:
```mvn package```
### Inicie o servidor embutido do Spring Boot:
```java -jar target/Spring-MVC-1.0.0.jar```
### Abra o navegador e digite a seguinte URL:
```http://localhost:8080/```
Você deverá ver a página inicial da aplicação web.

### Contribuindo
Se você quiser contribuir com este projeto, sinta-se à vontade para fazer um fork e enviar um pull request. Nós adoraríamos receber suas contribuições!

## Funções e telas:
Tela inicial (index):
![image](https://github.com/arthurqueiroz4/Spring-MVC/assets/110779984/d995765a-4630-471b-ac1a-7896674a47cc)

Tela de Login:
- Acessos => Para usar a Role ADMIN e USER, respectivamente:
  - login: admin | senha: 123
  - login: user | senha: 123
![image](https://github.com/arthurqueiroz4/Spring-MVC/assets/110779984/3066bd70-80c1-4c5f-a607-8d487e2ad5f0)
![image](https://github.com/arthurqueiroz4/Spring-MVC/assets/110779984/7d5d3c4e-740f-43e8-a0e3-1883ab0af7ef)

Tela principal:
- Obs: logado com admin. Somente admin pode fazer cadastro de Pessoa.
![image](https://github.com/arthurqueiroz4/Spring-MVC/assets/110779984/35c49708-ff92-4783-b6d3-ecd336f983bb)
![image](https://github.com/arthurqueiroz4/Spring-MVC/assets/110779984/b6b49229-2b20-45ba-a65f-dfed6c798d36)




