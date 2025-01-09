# Sử dụng hình ảnh Maven để xây dựng ứng dụng
FROM maven:3.8.5-openjdk-17-slim AS build

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file pom.xml và tải các dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Sao chép toàn bộ mã nguồn và build ứng dụng
COPY . .
RUN mvn package -DskipTests

# Sử dụng hình ảnh JRE nhẹ hơn để chạy ứng dụng
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc
WORKDIR /app

# Sao chép file jar từ bước build trước
COPY --from=build /app/target/*.jar app.jar

# Expose cổng mà ứng dụng chạy (thường là 8080)
EXPOSE 8080

# Lệnh chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
