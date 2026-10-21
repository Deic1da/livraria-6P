# Etapa 1: compilar o projeto (precisa do JDK e do Maven)
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app

# copia só o que define as dependências e baixa tudo antes do código:
# assim o Docker reaproveita essa camada enquanto o pom.xml não mudar (cache)
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw -q dependency:go-offline

COPY src src
RUN ./mvnw -q package -DskipTests

# Etapa 2: rodar (só o JRE, imagem bem menor)
FROM eclipse-temurin:25-jre-alpine
ENV PORT=8080
WORKDIR /app

# não rodar como root: se algo der errado, o processo não tem privilégio de administrador
RUN adduser -D usuarioapp
USER usuarioapp

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
