# API - Lab 4 SMTP report

**Authors:** Emmanuelle Comte, Fabien Terrani

## Project description

This project allows to send prank mails to a defined set of mail addresses. The prank mails and the mail addresses to use for the prank can be configured.

The victims will be divided in a configurable number of groups, each one containing at least 3 people. A sender will be randomly chosen in the group; all other people in the group will be the receivers.

The e-mail will be sent such that receivers will think the e-mail comes from the sender. The e-mail's body and subject is randomly chosen in the list of configured prank mails.

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

5. MockMock should now be running. You can send SMTP commands to `localhost` on port `25`. To see the webinterface of MockMock, open a web browser and go to `http://localhost:8282`.

6. When you are done using MockMock, you can stop the container using the following command: `docker container stop <container-id>` where `<container-id>` is the name of the container you created earlier (use `docker ps` to see started container and their IDs)

## How to use

### Configure a prank campaign

The prank campaign configuration is done via a JSON file named `prank-config.json`. The file follows the syntax shown below:

```json
{
  "smtpServer": {
    "host": "smtp.example.org",
    "port": 25
  },
  "nbPrankGroups": 2,
  "prankMails": [
    {
      "subject": "My first prank mail",
      "body": "The body of my first prank mail."
    },
    {
      "subject": "My second prank mail with UTF-8 chars 章レム献属達ヤ注見とらっし",
      "body": "The body can also contain UTF-8 chars極走見漢がぼあぱ。It can also \n contain \r\n new lines!"
    }
  ],
  "victims": [
    "a@example.org",
    "b@example.org",
    "c@example.org",
    "d@example.org",
    "e@example.org",
    "f@example.org",
    "g@example.org",
    "h@example.org",
    "i@example.org"
  ]
}
```

In this example, 2 groups were configured. This will lead to the creation of a 5-person group and a 4-person group. Configuring 1 or 3 groups would also be OK in this case. However, configuring 4 groups or more is impossible with the provided set of victims since a group must contain at least 3 people.

### Running a prank campaign

To run a prank campaign:

1. Download this GitHub repository's content
2. Go in the folder that contains `pom.xml` and use [Maven](https://maven.apache.org/) commands to download the required dependencies and compile the program to a JAR.
3. Create a `prank-config.json` file that follows the syntax described above
4. Place `prank-config.json` at `./config-files/prank-config.json` relatively to the JAR's location.
5. Run the JAR with `java -jar myfile.jar`. Doing this will send the prank e-mails.

## Implementation description

### Class diagram

TODO ECE

TODO ECE

TODO ECE

TODO ECE

#### Main classes' responsibilities

- `PrankConfigReader` is used to **parse** the JSON configuration file and **validate** the configuration.
- `PrankGroup` is used to represent prank groups (group of e-mail addresses with one sender and several recipients). The class also defines a method that will take care of **generating prank groups**, given a list of victims and a number of groups to generate.
- `SmtpClient` implements a basic SMTP client:
  - it can **send an e-mail** using the following SMTP commands: `EHLO`, `MAIL FROM`, `RCPT TO`, `DATA`
  - Each SMTP command has one or several expected response codes defined. If an **unexpected response code** is received from the server, a `RSET` command is used.
  - When the client **has sent all prank e-mails**, a `QUIT` command is used.
  - The client can read single or multiline SMTP responses
- `SmtpCommand` and its subclasses represent SMTP commands used by `SmtpClient`.
- `SmtpResponse` stores data of an SMTP response.

### Sequence diagram

TODO FTI

TODO FTI

document the key aspects of your code. It is a good idea to start with a **class diagram**. Decide which classes you want to show (focus on the important ones) and describe their responsibilities in text.

```
EHLO 42.com
250-bd8d8ddc5803
250-8BITMIME
250 Ok
MAIL FROM:<c@example.org>
250 Ok
RCPT TO:<f@example.org>
250 Ok
RCPT TO:<b@example.org>
250 Ok
RCPT TO:<e@example.org>
250 Ok
DATA
354 End data with <CR><LF>.<CR><LF>
From: c@example.org
To: f@example.org, b@example.org, e@example.org
Subject: =?utf-8?B?Tm9ybWFsIG1haWw=?=
Content-Type: text/plain; charset=utf-8

This is a normal mail.

Best regards
.
250 Ok
QUIT
221 Bye
```

It is also certainly a good  idea to include examples of dialogues between your client and an SMTP  server (maybe you also want to include some screenshots here).

TODO FTI

# TODO

- finish class diagram
- create sequence diagram
- document main methods in the code
- make sure no TODO is left in the code
- read again the HEIG's repository instructions to make sure everything was done
- re-read the report
