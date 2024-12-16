# Sermaluc-api

**Sermaluc-API** es una API RESTful desarrollada con **Spring Boot 2.5**, **Maven 3.6** y **Java 11**. Proporciona un endpoint para la creación de usuarios con validación de correo electrónico y contraseñas configurables en el archivo de propiedades.

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalados los siguientes programas:

- **Java 11** o superior
- **Maven** 3.6 o superior
- **Git** para clonar el repositorio

# Instrucciones de Construcción y Ejecución

# 1. **Clonar el Repositorio:**
    git clone https://github.com/jimmyvilla7x/sermaluc-api.git

    Ejecutar comando: "mvn install" (para instalar dependencias del .pom)

# 2. **Levantar Proyecto**
    Opción 1: Para levantar de linea de comando se puede ejecutar lo sgte: "mvn spring-boot:run"

    Opción 2: Abrir con algún IDE y ejecutar el proyecto

# 3. **Probar Metódos**
    (Dejo Curl's para Importar directo en postman)

    ## Metódo /sign-up (Para crear usuario)

    curl --location --request POST 'http://localhost:8080/api/users/sign-up' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name": "Jimmy Villa",
    "email": "jimmy@gmail.com",
    "password": "Aa12345678",
    "phones": [
    {
    "countrycode": "56",
    "citycode": "9",
    "number": "99794330"
    }
    ]
    }'

    ## Metódo /users (Para listar usuarios creados)

    curl --location --request GET 'http://localhost:8080/api/users/' \
    --header 'accept: application/json'

# 4. Swagger
    En la siguiente URL se pueden ver los métodos: http://localhost:8080/swagger-ui.html

# 5. Base datos h2

    En la siguiente URL se pueden ver http://localhost:8080/h2-console/login.jsp
    Usuario: sa
    Password: 123456
    (***Accesos se pueden cambiar en application properties***)

# 5. Prubas Unitarias y Coverage

    Para Ejecutar pruebas unitarias ejecutar: "mvn clean test"

    Para Generar reporte de jacoco, seguir estos pasos:
    1.- Genera el reporte de cobertura con: "mvn jacoco:report"
    2.- Accede al reporte generado en la siguiente ruta: "target/site/jacoco/index.html"

# 6. Diagrama
                            +------------------+
                            |   Usuario API    |
                            |  (RESTful API)   |
                            +------------------+
                                   |
                                   | POST /api/sign-up
                                   v
                          +-----------------------+
                          |   Validaciones        |
                          | - Validar email       |
                          | - Validar contraseña  |
                          | - Comprobar existencia|
                          +-----------------------+
                                   |
                                   v
                          +-----------------------+
                          |  Base de Datos (DB)   |
                          |  - Usuarios           |
                          |  - Tokens             |
                          +-----------------------+
                                   |
                                   v
                       +---------------------------+
                       |  Generación de Tokens     |
                       |  (UUID o JWT)             |
                       +---------------------------+
                                   |
                                   v
                          +-----------------------+
                          |  Respuesta al cliente |
                          | - ID, Name, Email     |
                          | - Created, Modified   |
                          | - Token, IsActive     |
                          +-----------------------+



  

    