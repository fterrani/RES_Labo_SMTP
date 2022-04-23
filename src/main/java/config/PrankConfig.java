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
        // checking port number
        if ( smtpPort <= 0 || smtpPort > 65535 )
            throw new IllegalArgumentException("Invalid port number: " + smtpPort);

        // checking compatibility between the number of groups and the number of victim e-mail addresses
        int maxCreatableGroups = victimsMails.length / 3;
        if ( maxCreatableGroups < nbPrankGroups )
        {
            throw new IllegalArgumentException(
                String.format(
                    "Incompatible number of groups and victim e-mail addresses: at most %d group(s) can be created" +
                    " using the %d victim addresses whereas %d groups were asked for.",
                    maxCreatableGroups, victimsMails.length, nbPrankGroups
                )
            );
        }

        // checking victim e-mail addresses
        for ( String victimMail : victimsMails )
        {
            if ( ! victimMail.contains( "@" ) )
            {
                throw new IllegalArgumentException(
                    String.format( "Invalid victim e-mail address: %s", victimMail )
                );
            }
        }

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
