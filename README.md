# Implementación de un servicio Rest en Spring Framework con algunos clientes.

<br>

Implementación de un servicio Rest sencillo en SpringFramework desplegado en contenedores Docker.

Cuenta con algunas implementaciones de clientes para la API, como lo es un cliente en Android y un cliente gráfico de escritorio desarrollado en JavaFX.

<br>

### Características del proyecto:

- Java 17
- Spring Framework (Spring Boot, Spring Security, Spring JPA)
- MariaDB como gestor de base de datos
- Uso de perfiles de Spring para el despliegue (Desarrollo y producción)
- Despliegue en contenedores Docker
- HTTPS (Certificados autofirmados)
- HTTP/2 habilitado
- JWT como método de autentificación

<br>

### Requisitos para el despliegue:
- Java 17
- Maven
- Docker
- Acceso a internet

<br>

### Puntos finales

**api/users [GET, POST, PUT, DELETE]**

**api/users GET**

```response```
```json
[
  {
    "name":"Fulanito",
    "lastName":"De tal",
    "email":"myemailo@email.com",
    "password":"1234",
    "imageProfile":"api/images/IMG_05042022105"
  },
  {
    "name":"Fulanito",
    "lastName":"De tal",
    "email":"myemailo@email.com",
    "password":"1234",
    "imageProfile":"api/images/IMG_05042022105"
  }
]
```

<br>

**api/users POST**

```consume```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "email":"myemailo@email.com",
  "password":"1234",
  "imageProfile":"api/images/IMG_05042022105"
}
```

```response```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "email":"myemailo@email.com",
  "password":"1234",
  "imageProfile":"api/images/IMG_05042022105"
}
```

<br>

**api/users PUT**

```consume```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "email":"myemailo@email.com",
  "password":"1234",
  "imageProfile":"api/images/IMG_05042022105"
}
```

```response```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "email":"myemailo@email.com",
  "password":"1234",
  "imageProfile":"api/images/IMG_05042022105"
}
```

<br>

**api/users/{id-user} DELETE**

```consume```

nothing

```response```

nothing

<br>
<br>

**api/users/{id-user}/contacts [GET, POST, PUT, DELETE]**

**api/users/{id-user}/contacts GET**

```response```
```json
[
  {
    "name":"Fulanito",
    "lastName":"De tal",
    "phoneNumber":"0123456789",
    "email":"fulanito@email.com",
    "imageProfile":"api/images/IMG_05042022105"
  },
  {
    "name":"Manganito",
    "lastName":"SinApellido",
    "phoneNumber":"9874632014",
    "email":"manganito@email.com",
    "imageProfile":"api/images/IMG_04042122106"
  }
]
```

<br>

**api/users/{id-user}/contacts/{id-contact} GET**

```response```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "phoneNumber":"0123456789",
  "email":"some@email.com",
  "imageProfile":"api/images/IMG_05042022105"
}
```

<br>

**api/users/{id-user}/contacts POST**

```consume```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "phoneNumber":"0123456789",
  "email":"some@email.com",
  "imageProfile":"api/images/IMG_05042022105"
}
```

```response```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "phoneNumber":"0123456789",
  "email":"some@email.com",
  "imageProfile":"api/images/IMG_05042022105"
}
```

<br>

**api/users/{id-user}/contacts PUT**

```consume```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "phoneNumber":"0123456789",
  "email":"some@email.com",
  "imageProfile":"api/images/IMG_05042022105"
}
```

```response```
```json
{
  "name":"Fulanito",
  "lastName":"De tal",
  "phoneNumber":"0123456789",
  "email":"some@email.com",
  "imageProfile":"api/images/IMG_05042022105"
}
```

<br>

**api/users/{id-user}/contacts/{id-contact} DELETE**

```consume```

nothing

```response```

nothing

<br>
<br>

- api/images [GET, POST]


- api/login [POST]

```json
{
  "id":"myemail@email.com",
  "password":"my-password"
}
```
