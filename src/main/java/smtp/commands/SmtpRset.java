package smtp.commands;

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
