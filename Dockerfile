# Imagen base con Java 21
FROM eclipse-temurin:21-jdk

# Directorio dentro del contenedor
WORKDIR /app

# Copiamos el proyecto
COPY target/Midaxus-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Comando para arrancar la app
ENTRYPOINT ["java", "-jar", "app.jar"]