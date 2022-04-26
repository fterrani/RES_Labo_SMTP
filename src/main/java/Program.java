import config.PrankConfigReader;
import config.PrankConfig;
import smtp.SmtpClient;

import java.io.*;
import java.nio.file.Path;

public class Program
{
    public static void main( String[] args ) throws IOException {
        // TODO Ask if JSON is ok

        // TODO add missing commands ECE
            // RCPT TO, DATA, RSET, QUIT
        // TODO implements mail sending ECE
        // TODO encoding subject and body => after
        // => TODO ask witch encoding the prof wants

        Path configPath = Path.of("config-files", "prank-config.json");
        PrankConfig config = PrankConfigReader.readPrankConfig( configPath );
        System.out.println();
        SmtpClient client = new SmtpClient(config.getSmtpHost(), config.getSmtpPort());
        client.sendEmail(config.getVictimsMails()[0], config.getVictimsMails(), config.getPrankMails()[1].getSubject(), config.getPrankMails()[1].getBody());
        client.quit();
    }
}
