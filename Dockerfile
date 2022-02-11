FROM ubuntu:20.04
RUN apt-get update
RUN apt-get -y install openjdk-8-jdk
CMD java -version