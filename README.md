# example-api-rest-hateoas-spring-sd

Ejemplo de una API RESTful que adhiere al principio HATEOAS, utilizando Spring.

> Trabajo realizado para la cátedra de Sistemas Distribuidos II.

## Configuración
Los datos de conexion a la base de datos se deben especificar con las siguientes variables de entorno.
```
spring.data.mongodb.uri=<string-de-conexion-a-la-base-de-datos>
spring.data.mongodb.database=<nombre-de-la-base-de-datos>
```
> Localmente se pueden agregar en `application.properties`
