# Bank API

API REST desarrollada con Spring Boot para la gestión de clientes, cuentas bancarias y movimientos (depósitos y retiros).

Proyecto creado como práctica de arquitectura backend con persistencia real usando JPA y base de datos en memoria H2.

---

## Stack tecnológico

- Java 17+
- Spring Boot 4
- Spring Web
- Spring Data JPA
- H2 (in-memory database)
- Maven (wrapper incluido)

---

## Arquitectura

El proyecto está organizado en capas:

- api → Controllers y DTOs
- storage.jpa → Entidades JPA y Repositories
- domain-core → Reglas de negocio

Buenas prácticas aplicadas:

- Separación de responsabilidades
- Uso de DTOs para respuestas
- Manejo global de excepciones con formato JSON uniforme
- Persistencia real con JPA
- Relaciones @OneToMany y @ManyToOne
- Ordenamiento en consultas
- Validaciones básicas de negocio

---

## Cómo ejecutar el proyecto

### Desde IntelliJ

Ejecutar la clase `BankApiApplication`.

### Desde terminal

```bash
cd bank-api
./mvnw spring-boot:run
```

O generar el JAR:

```bash
./mvnw clean package
java -jar target/bank-api-0.0.1-SNAPSHOT.jar
```

---

## Base de datos (H2)

Se utiliza H2 en memoria.

Acceso a la consola:

http://localhost:8080/h2-console

Configuración típica:

- JDBC URL: jdbc:h2:mem:testdb
- User: sa
- Password: (vacío)

---

## Endpoints principales

### Crear cliente

POST /api/v1/clients

```json
{
  "id": "1",
  "name": "Juan",
  "document": "123"
}
```

Status: 201 Created

---

### Crear cuenta

POST /api/v1/clients/{clientId}/accounts

```json
{
  "number": "ACC-100",
  "type": "AHORROS"
}
```

Status: 201 Created

---

### Depositar dinero

POST /api/v1/accounts/{number}/deposit

```json
{
  "amount": 5000
}
```

Status: 200 OK

---

### Retirar dinero

POST /api/v1/accounts/{number}/withdraw

```json
{
  "amount": 2000
}
```

Si el saldo es insuficiente:

```json
{
  "code": "INSUFFICIENT_FUNDS",
  "message": "Saldo insuficiente"
}
```

Status: 409 Conflict

---

### Listar movimientos

GET /api/v1/accounts/{number}/movements

Ejemplo de respuesta:

```json
[
  {
    "occurredAt": "2026-02-24T10:00:00Z",
    "type": "RETIRO",
    "amount": 2000,
    "resultingBalance": 3000
  },
  {
    "occurredAt": "2026-02-24T09:59:00Z",
    "type": "DEPOSITO",
    "amount": 5000,
    "resultingBalance": 5000
  }
]
```

Los movimientos se devuelven ordenados del más reciente al más antiguo.

---

## Ejecutar pruebas

```bash
./mvnw clean test
```

---

## Próximas mejoras

- Implementar seguridad con JWT
- Agregar Docker
- Migrar a PostgreSQL
- Implementar paginación
- Agregar documentación OpenAPI
- Integrar frontend (Angular / React)
- Agregar validaciones con Bean Validation

---

## Autor

Juan Camilo Ramírez  
Proyecto de práctica backend — 2026