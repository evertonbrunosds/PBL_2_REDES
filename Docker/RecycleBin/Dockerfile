FROM openjdk:11
WORKDIR /usr/src
COPY ./RecycleBin.jar /usr/src
COPY lib /usr/src/lib

ENTRYPOINT [ "java", "-jar", "./RecycleBin.jar" ]