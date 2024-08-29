FROM openjdk
WORKDIR /app
ENTRYPOINT ["java", "-jar", "JsonReader.jar"]
COPY out/artifacts/JsonReader_jar/JsonReader.jar /app/JsonReader.jar
COPY src/main/resources/tickets.json /app