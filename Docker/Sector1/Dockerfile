FROM openjdk:11
WORKDIR /usr/src
COPY ./host.txt /usr/src
COPY ./Sector.jar /usr/src
COPY lib /usr/src/lib
COPY ./Main.sh /usr/src

ENTRYPOINT ["sh", "./Main.sh"]