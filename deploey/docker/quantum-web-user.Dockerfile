# 使用官方 OpenJDK 镜像作为基础镜像
FROM openjdk:25-jdk-slim

# 设置维护者信息
LABEL maintainer="Quantum Team"

# 设置工作目录
WORKDIR /app

# 复制 JAR 文件到容器中
COPY quantum-web/quantum-web-user/target/quantum-web-user-*.jar app.jar

# 暴露端口
EXPOSE 5001

# 运行应用程序
ENTRYPOINT ["java", "-jar", "app.jar"]