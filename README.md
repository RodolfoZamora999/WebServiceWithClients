# Implementación de un servicio Rest en Spring Framework con algunos clientes.

<br>

Implementación de un servicio Rest sencillo en SpringFramework desplegado en contenedores Docker.

El objetivo principal de este proyecto es poner en práctica varios de los conocimientos que he ido adquiriendo durante los últimos meses, desde trabajar con Spring framework, Spring Security, Spring Data, pasando además por TLS con certificados autofirmados, utilización de tokens JWT para la autentificación sin estado, habilitación de HTTP/2 como protocolo principal  y hasta llegar con el despliegue de contenedores utilizando docker.  

Ahora bien, hablando sobre la dinámica del sistema, tenemos un pequeño servicio web que nos permite almacenar contactos de usuario y recuperarlos en cualquier momento, todo a través de una interfaz Rest API. 

Actualmente el proyecto cuenta solamente con una implementación “completa” de un cliente para este servicio Rest, el cual es una aplicación móvil desarrollada nativamente para la plataforma Android. El objetivo es desarrollar al menos otro cliente el cual consistiría en una implementación de escritorio utilizando JavaFX para el desarrollo de esta, por el momento quedará pendiente en un futuro no muy lejano. 


<br>

 ![System diagram](/documentation/diagram.png)

<br>

## Características del proyecto:

- Java 17
- Spring Framework (Spring Boot, Spring Security, Spring JPA)
- MariaDB como gestor de base de datos
- Uso de perfiles de Spring para el despliegue (Desarrollo y producción)
- Despliegue en contenedores Docker
- HTTPS (Certificados autofirmados)
- HTTP/2 habilitado
- JWT como método de autentificación

<br>

## Requisitos para el despliegue:
- Java 17
- Maven
- Docker
- Acceso a internet

<br>

## Puntos finales

### api/users [GET, POST, PUT, DELETE]

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

### api/users/{id-user}/contacts [GET, POST, PUT, DELETE]

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

### api/login [POST]

**api/login POST**

```consume```
```json
{
  "id":"fulanito@email.com",
  "password":"1234"
}
```

```response```
```json
{
  "user": "1",
  "type": "bearer",
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTY1MTkwNDUyM30.sZTGwYnITRNgvn9cNKRSdF3oc-S2N4bsNrf6pTuoCBM"
}
```

<br>
<br>

### api/images [GET, POST]

**api/images/{id-image}**

```response```

image/jpeg, image/png

<br>

**api/images POST**

```consume```

image/jpeg, image/png

```response```
```json
{
  "path": "api/images/IMG_1651300445323_17534.jpeg"
}
```

