FROM gradle AS builder
USER root
ENV GRADLE_OPTS "-Dorg.gradle.daemon=false"
WORKDIR /
COPY . .
RUN gradle bootJar

FROM java:8
COPY --from=builder /build/libs/movie-recommend-* app.jar
EXPOSE 10015
ENTRYPOINT ["java", "-jar", "app.jar"]