# 261CC341ASpringSigconBackend — LogiControl

Proyecto base entregado por la profesora + **HU01 (Autenticación y RBAC)** integrada.

---

## 📂 Estructura del proyecto

```
261CC341ASpringSigconBackend/
├── pom.xml                       ← Maven (Spring Boot 4.0.7, Java 21, + Security, JWT, Validation)
├── HELP.md                       ← Documento original del profesor
├── pruebas-login.http            ← Archivo de pruebas para HTTP Client de IntelliJ
├── mvnw, mvnw.cmd, .mvn/         ← Maven wrapper (del profesor)
└── src/
    ├── main/
    │   ├── java/com/_CC341ASpringSigconBackend/
    │   │   ├── Application.java                    ← Entry point del profesor (no se toca)
    │   │   │
    │   │   ├── auth/                               ← HU01 - Módulo de autenticación
    │   │   │   ├── controller/
    │   │   │   │   └── AuthController.java         ← REST: /api/auth/login, /registro, /perfil
    │   │   │   ├── dto/
    │   │   │   │   └── AuthDto.java                ← LoginRequest, RegistroRequest, AuthResponse
    │   │   │   ├── model/
    │   │   │   │   ├── Usuario.java                ← Entidad JPA (tabla "usuarios")
    │   │   │   │   └── Rol.java                    ← Enum con los 7 roles del sistema
    │   │   │   ├── repository/
    │   │   │   │   └── UsuarioRepository.java      ← Spring Data JPA
    │   │   │   ├── security/
    │   │   │   │   ├── JwtService.java             ← Generar/validar tokens JWT
    │   │   │   │   └── JwtAuthFilter.java          ← Filtro que intercepta cada petición
    │   │   │   ├── service/
    │   │   │   │   ├── IAuthService.java           ← Interfaz (SOLID-DIP)
    │   │   │   │   └── AuthServiceImpl.java        ← Lógica de login/registro
    │   │   │   └── pattern/
    │   │   │       ├── LoginEventPublisher.java    ← Patrón GoF: Observer
    │   │   │       └── RolPermisoRegistry.java     ← Patrón GoF: Singleton
    │   │   │
    │   │   ├── config/                             ← Configuración global
    │   │   │   ├── SecurityConfig.java             ← Reglas de seguridad por rol
    │   │   │   ├── GlobalExceptionHandler.java     ← Manejo centralizado de errores
    │   │   │   └── DataInitializer.java            ← Crea admin@xyz.com al arrancar
    │   │   │
    │   │   └── analisis/                           ← Tus clases BCE académicas (CC_, T_, UI_)
    │   │       ├── compras/                        ← Negociación, Cotización, OC, Recepción
    │   │       ├── pagos/                          ← Obligación de pago, Pago
    │   │       └── contabilidad/                   ← Asientos contables
    │   │
    │   └── resources/
    │       └── application.properties              ← PostgreSQL + JWT config
    └── test/
        └── java/com/_CC341ASpringSigconBackend/
            └── ApplicationTests.java               ← Test del profesor (no se toca)
```

---

## 🚀 Cómo ejecutarlo

### 1. Crea la base de datos en PostgreSQL

Abre pgAdmin o psql y ejecuta:

```sql
CREATE DATABASE logicontrol_db;
```

### 2. Configura tu contraseña de PostgreSQL

Edita `src/main/resources/application.properties` y cambia:

```properties
spring.datasource.password=tu_password_aqui
```

Por tu contraseña real de PostgreSQL.

### 3. Abre el proyecto en IntelliJ IDEA

`File → Open` → selecciona la carpeta `261CC341ASpringSigconBackend` (la que tiene `pom.xml`).
IntelliJ descargará las dependencias automáticamente (1–3 minutos la primera vez).

### 4. Habilita Annotation Processing (para Lombok)

`File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors`
→ Marca **Enable annotation processing** → Apply → OK.

Si Lombok te marca cosas en rojo: instala el plugin de Lombok en `Settings → Plugins`.

### 5. Ejecuta

Abre `Application.java` y haz clic en la flechita verde ▶ al lado del método `main`.

Verás en consola:

```
============================================
  USUARIO ADMIN CREADO
  Email   : admin@xyz.com
  Password: Admin1234!
============================================
Tomcat started on port 8080
Started Application in X seconds
```

