package smtp.commands;

public class SmtpEhlo extends SmtpCommand
{
    private final String domain;

    public SmtpEhlo( String domain )
    {
        super("250");
        this.domain = domain;
    }

    @Override
    public String toString()
    {
        return String.format("EHLO %s", domain );
    }
}
