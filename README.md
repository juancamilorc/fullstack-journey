# Bank API (Spring Boot + JPA + H2)

API simple de banco para practicar:
- CRUD básico de clientes
- Cuentas por cliente
- Depósitos y retiros
- Registro de movimientos (DEPOSITO / RETIRO)
- Manejo global de errores (JSON uniforme)

## Stack
- Java 17+
- Spring Boot 4
- Spring Web
- Spring Data JPA
- H2 (in-memory)
- Maven (wrapper)

---

## Cómo ejecutar

### Opción A: Desde IntelliJ
Run -> `BankApiApplication`

### Opción B: Terminal
```bash
cd bank-api
./mvnw spring-boot:run


# 🏦 Bank API

API REST desarrollada con Spring Boot para la gestión de clientes, cuentas bancarias y movimientos (depósitos y retiros).

Proyecto creado como práctica de arquitectura backend con persistencia real usando JPA y base de datos en memoria H2.

---

## 🚀 Tecnologías utilizadas

- Java 17
- Spring Boot 4
- Spring Web
- Spring Data JPA
- H2 Database
- Maven

---

## 🧱 Arquitectura

El proyecto sigue una estructura en capas:

api → Controllers + DTOs  
storage.jpa → Entidades JPA + Repositories  
domain-core → Reglas de negocio  

Buenas prácticas implementadas:

- Separación de responsabilidades  
- DTOs para respuestas  
- Manejo global de excepciones  
- Persistencia real con base de datos  
- Relaciones @OneToMany y @ManyToOne  
- Ordenamiento en consultas  
- Validaciones básicas de negocio  

---

## 🗄️ Base de datos

Se utiliza H2 en memoria.

Acceso a consola H2:

http://localhost:8080/h2-console

Configuración típica:

JDBC URL: jdbc:h2:mem:testdb  
User: sa  
Password: (vacío)  

---

## 📌 Endpoints principales

### 👤 Crear cliente

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

Respuesta:

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

## Cómo ejecutar el proyecto

Desde la carpeta bank-api:

```bash
./mvnw spring-boot:run
```

O:

```bash
./mvnw clean package
java -jar target/bank-api-0.0.1-SNAPSHOT.jar
```

---

## Ejecutar pruebas

```bash
./mvnw clean test
```

---

## Objetivos del proyecto

- Practicar arquitectura backend limpia
- Implementar persistencia con JPA
- Manejar relaciones entre entidades
- Aplicar manejo global de excepciones
- Construir una API REST funcional end-to-end

---

## Próximas mejoras

- Implementar seguridad con JWT
- Agregar Docker
- Migrar a PostgreSQL
- Implementar paginación
- Agregar Swagger / OpenAPI
- Integrar frontend en Angular o React
- Agregar validaciones con Bean Validation

---

## Autor

Juan Camilo Ramírez  
Proyecto de práctica backend — 2026