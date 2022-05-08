package smtp.commands;

public class SmtpRcptTo extends SmtpCommand {
    private final String mail;


    public SmtpRcptTo(String mail) {
        super("25[01]");
        this.mail = mail;
    }

    @Override
    public String toString()
    {
        return String.format("RCPT TO:<%s>", mail );
    }
}
