FROM openjdk:17-jdk-slim-buster
COPY target/dropwizard-example-4.0.0-beta.2.jar dropwizard-example-4.0.0-beta.2.jar
COPY example.yml example.yml
COPY example.keystore example.keystore
RUN java -jar dropwizard-example-4.0.0-beta.2.jar db migrate example.yml
ENTRYPOINT java -jar dropwizard-example-4.0.0-beta.2.jar server example.yml

# docker build --no-cache --platform=linux/amd64 --tag=service:latest .
# docker run -p 8080:8080 demo
