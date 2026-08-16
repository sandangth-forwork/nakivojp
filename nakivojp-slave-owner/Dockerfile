# ============================================================
# Build stage
# ============================================================
FROM maven:3.9-eclipse-temurin-25 AS builder

WORKDIR /build

COPY pom.xml .

COPY nakivojp-shared/pom.xml nakivojp-shared/pom.xml
COPY nakivojp-slave/pom.xml nakivojp-slave/pom.xml
COPY nakivojp-slave-owner/pom.xml nakivojp-slave-owner/pom.xml

RUN mkdir -p nakivojp-shared nakivojp-slave nakivojp-slave-owner

RUN mvn dependency:go-offline -B

COPY nakivojp-shared/src nakivojp-shared/src
COPY nakivojp-slave-owner/src nakivojp-slave-owner/src

RUN mvn clean package -DskipTests -B -pl nakivojp-slave-owner -am

# ============================================================
# Runtime stage
# ============================================================
FROM eclipse-temurin:25-jre
RUN apt-get update && apt-get install -y wget
WORKDIR /app
COPY --from=builder /build/nakivojp-slave-owner/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
