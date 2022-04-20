FROM openjdk:7u211-jre

# adding MockMock JAR file to the image (direct download from HEIG's forked MockMock repository)
ADD https://github.com/HEIGVD-Course-API/MockMock/blob/master/release/MockMock.jar?raw=true /usr/src/mockmock/

# moving to the JAR folder
WORKDIR /usr/src/mockmock/

# SMTP port
EXPOSE 25/tcp

# web interface port
EXPOSE 8282/tcp

# by default, we start MockMock when a container based on this image is started
CMD ["java", "-jar", "MockMock.jar"]
