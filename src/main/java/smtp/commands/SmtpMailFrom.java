package smtp.commands;

import smtp.SmtpResponseType;

public class SmtpMailFrom extends SmtpCommand
{
    private final String sender;

    public SmtpMailFrom( String sender )
    {
        super("250");
        this.sender = sender;
    }

    @Override
    public String toString()
    {
        return String.format( "MAIL FROM:<%s>", sender );
    }
}
