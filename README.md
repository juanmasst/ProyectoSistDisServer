#SISTEMAS DISTRIBUIDOS UDA
##Alumno Juan Manuel Massotto Lasagno - Legajo: 113842

# Sistema de Foro con Chat - Backend

Este es el backend de un sistema de foro con chat desarrollado con Spring Boot, que incluye autenticación con Auth0, gestión de temas y mensajes, y soporte para subida de imágenes.

## 🚀 Características

- **Autenticación con Auth0**: Integración completa con Auth0 para autenticación y autorización
- **Gestión de Temas**: Crear y consultar temas de discusión
- **Sistema de Mensajes**: Chat en tiempo real con soporte para imágenes y GIFs
- **Subida de Archivos**: Soporte para subir imágenes desde el frontend
- **API REST**: Endpoints bien documentados y estructurados
- **Validación**: Validación de datos con Bean Validation
- **CORS**: Configuración para desarrollo frontend
- **Base de Datos**: PostgreSQL con JPA/Hibernate

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.5.3**: Framework principal
- **Spring Security**: Seguridad y autenticación
- **Spring Data JPA**: Persistencia de datos
- **PostgreSQL**: Base de datos
- **Auth0**: Autenticación y autorización
- **Gradle (Kotlin DSL)**: Gestión de dependencias
- **Lombok**: Reducción de código boilerplate
- **Java 17**: Lenguaje de programación

## 📋 Prerrequisitos

- Java 17 o superior
- PostgreSQL 12 o superior
- Gradle 7.0 o superior
- Cuenta de Auth0 (gratuita)

## 🔧 Configuración

### 1. Configuración de Auth0

1. Crea una cuenta en [Auth0](https://auth0.com)
2. Crea una nueva aplicación (Single Page Application)
3. Configura las URLs permitidas:
   - Allowed Callback URLs: `http://localhost:3000/callback`
   - Allowed Logout URLs: `http://localhost:3000`
   - Allowed Web Origins: `http://localhost:3000`
4. Copia las siguientes credenciales:
   - Domain
   - Client ID
   - Client Secret

### 2. Configuración de la Base de Datos

1. Crea una base de datos PostgreSQL:
```sql
CREATE DATABASE forum_db;
CREATE USER forum_user WITH PASSWORD 'tu_password';
GRANT ALL PRIVILEGES ON DATABASE forum_db TO forum_user;
```

### 3. Configuración del Proyecto

1. Edita el archivo `src/main/resources/application.properties`:
```properties
# Auth0 Configuration
auth0.audience=https://tu-dominio.auth0.com/api/v2/
auth0.issuer=https://tu-dominio.auth0.com/
auth0.jwk-set-uri=https://tu-dominio.auth0.com/.well-known/jwks.json

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/forum_db
spring.datasource.username=forum_user
spring.datasource.password=tu_password
```

## 🚀 Instalación y Ejecución

### 1. Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd ProyectoSistDisServer
```

### 2. Ejecutar con Gradle
```bash
# Compilar el proyecto
./gradlew build

# Ejecutar la aplicación
./gradlew bootRun
```

### 3. Verificar la Instalación
- El servidor estará disponible en: `http://localhost:8080`
- Health check: `http://localhost:8080/api/public/health`

## 📚 API Endpoints

### Endpoints Públicos (Sin Autenticación)
- `GET /api/public/health` - Health check
- `GET /api/public/temas` - Listar temas públicos
- `GET /api/public/temas/{id}` - Obtener tema por ID

### Endpoints de Autenticación
- `GET /api/auth/me` - Obtener usuario actual

### Endpoints Protegidos (Requieren Autenticación)
- `GET /api/temas` - Listar todos los temas
- `POST /api/temas` - Crear nuevo tema
- `GET /api/temas/{id}` - Obtener tema por ID
- `GET /api/mensajes/tema/{temaId}` - Obtener mensajes de un tema
- `POST /api/mensajes` - Crear nuevo mensaje
- `POST /api/upload` - Subir imagen
- `GET /api/upload/{nombreArchivo}` - Descargar imagen

## 🔐 Autenticación

El sistema utiliza JWT tokens de Auth0. Para hacer requests autenticados:

1. Obtén un token de Auth0 desde tu frontend
2. Incluye el token en el header Authorization:
```
Authorization: Bearer <tu-jwt-token>
```

## 📁 Estructura del Proyecto

```
src/main/java/com/sistDistribuidos/server/
├── config/                 # Configuraciones
│   ├── Auth0Properties.java
│   └── SecurityConfig.java
├── controller/             # Controladores REST
│   ├── AuthController.java
│   ├── FileController.java
│   ├── MensajeController.java
│   ├── PublicController.java
│   └── TemaController.java
├── dto/                   # Data Transfer Objects
│   ├── CrearMensajeRequest.java
│   ├── CrearTemaRequest.java
│   ├── MensajeDto.java
│   ├── TemaDto.java
│   └── UsuarioDto.java
├── entity/                # Entidades JPA
│   ├── Mensaje.java
│   ├── Tema.java
│   └── Usuario.java
├── exception/             # Manejo de excepciones
│   └── GlobalExceptionHandler.java
├── repository/            # Repositorios JPA
│   ├── MensajeRepository.java
│   ├── TemaRepository.java
│   └── UsuarioRepository.java
├── service/               # Servicios de negocio
│   ├── FileService.java
│   ├── MensajeService.java
│   ├── TemaService.java
│   └── UsuarioService.java
└── ServerApplication.java # Clase principal
```

## 🧪 Testing

### Ejecutar Tests
```bash
./gradlew test
```

### Ejecutar Tests con Cobertura
```bash
./gradlew test jacocoTestReport
```

## 📦 Despliegue

### Docker
```bash
# Construir imagen
docker build -t forum-server .

# Ejecutar contenedor
docker run -p 8080:8080 forum-server
```

### Producción
1. Configura las variables de entorno para producción
2. Usa un servidor de aplicaciones como Tomcat
3. Configura un proxy reverso (nginx, Apache)
4. Configura SSL/TLS

## 🔧 Configuración de Desarrollo

### Habilitar DevTools
El proyecto incluye Spring Boot DevTools para desarrollo:
- Hot reload automático
- Configuración automática de CORS
- Logging mejorado

### Logging
Los logs se configuran en `application.properties`:
```properties
logging.level.com.sistDistribuidos=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 🆘 Soporte

Si tienes problemas o preguntas:

1. Revisa la documentación de Auth0
2. Verifica la configuración de la base de datos
3. Revisa los logs de la aplicación
4. Abre un issue en el repositorio

## 🔄 Próximas Mejoras

- [ ] WebSocket para chat en tiempo real
- [ ] Notificaciones push
- [ ] Búsqueda avanzada
- [ ] Moderación de contenido
- [ ] API de estadísticas
- [ ] Exportación de datos
