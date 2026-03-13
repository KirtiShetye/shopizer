FROM eclipse-temurin:11-jre

WORKDIR /app

COPY sm-shop/target/shopizer.jar /app/shopizer.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "shopizer.jar"]
