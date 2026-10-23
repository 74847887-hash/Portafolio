# Andes Llantas Backend

Backend REST con Spring Boot para la pagina publica y preparado para un futuro panel de administracion.

## Requisitos

- Java 17
- Maven 3.9+
- PostgreSQL 14+ para el entorno real

## Ejecutar sin PostgreSQL

Usa el perfil `dev`, que levanta una base H2 en memoria con datos iniciales:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

API:

```text
http://localhost:8080
```

## Ejecutar con PostgreSQL

Crea una base llamada `andes_llantas` y configura credenciales si son distintas:

```bash
set SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/andes_llantas
set SPRING_DATASOURCE_USERNAME=postgres
set SPRING_DATASOURCE_PASSWORD=postgres
mvn spring-boot:run
```

## Endpoints publicos

- `GET /productos`
- `GET /productos/{slug}`
- `GET /productos/buscar?q=texto`
- `GET /marcas`
- `GET /categorias`
- `GET /promociones/activas`
- `POST /contacto`

Tambien existe `GET /products` como alias compatible con el frontend Nuxt actual.
