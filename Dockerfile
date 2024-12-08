#WYBIERAMY ŚRODOWISKO JAVY
FROM amazoncorretto:17.0.13-alpine

#OSOBA ODPOWIEDZIALNA ZA OBRAZ
LABEL maintainer="budzikovy"

#KOPIUJEMY NASZ PLIK JAR DO OBRAZU
COPY target/medical-clinic-proxy-0.0.1-SNAPSHOT.jar medical-clinic-proxy-0.0.1-SNAPSHOT.jar

#PORT NA KTÓRYM APLIKACJA BEDZIE DOSTEPNA
EXPOSE 8081

#PLIK WYKONYWALNY
ENTRYPOINT ["java", "-jar", "medical-clinic-proxy-0.0.1-SNAPSHOT.jar"]
