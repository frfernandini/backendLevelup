package com.levelup.backend.config;

import com.levelup.backend.models.*;
import com.levelup.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class LoadDatabase {
    
    // ✅ Ya no necesitamos PasswordEncoder - simplificado
    private final Faker faker = new Faker();
    
    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository,
            BlogRepository blogRepository,
            ContactoRepository contactoRepository) {
        
        return args -> {
            log.info("Iniciando carga de datos de prueba...");
            
            // Crear usuarios
            List<Usuario> usuarios = crearUsuarios(usuarioRepository);
            log.info("✓ {} usuarios creados", usuarios.size());
            
            // Crear categorías
            List<Categoria> categorias = crearCategorias(categoriaRepository);
            log.info("✓ {} categorías creadas", categorias.size());
            
            // Crear productos
            List<Producto> productos = crearProductos(productoRepository, categorias);
            log.info("✓ {} productos creados", productos.size());
            
            // Crear blogs
            List<Blog> blogs = crearBlogs(blogRepository);
            log.info("✓ {} blogs creados", blogs.size());
            
            // Crear contactos
            List<Contacto> contactos = crearContactos(contactoRepository);
            log.info("✓ {} mensajes de contacto creados", contactos.size());
            
            log.info("========================================");
            log.info("Datos de prueba cargados exitosamente!");
            log.info("========================================");
            log.info("Usuario Admin - Email: admin@test.com | Password: admin123");
            log.info("Usuario Test  - Email: user@test.com  | Password: user123");
            log.info("H2 Console: http://localhost:8080/h2-console");
            log.info("Swagger UI: http://localhost:8080/swagger-ui.html");
            log.info("========================================");
        };
    }
    
    private List<Usuario> crearUsuarios(UsuarioRepository repository) {
        List<Usuario> usuarios = new ArrayList<>();
        
        // Usuario Admin - Password sin encriptar (simple)
        Usuario admin = new Usuario();
        admin.setNombre("Admin Usuario");
        admin.setEmail("admin@test.com");
        admin.setPassword("admin123");  // ✅ Sin encriptar
        admin.setTelefono("555-0001");
        admin.setDireccion("Calle Principal 123");
        Set<String> adminRoles = new HashSet<>();
        adminRoles.add("ADMIN");
        adminRoles.add("USER");
        admin.setRoles(adminRoles);
        admin.setActivo(true);
        usuarios.add(repository.save(admin));
        
        // Usuario normal - Password sin encriptar (simple)
        Usuario user = new Usuario();
        user.setNombre("Test Usuario");
        user.setEmail("user@test.com");
        user.setPassword("user123");  // ✅ Sin encriptar
        user.setTelefono("555-0002");
        user.setDireccion("Avenida Secundaria 456");
        Set<String> userRoles = new HashSet<>();
        userRoles.add("USER");
        user.setRoles(userRoles);
        user.setActivo(true);
        usuarios.add(repository.save(user));
        
        // Usuarios adicionales - Password sin encriptar (simple)
        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setPassword("password123");  // ✅ Sin encriptar
            usuario.setTelefono(faker.phoneNumber().cellPhone());
            usuario.setDireccion(faker.address().fullAddress());
            Set<String> roles = new HashSet<>();
            roles.add("USER");
            usuario.setRoles(roles);
            usuario.setActivo(true);
            usuarios.add(repository.save(usuario));
        }
        
        return usuarios;
    }
    
    private List<Categoria> crearCategorias(CategoriaRepository repository) {
        List<Categoria> categorias = new ArrayList<>();
        String[] nombres = {"Consolas", "Videojuegos", "Accesorios", "PC Gaming", "Periféricos", "Coleccionables"};
        
        for (String nombre : nombres) {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoria.setDescripcion("Categoría de " + nombre.toLowerCase());
            categoria.setImagen("/images/categorias/" + nombre.toLowerCase() + ".jpg");
            categoria.setActivo(true);
            categorias.add(repository.save(categoria));
        }
        
        return categorias;
    }
    
    private List<Producto> crearProductos(ProductoRepository repository, List<Categoria> categorias) {
        List<Producto> productos = new ArrayList<>();
        
        // Productos específicos de gaming
        String[][] productosData = {
                {"PlayStation 5", "Consola de última generación", "799.99", "Consolas", "Sony"},
                {"Xbox Series X", "Potente consola de Microsoft", "699.99", "Consolas", "Microsoft"},
                {"Nintendo Switch OLED", "Consola híbrida con pantalla OLED", "449.99", "Consolas", "Nintendo"},
                {"The Legend of Zelda", "Aventura épica", "59.99", "Videojuegos", "Nintendo"},
                {"God of War Ragnarök", "Acción y mitología nórdica", "69.99", "Videojuegos", "Sony"},
                {"Elden Ring", "RPG de mundo abierto", "59.99", "Videojuegos", "FromSoftware"},
                {"Control DualSense", "Control inalámbrico PS5", "74.99", "Accesorios", "Sony"},
                {"Auriculares Gaming", "Audio 7.1 surround", "129.99", "Periféricos", "Logitech"},
                {"Teclado Mecánico RGB", "Switches azules", "149.99", "Periféricos", "Razer"},
                {"Mouse Gaming", "16000 DPI", "79.99", "Periféricos", "Logitech"},
                {"RTX 4070 Ti", "Tarjeta gráfica de alta gama", "899.99", "PC Gaming", "NVIDIA"},
                {"Ryzen 7 7800X3D", "Procesador para gaming", "449.99", "PC Gaming", "AMD"},
                {"Monitor 4K 144Hz", "Panel IPS", "699.99", "PC Gaming", "ASUS"},
                {"Silla Gaming", "Ergonómica con soporte lumbar", "299.99", "Accesorios", "Secretlab"},
                {"Figura Link", "Coleccionable 30cm", "149.99", "Coleccionables", "Nintendo"}
        };
        
        for (String[] data : productosData) {
            Producto producto = new Producto();
            producto.setNombre(data[0]);
            producto.setDescripcion(data[1]);
            producto.setPrecio(new BigDecimal(data[2]));
            
            // Precio anterior aleatorio (10-20% más alto)
            BigDecimal precioAnterior = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(1.1 + (Math.random() * 0.1)))
                    .setScale(2, RoundingMode.HALF_UP);
            producto.setPrecioAnterior(precioAnterior);
            
            producto.setStock(faker.number().numberBetween(10, 100));
            producto.setImagen("/images/productos/" + data[0].toLowerCase().replace(" ", "-") + ".jpg");
            producto.setMarca(data[4]);
            producto.setSku("SKU-" + faker.number().digits(8));
            producto.setDestacado(Math.random() > 0.7); // 30% destacados
            producto.setActivo(true);
            
            // Asignar categoría
            String catNombre = data[3];
            Categoria categoria = categorias.stream()
                    .filter(c -> c.getNombre().equals(catNombre))
                    .findFirst()
                    .orElse(categorias.get(0));
            producto.setCategoria(categoria);
            
            productos.add(repository.save(producto));
        }
        
        // Productos adicionales aleatorios
        for (int i = 0; i < 20; i++) {
            Producto producto = new Producto();
            producto.setNombre(faker.commerce().productName());
            producto.setDescripcion(faker.lorem().paragraph());
            producto.setPrecio(new BigDecimal(faker.number().randomDouble(2, 19, 999)));
            producto.setPrecioAnterior(producto.getPrecio().multiply(BigDecimal.valueOf(1.15)));
            producto.setStock(faker.number().numberBetween(5, 150));
            producto.setImagen("/images/productos/default.jpg");
            producto.setMarca(faker.company().name());
            producto.setSku("SKU-" + faker.number().digits(8));
            producto.setDestacado(Math.random() > 0.8);
            producto.setActivo(true);
            producto.setCategoria(categorias.get(faker.number().numberBetween(0, categorias.size())));
            
            productos.add(repository.save(producto));
        }
        
        return productos;
    }
    
    private List<Blog> crearBlogs(BlogRepository repository) {
        List<Blog> blogs = new ArrayList<>();
        
        String[] titulos = {
                "Los mejores juegos de 2024",
                "Guía completa para principiantes en gaming",
                "PlayStation 5 vs Xbox Series X: Comparativa",
                "Tendencias en PC Gaming",
                "Cómo elegir el monitor perfecto para gaming"
        };
        
        for (String titulo : titulos) {
            Blog blog = new Blog();
            blog.setTitulo(titulo);
            blog.setResumen(faker.lorem().sentence(15));
            blog.setContenido(faker.lorem().paragraphs(5).toString());
            blog.setImagen("/images/blogs/" + titulo.toLowerCase().replace(" ", "-") + ".jpg");
            blog.setAutor(faker.name().fullName());
            blog.setPublicado(true);
            blogs.add(repository.save(blog));
        }
        
        // Blogs adicionales
        for (int i = 0; i < 5; i++) {
            Blog blog = new Blog();
            blog.setTitulo(faker.book().title());
            blog.setResumen(faker.lorem().sentence(12));
            blog.setContenido(faker.lorem().paragraphs(4).toString());
            blog.setImagen("/images/blogs/default.jpg");
            blog.setAutor(faker.name().fullName());
            blog.setPublicado(Math.random() > 0.3); // 70% publicados
            blogs.add(repository.save(blog));
        }
        
        return blogs;
    }
    
    private List<Contacto> crearContactos(ContactoRepository repository) {
        List<Contacto> contactos = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            Contacto contacto = new Contacto();
            contacto.setNombre(faker.name().fullName());
            contacto.setEmail(faker.internet().emailAddress());
            contacto.setTelefono(faker.phoneNumber().cellPhone());
            contacto.setAsunto(faker.lorem().sentence(5));
            contacto.setMensaje(faker.lorem().paragraph(3));
            contacto.setLeido(Math.random() > 0.5); // 50% leídos
            contactos.add(repository.save(contacto));
        }
        
        return contactos;
    }
}
