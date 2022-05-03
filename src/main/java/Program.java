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
        Path configPath = Path.of("config-files", "prank-config.json");
        PrankConfig config = PrankConfigReader.readPrankConfig( configPath );
        PrankGroup[] groups = PrankGroup.createPrankGroups(config.getVictimsMails(), config.getNbPrankGroups());

        SmtpClient client = new SmtpClient(config.getSmtpHost(), config.getSmtpPort());

        Random rand = new Random();
        for (PrankGroup group : groups) {
            int mailsIndex = rand.nextInt(config.getPrankMails().length);
            client.sendEmail(group.getSender(), group.getVictims(),
                             config.getPrankMails()[mailsIndex].getSubject(), config.getPrankMails()[mailsIndex].getBody());
        }
        client.quit();
    }
}