---

## 🧪 Cómo probarlo

### Opción A: Archivo `pruebas-login.http` (más fácil, ya viene incluido)

Abre `pruebas-login.http` en IntelliJ y haz clic en la flecha verde ▶ al lado de cada bloque.

### Opción B: Swagger UI

Abre en el navegador: **http://localhost:8080/swagger-ui.html**

Allí puedes ver todos los endpoints y probarlos visualmente.

### Opción C: Postman

```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "admin@xyz.com",
  "password": "Admin1234!"
}
```

Respuesta esperada:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "admin@xyz.com",
  "nombre": "Administrador LogiControl",
  "rol": "ADMIN",
  "mensaje": "Bienvenido, Administrador LogiControl"
}
```

---

## 🎓 Cómo presentarlo a tu profesora

### Patrones GoF aplicados

| Patrón | Clase | Función |
|---|---|---|
| **Facade** | `IAuthService` / `AuthServiceImpl` | Oculta Spring Security + BCrypt + JWT tras 3 métodos simples |
| **Strategy** | `JwtService` | Encapsula el algoritmo de firma JWT (puede cambiar de HS256 a RS256 sin tocar nada más) |
| **Chain of Responsibility** | `JwtAuthFilter` | Forma parte de la cadena de filtros de Spring Security |
| **Observer** | `LoginEventPublisher` | Notifica login exitoso/fallido sin acoplar la lógica al servicio |
| **Singleton** | `RolPermisoRegistry` | Una sola instancia gestiona el mapa de permisos por rol |
| **Builder** | `Usuario` (`@Builder` de Lombok) | Construcción fluida sin constructores de 7 parámetros |
| **Template Method** | `GlobalExceptionHandler` | Define el algoritmo general de respuesta de error; cada `@ExceptionHandler` es un paso |

### Principios SOLID aplicados

- **SRP** (Single Responsibility): cada clase tiene una sola razón para cambiar
  → ej. `JwtService` solo se ocupa de JWT, no de login
- **OCP** (Open/Closed): para añadir un nuevo observador de login basta crear una nueva clase
- **LSP** (Liskov): `AuthServiceImpl` puede reemplazar a `IAuthService` sin romper nada
- **ISP** (Interface Segregation): `IAuthService` solo declara los métodos necesarios para HU01
- **DIP** (Dependency Inversion): el controlador depende de `IAuthService` (interfaz), no de la implementación

### Flujo de HU01

1. El usuario envía `email + password` a `POST /api/auth/login`
2. `AuthController` delega en `IAuthService` (Facade)
3. Spring Security valida las credenciales contra la BD (usando `BCryptPasswordEncoder`)
4. `JwtService` genera un token JWT con el rol embebido
5. El frontend guarda el token y lo envía en cada petición como `Authorization: Bearer ...`
6. `JwtAuthFilter` valida el token en cada petición
7. `SecurityConfig` decide si el rol del usuario tiene permiso para esa ruta

---

## ⚠️ Solución de problemas comunes

| Error | Causa | Solución |
|---|---|---|
| `Connection refused: localhost:5432` | PostgreSQL apagado | Abre pgAdmin o arranca el servicio `postgresql` en Windows |
| `password authentication failed` | Contraseña mal en `application.properties` | Revisa la línea `spring.datasource.password` |
| `database "logicontrol_db" does not exist` | No creaste la BD | `CREATE DATABASE logicontrol_db;` en pgAdmin |
| Cosas en rojo (`getNombre()` no existe, etc.) | Lombok no habilitado | Habilita Annotation Processing en Settings |
| `Port 8080 already in use` | Otra app usa el puerto | Cambia `server.port=8081` en `application.properties` |
| `Cannot resolve symbol Jwts` | Maven no descargó | `mvn clean install` o "Reload Maven Project" en IntelliJ |

---

## 📌 Próximos pasos

Las siguientes HU del backlog (HU02 en adelante) se construyen encima de esta base. Cada nueva HU añadirá:

- Una entidad JPA en su propio paquete (ej. `compras/model/Proveedor.java`)
- Un repositorio
- Un servicio
- Un controlador REST
- Las reglas de RBAC ya están definidas en `SecurityConfig.java`
