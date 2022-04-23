package smtp.commands;

import smtp.SmtpResponseType;

public class SmtpMailFrom extends SmtpCommand
{
    private final String sender;

    public SmtpMailFrom( String sender )
    {
        super( SmtpResponseType.POSITIVE_COMPLETION);
        this.sender = sender;
    }

    @Override
    public String toString()
    {
        return String.format( "MAIL FROM:<%s>", sender );
    }
}
