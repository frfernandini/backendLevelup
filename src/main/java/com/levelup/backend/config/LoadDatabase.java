package com.levelup.backend.config;

import com.levelup.backend.models.*;
import com.levelup.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class LoadDatabase {
    

    private final Faker faker = new Faker();
    
    @Bean
    CommandLineRunner initDatabase(
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository,
            BlogRepository blogRepository,
            ContactoRepository contactoRepository,
            EventoRepository eventoRepository) {

        return args -> {
            System.out.println("Verificando datos en la base de datos...");

            // Verificar si ya existen datos
            long usuariosCount = usuarioRepository.count();
            long categoriasCount = categoriaRepository.count();
            long productosCount = productoRepository.count();
            long eventosCount = eventoRepository.count();

            if (usuariosCount > 0 || categoriasCount > 0 || productosCount > 0 || eventosCount > 0) {
                System.out.println("========================================");
                System.out.println("⚠️  Datos ya existen en la base de datos");
                System.out.println("========================================");
                System.out.println("Usuarios: " + usuariosCount);
                System.out.println("Categorías: " + categoriasCount);
                System.out.println("Productos: " + productosCount);
                System.out.println("Eventos: " + eventosCount);
                System.out.println("Blogs: " + blogRepository.count());
                System.out.println("Contactos: " + contactoRepository.count());
                System.out.println("========================================");
                System.out.println("💡 Si deseas recargar los datos, cambia ddl-auto a 'create-drop'");
                System.out.println("========================================");
                return;
            }

            System.out.println("No hay datos, iniciando carga de datos de prueba...");

            // Crear usuarios
            List<Usuario> usuarios = crearUsuarios(usuarioRepository);
            System.out.println("✓ " + usuarios.size() + " usuarios creados");

            // Crear categorías
            List<Categoria> categorias = crearCategorias(categoriaRepository);
            System.out.println("✓ " + categorias.size() + " categorías creadas");

            // Crear productos
            List<Producto> productos = crearProductos(productoRepository, categorias);
            System.out.println("✓ " + productos.size() + " productos creados");

            // Crear blogs
            List<Blog> blogs = crearBlogs(blogRepository);
            System.out.println("✓ " + blogs.size() + " blogs creados");

            // Crear contactos
            List<Contacto> contactos = crearContactos(contactoRepository);
            System.out.println("✓ " + contactos.size() + " mensajes de contacto creados");

            // Crear eventos
            List<Evento> eventos = crearEventos(eventoRepository);
            System.out.println("✓ " + eventos.size() + " eventos creados");

            System.out.println("========================================");
            System.out.println("✅ Datos de prueba cargados exitosamente!");
            System.out.println("========================================");
            System.out.println("Usuario Admin - Email: admin@test.com | Password: admin123");
            System.out.println("Usuario Test  - Email: user@test.com  | Password: user123");
            System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
            System.out.println("========================================");
        };
    }
    
    private List<Usuario> crearUsuarios(UsuarioRepository repository) {
        List<Usuario> usuarios = new ArrayList<>();
        

        Usuario admin = new Usuario();
        admin.setNombre("Admin Usuario");
        admin.setEmail("admin@test.com");
        admin.setPassword("admin123");
        admin.setTelefono("555-0001");
        admin.setDireccion("Calle Principal 123");
        Set<String> adminRoles = new HashSet<>();
        adminRoles.add("ADMIN");
        adminRoles.add("USER");
        admin.setRoles(adminRoles);
        admin.setActivo(true);
        usuarios.add(repository.save(admin));
        

        Usuario user = new Usuario();
        user.setNombre("Test Usuario");
        user.setEmail("user@test.com");
        user.setPassword("user123");
        user.setTelefono("555-0002");
        user.setDireccion("Avenida Secundaria 456");
        Set<String> userRoles = new HashSet<>();
        userRoles.add("USER");
        user.setRoles(userRoles);
        user.setActivo(true);
        usuarios.add(repository.save(user));
        

        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setPassword("password123");
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
        

        String[][] productosData = {
                {"Catan", "Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan.", "29990", "Juegos de Mesa", "Catan Studio", "https://bucket-levelup.s3.us-east-1.amazonaws.com/catan.png"},
                {"Carcassonne", "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne.", "24990", "Juegos de Mesa", "Z-Man Games", "https://bucket-levelup.s3.us-east-1.amazonaws.com/carcassonne.png"},
                {"Controlador Inalámbrico Xbox Series X", "Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.", "59990", "Accesorios", "Microsoft", "https://bucket-levelup.s3.us-east-1.amazonaws.com/xbox_controller.png"},
                {"Auriculares Gamer HyperX Cloud II", "Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad", "79990", "Accesorios", "HyperX", "https://bucket-levelup.s3.us-east-1.amazonaws.com/hyperx_cloud.png"},
                {"PlayStation 5", "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.", "549990", "Consolas", "Sony", "https://bucket-levelup.s3.us-east-1.amazonaws.com/ps5-test.png"},
                {"PC Gamer ASUS ROG Strix", "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional", "1299990", "Computadores Gamers", "ASUS", "https://bucket-levelup.s3.us-east-1.amazonaws.com/pc gamer.png"},
                {"Silla Gamer Secretlab Titan", "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.", "349990", "Sillas Gamers", "Secretlab", "https://bucket-levelup.s3.us-east-1.amazonaws.com/silla_gamer.png"},
                {"Mouse Gamer Logitech G502 HERO", "Con sensor de alta precisión y botones personalizables, este mouse es ideal para gamers que buscan un control preciso y personalización.", "49990", "Mouse", "Logitech", "https://bucket-levelup.s3.us-east-1.amazonaws.com/mouse.png"},
                {"Mousepad Razer Goliathus Extended Chroma", "Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse.", "29990", "Mousepad", "Razer", "https://bucket-levelup.s3.us-east-1.amazonaws.com/mousepad.png"},
                {"Polera Gamer Personalizada 'Level-Up'", "Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.", "14990", "Poleras Personalizadas", "Level-Up", "https://bucket-levelup.s3.us-east-1.amazonaws.com/polera_gamer_life.png"}
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
            producto.setImagen(data[5]);  // Usar la ruta de imagen del array
            producto.setMarca(data[4]);
            producto.setSku("SKU-" + faker.number().digits(8));
            producto.setDestacado(Math.random() > 0.7); // 30% destacados
            producto.setActivo(true);
            
            // Asignar categoría
            String catNombre = data[3];
            Categoria categoria = categorias.stream()
                    .filter(c -> c.getNombre().equals(catNombre))
                    .findFirst()
                    .orElseGet(categorias::getFirst);
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

    private List<Evento> crearEventos(EventoRepository repository) {
        List<Evento> eventos = new ArrayList<>();
        String[][] eventosData = {
            {"Torneo de League of Legends", "Participa en nuestro torneo anual de League de Legends y gana grandes premios.", "https://bucket-levelup.s3.us-east-1.amazonaws.com/lol-torneo.jpg", "Online", "Torneos"},
            {"Lanzamiento de PS6", "Sé el primero en experimentar la nueva generación de consolas de Sony.", "https://bucket-levelup.s3.us-east-1.amazonaws.com/lanzamiento-ps6.jpg", "Tienda Principal", "Lanzamientos"},
            {"Feria de Videojuegos Retro", "Vuelve al pasado y disfruta de los clásicos que marcaron una época.", "https://bucket-levelup.s3.us-east-1.amazonaws.com/feria-retro.jpg", "Centro de Convenciones", "Ferias"}
        };

        for (String[] data : eventosData) {
            Evento evento = new Evento();
            evento.setNombre(data[0]);
            evento.setDescripcion(data[1]);
            evento.setFechaInicio(java.time.LocalDateTime.now().plusDays(faker.number().numberBetween(10, 30)));
            evento.setFechaFin(evento.getFechaInicio().plusHours(faker.number().numberBetween(2, 8)));
            evento.setImagenUrl(data[2]);
            evento.setLugar(data[3]);
            evento.setCategoriaAsociada(data[4]);
            eventos.add(repository.save(evento));
        }
        return eventos;
    }
}
