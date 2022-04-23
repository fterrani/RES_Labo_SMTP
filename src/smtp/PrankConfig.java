package smtp;

import java.util.List;

public class PrankConfig {
    public String smtpHost;
    public int smtpPort;

    public int nbPrankGroups;
    public List<PrankMail> prankMails;
    public List<String> victimsMails;
}
