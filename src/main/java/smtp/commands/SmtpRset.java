package smtp.commands;

import smtp.SmtpResponseType;

public class SmtpRset extends SmtpCommand {

    public SmtpRset() {
        super("250");
    }

    @Override
    public String toString()
    {
        return "RSET";
    }
}
