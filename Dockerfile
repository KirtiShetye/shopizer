FROM eclipse-temurin:11-jre

WORKDIR /app

COPY sm-shop/target/shopizer.jar /app/shopizer.jar
COPY add-sample-product.sh /app/
COPY add-electronics-products.sh /app/

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN chmod +x /app/*.sh

EXPOSE 8080

COPY docker-entrypoint.sh /app/
RUN chmod +x /app/docker-entrypoint.sh

ENTRYPOINT ["/app/docker-entrypoint.sh"]
