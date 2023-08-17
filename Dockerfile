FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./build/libs/social-media-1.0-SNAPSHOT.jar .
CMD ["java", "-jar", "social-media-1.0-SNAPSHOT.jar"]