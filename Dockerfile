FROM amazoncorretto:17
MAINTAINER IDEAProjects
COPY target/MiddleProject-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]