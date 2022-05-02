import config.PrankConfigReader;
import config.PrankConfig;
import config.PrankGroup;
import smtp.SmtpClient;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

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
        PrankGroup[] groups = createGroups(config.getVictimsMails(), config.getNbPrankGroups());
        Random rand = new Random();
        int mailsIndex = rand.nextInt(config.getPrankMails().length);
        for (PrankGroup group : groups) {
            client.sendEmail(group.getSender(), group.getVictims(), config.getPrankMails()[mailsIndex].getSubject(), config.getPrankMails()[mailsIndex].getBody());
        }
        client.quit();
    }
     private static PrankGroup[] createGroups(String[] victims, int nbGroups) {
         int groupsSize = victims.length / nbGroups;
         int rest = victims.length % nbGroups;
         System.out.println(groupsSize);
         PrankGroup[] groups = new PrankGroup[nbGroups];
         for (int i = 0; i < nbGroups; ++i) {
             if (i < rest)
                 groups[i] = new PrankGroup(groupsSize + 1);
             else
                 groups[i] = new PrankGroup(groupsSize);
         }
         Random rand = new Random();

         int nbToAdd = victims.length;
         int i;
         String tmp;
         for (PrankGroup group : groups) {
             i  = rand.nextInt(nbToAdd);
             group.setSender(victims[i]);

             tmp = victims[nbToAdd - 1];
             victims[nbToAdd - 1] = victims[i];
             victims[i] = tmp;
             --nbToAdd;
             while (!group.isFull()) {
                 i  = rand.nextInt(nbToAdd);
                 group.addVictim(victims[i]);
                 
                 tmp = victims[nbToAdd - 1];
                 victims[nbToAdd - 1] = victims[i];
                 victims[i] = tmp;
                 --nbToAdd;
             }
         }
         return groups;
     }

     private static void swap(String a, String b) {
         String tmp = a;
         a = b;
         b = tmp;
     }
}
