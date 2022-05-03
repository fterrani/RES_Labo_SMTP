package smtp.commands;

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
