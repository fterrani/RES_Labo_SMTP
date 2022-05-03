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
        if (!response.code.startsWith("2")) {
            throw new SmtpProtocolException("Server not ready");
        }
    }

    public boolean sendEmail(String sender, String[] receivers, String subject, String body) throws IOException
    {
        // TODO Create required smtp.commands and add them to the array
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

                if ( ! command.isResponseCodeExpected( response.code ) )
                {
                    throw new SmtpProtocolException(
                        String.format(
                            "Unexpected response (expected something matching regex \"%s\", got \"%s\")",
                            command.getExpectedResponseRegex(),
                            response.code
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
            if (!command.isResponseCodeExpected(response.code)) {
                quit();
            }

            return false;
        }
    }

    public void quit() throws IOException
    {
        sendCommand( new SmtpQuit() );
        socket.close();
    }

    private SmtpResponse sendCommand(SmtpCommand command) throws IOException {
        sendWriter.println( command );
        sendWriter.flush();
        return readResponse();
    }

    private SmtpResponse readResponse() throws IOException
    {
        StringBuilder responseText = new StringBuilder();
        String line;
        String responseCode = null;
        boolean responseEnd = false;

        do
        {
            line = receiveReader.readLine();
            System.out.println(line);

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
