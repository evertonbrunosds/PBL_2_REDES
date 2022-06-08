FROM openjdk:11
WORKDIR /usr/src
COPY ./host.txt /usr/src
COPY ./GarbageTruck.jar /usr/src
COPY lib /usr/src/lib

ENTRYPOINT [ "java", "-jar", "./GarbageTruck.jar" ]