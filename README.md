# Backend Monolítico - LevelUp Gamer (Proyecto Universitario)

Backend Spring Boot monolítico **simplificado** para la aplicación de e-commerce de gaming desarrollada con React.

> **Nota**: Este es un proyecto universitario con seguridad simplificada. Todos los endpoints son públicos y las contraseñas no están encriptadas para facilitar el desarrollo y las pruebas.

## 🚀 Características

- **Arquitectura Monolítica**: Toda la lógica de negocio en un solo proyecto
- **Spring Boot 3.5.7**: Framework principal
- **Spring Security**: Configurado en modo permisivo (todo público)
- **Spring Data JPA**: Acceso a datos con Hibernate
- **H2 Database**: Base de datos en memoria para desarrollo
- **MySQL**: Base de datos para producción (opcional)
- **Swagger/OpenAPI**: Documentación automática de la API
- **CORS**: Configurado para el frontend React
- **DataFaker**: Datos de prueba realistas

## ⚡ Simplificaciones para Proyecto Universitario

- ✅ **Todos los endpoints son públicos** - No necesitas token para ninguna operación
- ✅ **Contraseñas sin encriptar** - Guardadas en texto plano para facilidad
- ✅ **Sin JWT complejo** - Token simple tipo "simple-token-1"
- ✅ **Sin autenticación obligatoria** - Puedes usar cualquier endpoint libremente
- ✅ **Ideal para demos y presentaciones** - Sin complicaciones de seguridad

## 📦 Módulos Principales

### Entidades
- **Usuario**: Gestión de usuarios con roles y autenticación
- **Producto**: Catálogo de productos gaming
- **Categoria**: Organización de productos
- **Blog**: Sistema de blogs/noticias
- **Contacto**: Formulario de contacto
- **Pedido/DetallePedido**: Sistema de compras/carrito

### Endpoints Principales

#### Autenticación (`/api/auth`) - **PÚBLICO**
- `POST /registro` - Registrar nuevo usuario (password sin encriptar)
- `POST /login` - Iniciar sesión (retorna token simple)

#### Productos (`/api/productos`) - **PÚBLICO**
- `GET /` - Listar todos los productos
- `GET /destacados` - Productos destacados
- `GET /{id}` - Detalle de producto
- `GET /categoria/{id}` - Productos por categoría
- `GET /buscar?keyword=` - Buscar productos
- `POST /` - Crear producto
- `PUT /{id}` - Actualizar producto
- `DELETE /{id}` - Eliminar producto

#### Categorías (`/api/categorias`) - **PÚBLICO**
- `GET /` - Listar categorías
- `GET /{id}` - Detalle de categoría
- `POST /` - Crear categoría
- `PUT /{id}` - Actualizar categoría
- `DELETE /{id}` - Eliminar categoría

#### Pedidos (`/api/pedidos`) - **PÚBLICO**
- `GET /` - Listar todos los pedidos
- `GET /{id}` - Detalle de pedido
- `GET /usuario/{id}` - Pedidos de un usuario
- `POST /` - Crear pedido
- `PATCH /{id}/estado` - Actualizar estado

#### Blogs (`/api/blogs`) - **PÚBLICO**
- `GET /` - Blogs publicados
- `GET /all` - Todos los blogs
- `GET /{id}` - Detalle de blog
- `POST /` - Crear blog
- `PUT /{id}` - Actualizar blog
- `DELETE /{id}` - Eliminar blog

#### Contacto (`/api/contacto`) - **PÚBLICO**
- `GET /` - Listar mensajes
- `GET /no-leidos` - Mensajes no leídos
- `POST /` - Enviar mensaje
- `PATCH /{id}/leido` - Marcar como leído

## � Seguridad Simplificada

Este proyecto usa **seguridad básica** apropiada para entornos universitarios:

- ✅ Todos los endpoints son **públicos** (no requieren autenticación)
- ✅ Las contraseñas se guardan **sin encriptar**
- ✅ El login retorna un token simple: `simple-token-1`
- ✅ No necesitas enviar headers de Authorization

### ⚠️ Advertencia
Este nivel de seguridad es **SOLO PARA PROYECTOS EDUCATIVOS**. En producción real deberías:
- Encriptar contraseñas con BCrypt
- Usar JWT real con firmas
- Requerir autenticación para endpoints sensibles
- Implementar HTTPS

### Prerrequisitos
- Java 21
- Maven 3.6+

### Ejecutar en Desarrollo

```bash
# Desde la carpeta backend/
mvn spring-boot:run
```

El servidor se iniciará en `http://localhost:8080`

### Compilar para Producción

```bash
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

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

## 🔄 Integración con React (Simplificada)

El backend está configurado para trabajar con el frontend React sin complicaciones de autenticación.

### Uso Básico (Sin autenticación necesaria)

```javascript
// ✅ Todos los endpoints funcionan sin token
const productos = await fetch('http://localhost:8080/api/productos');
const categorias = await fetch('http://localhost:8080/api/categorias');
const blogs = await fetch('http://localhost:8080/api/blogs');

// ✅ Crear un pedido (tampoco requiere autenticación)
const pedido = await fetch('http://localhost:8080/api/pedidos', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    usuarioId: 1,
    direccionEnvio: 'Mi dirección',
    items: [
      { productoId: 1, cantidad: 2, precioUnitario: 799.99 }
    ]
  })
});
```

### Login Simplificado (Opcional)

```javascript
// Login (retorna token simple)
const response = await fetch('http://localhost:8080/api/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ 
    email: 'user@test.com', 
    password: 'user123'  // Password sin encriptar
  })
});

const data = await response.json();
// data = { 
//   token: "simple-token-2", 
//   id: 2, 
//   nombre: "Test Usuario",
//   email: "user@test.com"
// }
```

## 🧪 Datos de Prueba

El sistema carga automáticamente:
- 7 usuarios (incluyendo admin y user)
- 6 categorías de productos gaming
- 35+ productos
- 10 blogs
- 10 mensajes de contacto

## 🤝 Contribución

Este backend está diseñado para ser fácilmente extensible. Para agregar nuevas funcionalidades:

1. Crear la entidad en `models/`
2. Crear el repository en `repositories/`
3. Crear el service en `services/`
4. Crear el controller en `controllers/`
5. Agregar datos de prueba en `config/LoadDatabase.java`

## 📄 Licencia

Proyecto educativo para Level Up Gamer.
