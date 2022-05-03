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
        PrankGroup[] groups = createGroups(config.getVictimsMails(), config.getNbPrankGroups());

        SmtpClient client = new SmtpClient(config.getSmtpHost(), config.getSmtpPort());

        Random rand = new Random();
        for (PrankGroup group : groups) {
            int mailsIndex = rand.nextInt(config.getPrankMails().length);
            client.sendEmail(group.getSender(), group.getVictims(),
                             config.getPrankMails()[mailsIndex].getSubject(), config.getPrankMails()[mailsIndex].getBody());
        }
        client.quit();
    }
     private static PrankGroup[] createGroups(String[] victims, int nbGroups) {
         int groupsSize = victims.length / nbGroups;
         int rest = victims.length % nbGroups;

         PrankGroup[] groups = new PrankGroup[nbGroups];
         for (int i = 0; i < nbGroups; ++i) {
             if (i < rest)
                 groups[i] = new PrankGroup(groupsSize + 1);
             else
                 groups[i] = new PrankGroup(groupsSize);
         }

         Random rand = new Random();
         int remainingMails = victims.length;
         String selectedMail;
         for (PrankGroup group : groups) {
             selectedMail = selectRandomMail(victims, remainingMails, rand);
             group.setSender(selectedMail);
             --remainingMails;

             while (!group.isFull()) {
                 selectedMail = selectRandomMail(victims, remainingMails, rand);
                 group.addVictim(selectedMail);
                 --remainingMails;
             }
         }
         return groups;
     }

     private static String selectRandomMail(String[] list, int length, Random r) {
         int i  = r.nextInt(length);

         String tmp = list[i];
         list[i] = list[length - 1];
         list[length - 1] = tmp;

         return tmp;
     }
}
