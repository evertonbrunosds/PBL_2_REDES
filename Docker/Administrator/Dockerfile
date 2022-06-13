FROM openjdk:11
WORKDIR /usr/src
EXPOSE 8080
COPY ./ApiRest.jar /usr/src
COPY ./MasterSector.jar /usr/src
COPY lib /usr/src/lib
COPY ./Main.sh /usr/src

ENTRYPOINT ["sh", "./Main.sh"]