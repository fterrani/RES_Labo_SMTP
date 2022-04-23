package config;

public class PrankMail
{
    private final String subject;
    private final String body;

    public PrankMail( String subject, String body )
    {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getBody()
    {
        return body;
    }
}
