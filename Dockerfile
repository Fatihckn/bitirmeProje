# Back-end'i dockerize etmek için Dockerfile

# Geliştirme ortamı: Maven + JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS dev

# Uygulama çalışma dizini
WORKDIR /app

# Tüm proje dosyalarını container'a kopyala
COPY . .

# Uygulamayı spring-boot:run ile çalıştır
CMD ["mvn", "spring-boot:run"]
