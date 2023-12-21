FROM openjdk:18
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ToDoServer.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/ToDoServer.jar"]