package config;

public class PrankConfig
{
    private final String smtpHost;
    private final int smtpPort;
    private final int nbPrankGroups;
    private final PrankMail[] prankMails;
    private final String[] victimsMails;

    public PrankConfig( String smtpHost, int smtpPort, int nbPrankGroups, PrankMail[] prankMails, String[] victimsMails )
    {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.nbPrankGroups = nbPrankGroups;
        this.prankMails = prankMails;
        this.victimsMails = victimsMails;
    }

    public String getSmtpHost()
    {
        return smtpHost;
    }

    public int getSmtpPort()
    {
        return smtpPort;
    }

    public int getNbPrankGroups()
    {
        return nbPrankGroups;
    }

    public PrankMail[] getPrankMails()
    {
        return prankMails;
    }

    public String[] getVictimsMails()
    {
        return victimsMails;
    }
}
