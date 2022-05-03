package smtp.commands;

import smtp.SmtpClient;
import smtp.SubjectHeaderRfc1342Encoder;

public class SmtpDataContent extends SmtpCommand {
    private final String subject;
    private final String body;
    private final String sender;
    private final String[] receivers;



    public SmtpDataContent(String subject, String body, String sender, String[] receivers) {
        super("250");
        this.subject = subject;
        this.body = body;
        this.sender = sender;
        this.receivers = receivers;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("From: ").append(sender).append(SmtpClient.END_LINE);
        s.append("To: ").append(receivers[0]);
        for (int i = 1; i < receivers.length; ++i)
            s.append(", ").append(receivers[i]);
        s.append(SmtpClient.END_LINE);
        s.append("Subject: ").append( SubjectHeaderRfc1342Encoder.encode(subject) );
        s.append(SmtpClient.END_LINE);
        s.append("Content-Type: text/plain; charset=utf-8");
        s.append(SmtpClient.END_LINE).append(SmtpClient.END_LINE);
        s.append( body.replaceAll("\r\n\\.", "\r\n..") );
        s.append(SmtpClient.END_LINE).append(".");
        return s.toString();
    }
}
