package smtp.commands;

import smtp.SmtpClient;
import smtp.SubjectHeaderRfc1342Encoder;

public class SmtpDataContent extends SmtpCommand {
    private final String subject;
    private final String body;



    public SmtpDataContent(String subject, String body) {
        super("250");
        this.subject = subject;
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Subject: ").append( SubjectHeaderRfc1342Encoder.encode(subject) );
        s.append(SmtpClient.END_LINE);
        s.append("Content-Type: text/plain; charset=utf-8");
        s.append(SmtpClient.END_LINE).append(SmtpClient.END_LINE);
        s.append( body.replaceAll("\r\n\\.", "\r\n..") );
        s.append(SmtpClient.END_LINE).append(".");
        return s.toString();
    }
}
