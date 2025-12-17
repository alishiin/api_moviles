# Imagen con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Carpeta de trabajo
WORKDIR /app

# Copiar wrapper y config
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dar permisos
RUN chmod +x mvnw

# Descargar dependencias
RUN ./mvnw dependency:go-offline

# Copiar código
COPY src src

# Compilar
RUN ./mvnw clean package -DskipTests

# Exponer puerto (Render usa PORT dinámico)
EXPOSE 8080

# Ejecutar app
CMD ["java", "-jar", "target/*.jar"]
