package smtp;

import smtp.commands.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SmtpClient
{
    public static final String END_LINE = "\r\n";

    private final Socket socket;
    private final BufferedReader receiveReader;
    private final PrintWriter sendWriter;

    public SmtpClient(String host, int port) throws IOException
    {
        socket = new Socket(host, port );
        receiveReader = new BufferedReader( new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8) );
        sendWriter = new PrintWriter( new BufferedOutputStream(socket.getOutputStream()), true, StandardCharsets.UTF_8 );
        SmtpResponse response = readResponse();
        if (!response.getCode().startsWith("2")) {
            throw new SmtpProtocolException("Server not ready");
        }
    }

    /**
     * Sends an e-mail to this instance's server using the SMTP protocol. All receivers will receive the e-mail directly
     * (To header will be used, neither Cc nor Bcc).
     * @param sender The sender's mail address
     * @param receivers The receivers' mail addresses
     * @param subject The mail's subject
     * @param body The mail's body
     * @return true if the e-mail was successfully sent, false if a problem occurred and the mail wasn't sent.
     * @throws IOException If a network error occurs.
     */
    public boolean sendEmail(String sender, String[] receivers, String subject, String body) throws IOException
    {
        ArrayList<SmtpCommand> commands = new ArrayList<>();
        commands.add( new SmtpEhlo("42.com"));
        commands.add( new SmtpMailFrom(sender) );

        for (String receiver : receivers) {
            commands.add( new SmtpRcptTo(receiver) );
        }

        commands.add( new SmtpData() );
        commands.add( new SmtpDataContent(subject, body, sender, receivers) );

        try
        {
            for (SmtpCommand command : commands)
            {
                sendWriter.println(command);
                sendWriter.flush();
                System.out.println(command);
                SmtpResponse response = readResponse();
                System.out.println(response.getText());

                if ( ! command.isResponseCodeExpected( response.getCode() ) )
                {
                    throw new SmtpProtocolException(
                        String.format(
                            "Unexpected response (expected something matching regex \"%s\", got \"%s\")",
                            command.getExpectedResponseRegex(),
                            response.getCode()
                        )
                   );
                }
            }

            return true;
        }
        catch (SmtpProtocolException ex)
        {
            SmtpCommand command = new SmtpRset();
            SmtpResponse response = sendCommand(command);
            if (!command.isResponseCodeExpected(response.getCode())) {
                quit();
            }

            return false;
        }
    }

    /**
     * Ends the SMTP communication by sending a QUIT command and closing the socket.
     * @throws IOException If a network error occurs.
     */
    public void quit() throws IOException
    {
        sendCommand( new SmtpQuit() );
        socket.close();
    }

    /**
     * Sends the command SmtpCommand and reads the response.
     * @param command The command to send
     * @return The server response
     * @throws IOException If a network error occurs.
     */
    private SmtpResponse sendCommand(SmtpCommand command) throws IOException {
        sendWriter.println( command );
        sendWriter.flush();
        return readResponse();
    }

    /**
     * Reads a SMTP response from the socket. This method can read single- and multi-line responses.
     * It also parses the SMTP response code.
     * @return A SmtpResponse instance containing the SMTP response code and all rows returned by the server
     * @throws IOException If a network error occurs when reading the response
     * @throws SmtpProtocolException If an unexpected SMTP response is received from the server
     */
    private SmtpResponse readResponse() throws IOException
    {
        StringBuilder responseText = new StringBuilder();
        String line;
        String responseCode = null;
        boolean responseEnd = false;

        do
        {
            line = receiveReader.readLine();

            if ( line == null || line.length() < 3)
                throw new SmtpProtocolException( "Response line is too short (less than 3 characters)" );

            String lineResponseCode = line.substring( 0, 3 );

            // regex from the RFC
            if ( ! lineResponseCode.matches( "[2-5][0-5][0-9]" ) )
                throw new SmtpProtocolException( String.format("Invalid response code: %s", lineResponseCode ) );

            if (responseCode == null)
            {
                responseCode = lineResponseCode;
            }
            else if ( ! responseCode.equals(lineResponseCode) )
            {
                throw new SmtpProtocolException(
                        String.format(
                                "Incoherent response code in multiline response (expected \"%s\", received \"%s\")",
                                responseCode, lineResponseCode
                        )
                );
            }

            if (line.length() == 3)
            {
                responseEnd = true;
            }
            else
            {
                responseText.append( line );

                if (line.charAt(3) == '-')
                    responseText.append( '\n' );
                else if (line.charAt(3) == ' ')
                    responseEnd = true;
                else
                    throw new SmtpProtocolException( "Unexpected char ('"+line.charAt(3) +"') after response code!" );
            }
        }
        while( ! responseEnd );


        return new SmtpResponse( responseCode, responseText.toString() );
    }
}
