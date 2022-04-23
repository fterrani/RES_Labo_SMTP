# API - Lab 4 SMTP report

**Authors:** Emmanuelle Comte, Fabien Terrani

## Project description

TODO

TODO

TODO

TODO

## Test the project locally

You can use MockMock to test the software on your machine, without sending any real e-mail.

### What is MockMock?

[MockMock](https://github.com/tweakers/MockMock) is a Java 7 software that mocks a real SMTP server. SMTP clients can connect to MockMock and send actual SMTP commands to test their e-mail sending features.

However, a MockMock instance only allows *visualizing e-mails that should have been sent* since it was started. MockMock will **never** actually send any e-mail.

### Running the MockMock HEIG fork in a Docker container

This project uses a [bugfixed MockMock fork maintained by HEIG-VD](https://github.com/HEIGVD-Course-API/MockMock) since the original project isn't maintained anymore. To run this fork in a Docker container, do the following:

1. If this is not done yet, start Docker on your machine.

2. Open a terminal and go in the folder of this repository that contains a file named `Dockerfile`.

3. Run this command to build a Docker image containing HEIG's MockMock and JRE 7:
   `docker build -t heig-mockmock .`

4. Once the image was built, run this command to start the container in a temporary container:
   `docker run -d --rm -p 25:25/tcp -p 8282:8282/tcp heig-mockmock`

5. MockMock should now be running. To see the webinterface of MockMock, open a web browser and go to `http://localhost:8282`

6. When you are done using MockMock, you can stop the container using the following command: `docker container stop <container-id>` where `<container-id>` is the name of the container you created earlier (use `docker ps` to see started container and their IDs)

## How to use

### Configure a prank campaign

TODO

TODO

TODO

TODO

### Running a prank campaign

TODO

TODO

TODO

TODO

## Implementation description

### Class diagram

TODO

TODO

TODO

TODO

### Main classes' responsibilities

TODO

TODO

**Clear and simple instructions for configuring your tool and running a prank campaign**. If you do a good job, an external user should be able to clone your  repo, edit a couple of files and send a batch of e-mails in less than 10 minutes.

TODO

TODO

### Sequence diagram

TODO

TODO

document the key aspects of your code. It is a good idea to start with a **class diagram**. Decide which classes you want to show (focus on the important ones) and describe their responsibilities in text. It is also certainly a good  idea to include examples of dialogues between your client and an SMTP  server (maybe you also want to include some screenshots here).

TODO

TODO
