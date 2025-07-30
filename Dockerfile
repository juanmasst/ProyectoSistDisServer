# Usar OpenJDK 17 como base
FROM openjdk:17-jdk-slim

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR del proyecto
COPY build/libs/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Crear directorio para uploads
RUN mkdir -p /app/uploads

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"] 