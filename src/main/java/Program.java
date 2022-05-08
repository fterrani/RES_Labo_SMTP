import config.PrankConfigReader;
import config.PrankConfig;
import config.PrankGroup;
import smtp.SmtpClient;

import java.io.*;
import java.nio.file.Path;
import java.util.Random;

public class Program
{
    public static void main( String[] args ) throws IOException {
        // Reads configuration
        Path configPath = Path.of("config-files", "prank-config.json");
        PrankConfig config = PrankConfigReader.readPrankConfig( configPath );

        // Creates groups
        PrankGroup[] groups = PrankGroup.createPrankGroups(config.getVictimsMails(), config.getNbPrankGroups());

        // Creates the SmtpClient
        SmtpClient client = new SmtpClient(config.getSmtpHost(), config.getSmtpPort());

        // For each group, randomly selects a mail and send it.
        Random rand = new Random();
        int mailNumber = 1;
        for (PrankGroup group : groups) {
            int mailsIndex = rand.nextInt(config.getPrankMails().length);

            System.out.println("####################### Sending mail " + mailNumber + "...");
            boolean mailSent = client.sendEmail(group.getSender(), group.getVictims(),
                                                config.getPrankMails()[mailsIndex].getSubject(),
                                                config.getPrankMails()[mailsIndex].getBody());

            if (mailSent) {
                System.out.println("####################### Mail " + mailNumber + ": success \uD83D\uDE0A");
            } else {
                System.out.println("####################### Mail " + mailNumber + ": fail \uD83D\uDE22");
            }
            ++mailNumber;
            System.out.println();
        }

        // Closes the client cleanly
        client.quit();
    }
}
