package smtp.commands;

import smtp.SmtpResponseType;

public class SmtpEhlo extends SmtpCommand
{
    private final String domain;

    public SmtpEhlo( String domain )
    {
        super(SmtpResponseType.POSITIVE_COMPLETION);
        this.domain = domain;
    }

    @Override
    public String toString()
    {
        return String.format("EHLO %s", domain );
    }
}
