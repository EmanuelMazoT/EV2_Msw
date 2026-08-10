# Ev2_Msw / evaluaciont2

Evaluación: API REST CRUD de proveedores con Spring Boot, Spring Data JPA y PostgreSQL.

## Tecnologías

- Java 25
- Spring Boot 3.5.0
- Spring Data JPA / Hibernate
- PostgreSQL (alojada en Render)
- Maven
- REST API

## Descripción

`Ev2_Msw` expone un API REST con CRUD completo de `proveedor`. La aplicación lee
las credenciales de la base de datos exclusivamente desde variables de entorno.

La base de datos PostgreSQL está alojada en Render y ya existe; este proyecto solo
se conecta a ella.

## Endpoints

| Método | Ruta                      | Descripción                    |
|--------|---------------------------|--------------------------------|
| GET    | `/api/proveedores`        | Lista todos los proveedores    |
| GET    | `/api/proveedores/{id}`   | Obtiene un proveedor por id    |
| POST   | `/api/proveedores`        | Crea un proveedor              |
| PUT    | `/api/proveedores/{id}`   | Actualiza un proveedor         |
| DELETE | `/api/proveedores/{id}`   | Elimina un proveedor           |

## Variables de entorno

La aplicación requiere las siguientes variables (sin valores aquí a propósito):

```
DB_URL=
DB_USERNAME=
DB_PASSWORD=
```

- `DB_URL`: cadena JDBC completa, p. ej. `jdbc:postgresql://HOST:5432/DATABASE?sslmode=require`
- `DB_USERNAME`: usuario de la base de datos
- `DB_PASSWORD`: contraseña de la base de datos

`server.port` se toma de la variable `PORT` (usada por Render), con fallback a `8080`.

> Las credenciales nunca deben escribirse en archivos del repositorio. Ve `.env.example`.

## Compilar

```
./mvnw clean package -DskipTests
```

## Ejecutar localmente

Define antes las variables de entorno `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` y luego:

```
java -jar target/evaluaciont2-0.0.1-SNAPSHOT.jar
```

## Despliegue en Render

Este proyecto usa Java, por lo que Render requiere despliegue con Docker:

1. Crea un **Web Service** en Render conectado a este repositorio.
2. En **Environment**: selecciona **Docker**.
3. Añade las variables de entorno:
   - `DB_URL`
   - `DB_USERNAME`
   - `DB_PASSWORD`
4. Render asigna automáticamente `PORT`; la app usa `server.port=${PORT:8080}`.
5. Deploy.

### URL API Render

`PENDIENTE_DE_COLOCAR`

(Este valor debe reemplazarse por la URL pública que Render asigne al Web Service.)