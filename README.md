# Implementación de un servicio Rest en Spring Framework con algunos clientes.

<br>

Implementación de un servicio Rest sencillo en SpringFramework desplegado en contenedores Docker.

El objetivo principal de este proyecto es poner en práctica varios de los conocimientos que he ido adquiriendo durante los últimos meses, desde trabajar con Spring framework, Spring Security, Spring Data, pasando además por TLS con certificados autofirmados, utilización de tokens JWT para la autentificación sin estado, habilitación de HTTP/2 como protocolo principal  y hasta llegar con el despliegue de contenedores utilizando docker.  

Ahora bien, hablando sobre la dinámica del sistema, tenemos un pequeño servicio web que nos permite almacenar contactos de usuario y recuperarlos en cualquier momento, todo a través de una interfaz Rest API. 

Actualmente el proyecto cuenta solamente con una implementación “completa” de un cliente para este servicio Rest, el cual es una aplicación móvil desarrollada nativamente para la plataforma Android. El objetivo es desarrollar al menos otro cliente el cual consistiría en una implementación de escritorio utilizando JavaFX para el desarrollo de esta, por el momento quedará pendiente en un futuro no muy lejano. 


<br>

 ![System diagram](documentation/diagram.svg)

> Diagrama que muestra una vista completa del sistema interactuando con algunos clientes.

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

## Instrucciones para el despliegue del proyecto

Dentro de la carpeta del proyecto “WebService” se encuentra un script de bash ```desploy-docker.sh``` que viene con lo necesario para desplegar completamente el sistema, desde la compilación y empaquetado del proyecto hasta el despliegue de los contenedores que contiene el servicio web y la base de datos.

Lo único que se tiene que hacer es ejecutar los siguientes comandos sobre el script ```desploy-docker.sh``` en orden en un entorno bash: 

- Despliegue del contenedor de la base de datos MariaDB

```bash
./desploy-docker.sh database
```

- Despliegue del contenedor del servicio web

```bash
./desploy-docker.sh webservice
```

> Nota 1: Posiblemente sea necesario cambiar las propiedades del script para poder ejecutarlo, para esto tiene que hacer lo siguiente: chmod +x desploy-docker.sh

> Nota 2: Posiblemente también sea necesario cambiar los permisos del socket generado por docker para poder trabajar directamente con un usuario que no sea root, para eso tiene que hacer lo siguiente : sudo chmod 777 /var/run/docker.sock


<br>
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

<br>
<br>

## Implementación de cliente Android

Implementación de un cliente sencillo desarrollado nativamente para la plataforma Android, la aplicación ha sido desarrollada utilizando kotlin como lenguaje de programación y un nivel de API 23 (Android 6.0) como versión mínima. 

El objetivo de la implementación de este cliente es solamente demostrativo, teniendo como objetivo principal mostrar el funcionamiento correcto del sistema Rest Api implementado.

La aplicación no maneja ningún tipo de persistencia local, esto quiere decir que toda la información mostrada junto con las imagenes son cargadas a través de la red utilizando el servicio Rest y permaneciendo en memoria durante el tiempo de ejecución, debido a esto, la aplicación puede presentar algunos retardos al momento de guardar o cargar información de los contactos.   

<br>

 ![System diagram](documentation/register.png)

> Pantalla de inicio de sesión de la aplicación (1.a), pantalla donde se puede registrar una nueva cuenta (1.b) y registro de nuevos contactos (1.c). 

 <br>

  ![System diagram](documentation/contacts.png)

  > Pantalla que lista todos los contactos registrados en la cuenta del usuario (2.a), pantalla que nos muestra más detalles acerca del contacto y acciones que se pueden realizar (2.b), la última pantalla nos muestra el resultado de haber presionado el botón para marcar (2.c). 
