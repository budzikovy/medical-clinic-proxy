FROM amazoncorretto:17.0.13-alpine

LABEL maintainer="budzikovy"

COPY target/medical-clinic-proxy-0.0.1-SNAPSHOT.jar medical-clinic-proxy-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "medical-clinic-proxy-0.0.1-SNAPSHOT.jar"]
