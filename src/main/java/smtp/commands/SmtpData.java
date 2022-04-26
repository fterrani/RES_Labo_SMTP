package smtp.commands;

import smtp.SmtpResponseType;

public class SmtpData extends SmtpCommand {
     public SmtpData() {
        super("354");
    }

    @Override
    public String toString()
    {
        return "DATA";
    }
}
