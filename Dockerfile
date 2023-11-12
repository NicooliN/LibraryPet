FROM openjdk:17

ARG jarFile=target/LibraryPet-0.0.1-SNAPSHOT.war

WORKDIR  opt/app

COPY ${jarFile} petlibrary.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "petlibrary.war"]