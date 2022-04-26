package smtp.commands;

import smtp.SmtpResponseType;

public class SmtpData extends SmtpCommand {
     public SmtpData(String subject, String body) {
        super("354");
    }

    @Override
    public String toString()
    {
        return "DATA";
    }
}
