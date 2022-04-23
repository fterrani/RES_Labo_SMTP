package smtp;

public class SmtpResponse
{
    public String code;
    public String text;

    public SmtpResponse(String code, String text)
    {
        this.code = code;
        this.text = text;
    }
}
