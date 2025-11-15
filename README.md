# Backend Monolítico - LevelUp Gamer 

Backend Spring Boot monolítico **simplificado** para la aplicación de e-commerce de gaming desarrollada con React.


## Características

- **Arquitectura Monolítica**: Toda la lógica de negocio en un solo proyecto
- **Spring Boot 3.5.7**: Framework principal
- **Spring Security**: Configurado en modo permisivo (todo público)
- **Spring Data JPA**: Acceso a datos con Hibernate
- **Swagger/OpenAPI**: Documentación automática de la API
- **CORS**: Configurado para el frontend React
- **DataFaker**: Datos de prueba realistas


##  Módulos Principales

### Entidades
- **Usuario**: Gestión de usuarios con roles y autenticación
- **Producto**: Catálogo de productos gaming
- **Categoria**: Organización de productos
- **Blog**: Sistema de blogs/noticias
- **Contacto**: Formulario de contacto
- **Pedido/DetallePedido**: Sistema de compras/carrito

### Endpoints Principales

#### Autenticación (`/api/auth`) 
- `POST /registro` - Registrar nuevo usuario (password sin encriptar)
- `POST /login` - Iniciar sesión (retorna token simple)

#### Productos (`/api/productos`) 
- `GET /` - Listar todos los productos
- `GET /destacados` - Productos destacados
- `GET /{id}` - Detalle de producto
- `GET /categoria/{id}` - Productos por categoría
- `GET /buscar?keyword=` - Buscar productos
- `POST /` - Crear producto
- `PUT /{id}` - Actualizar producto
- `DELETE /{id}` - Eliminar producto

#### Categorías (`/api/categorias`)
- `GET /` - Listar categorías
- `GET /{id}` - Detalle de categoría
- `POST /` - Crear categoría
- `PUT /{id}` - Actualizar categoría
- `DELETE /{id}` - Eliminar categoría

#### Pedidos (`/api/pedidos`) 
- `GET /` - Listar todos los pedidos
- `GET /{id}` - Detalle de pedido
- `GET /usuario/{id}` - Pedidos de un usuario
- `POST /` - Crear pedido
- `PATCH /{id}/estado` - Actualizar estado

#### Blogs (`/api/blogs`) 
- `GET /` - Blogs publicados
- `GET /all` - Todos los blogs
- `GET /{id}` - Detalle de blog
- `POST /` - Crear blog
- `PUT /{id}` - Actualizar blog
- `DELETE /{id}` - Eliminar blog

#### Contacto (`/api/contacto`) 
- `GET /` - Listar mensajes
- `GET /no-leidos` - Mensajes no leídos
- `POST /` - Enviar mensaje
- `PATCH /{id}/leido` - Marcar como leído

### Prerrequisitos
- Java 21
- Maven 3.6+



## 🔧 Configuración

### Base de Datos

**Desarrollo (H2):**
- URL: `jdbc:h2:mem:fullstackdb`
- Console: `http://localhost:8080/h2-console`
- Usuario: `sa`
- Password: (vacío)

**Producción (MySQL):**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fullstackdb
spring.datasource.username=root
spring.datasource.password=root
```

## 🔧 Instalación y Ejecución
Configurado para permitir:
- `http://localhost:5173` (Vite)
- `http://localhost:3000` (Create React App)

## 📚 Documentación API

Una vez ejecutada la aplicación:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Docs**: http://localhost:8080/api-docs

### CORS

Al iniciar la aplicación se crean automáticamente:

**Admin:**
- Email: `admin@test.com`
- Password: `admin123`
- Roles: ADMIN, USER

**Usuario:**
- Email: `user@test.com`
- Password: `user123`
- Roles: USER

## 📁 Estructura del Proyecto

```
backend/
├── src/main/java/com/levelup/backend/
│   ├── config/          # Configuraciones (LoadDatabase)
│   ├── controllers/     # Controladores REST
│   ├── dto/             # Data Transfer Objects
│   ├── exceptions/      # Excepciones personalizadas
│   ├── models/          # Entidades JPA
│   ├── repositories/    # Repositorios Spring Data
│   ├── security/        # Configuración de seguridad y JWT
│   ├── services/        # Lógica de negocio
│   └── DemoApplication.java
├── src/main/resources/
│   ├── application.properties
│   └── application-prod.properties
└── pom.xml
```

