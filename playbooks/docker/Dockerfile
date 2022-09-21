FROM openjdk:17-slim

LABEL org.opencontainers.image.source https://github.com/ruchira088/video-downloader-fallback-api

WORKDIR /opt/video-downloader-fallback-api

ADD build/libs/video-downloader-fallback-api.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/video-downloader-fallback-api/app.jar"]