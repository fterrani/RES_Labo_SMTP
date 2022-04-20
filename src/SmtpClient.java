import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SmtpClient
{
    private final Socket socket;
    private final BufferedReader receiveReader;
    private final PrintWriter sendWriter;

    public SmtpClient(String host, int port) throws IOException
    {
        socket = new Socket(host, port );
        receiveReader = new BufferedReader( new InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII) );
        sendWriter = new PrintWriter( socket.getOutputStream(), true, StandardCharsets.US_ASCII );
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

            if ( line == null || line.length() < 3)
                throw new SmtpProtocolException( "Response line is too short (less than 3 characters)" );

            String lineResponseCode = line.substring( 0, 3 );

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
                else if (line.charAt(3) != ' ')
                    responseEnd = true;
                else
                    throw new SmtpProtocolException( "Unexpected char ('"+line.charAt(3) +"') after response code!" );
            }
        }
        while( ! responseEnd );


        return new SmtpResponse( responseCode, responseText.toString() );
    }

    public boolean sendEmail(String sender, String[] receivers, String subject, String body) throws IOException, InterruptedException
    {
        // TODO Create required commands and add them to the array
        SmtpCommand[] commands = new SmtpCommand[0];

        try
        {
            for (SmtpCommand command : commands)
            {
                sendWriter.println( command.toString() );
                sendWriter.flush();

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
            // TODO Send a RSET command

            return false;
        }
    }

    public void quit() throws IOException
    {
        // TODO Send QUIT command

        socket.close();
    }
}
