package smtp.commands;

import smtp.SmtpResponseType;

public class SmtpRcptTo extends SmtpCommand {
    private final String mail;


    public SmtpRcptTo(String mail) {
        super(SmtpResponseType.POSITIVE_COMPLETION);
        this.mail = mail;
    }

    @Override
    public String toString()
    {
        return String.format("RCPT TO <%s>", mail );
    }
}
