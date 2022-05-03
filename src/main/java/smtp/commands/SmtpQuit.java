package smtp.commands;

public class SmtpQuit extends SmtpCommand {

    public SmtpQuit() {
        super("221");
    }

    @Override
    public String toString()
    {
        return "QUIT";
    }
}
