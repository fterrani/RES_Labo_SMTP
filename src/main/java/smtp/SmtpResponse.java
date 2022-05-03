package smtp;

public class SmtpResponse
{
    private final String code;
    private final String text;

    public SmtpResponse(String code, String text)
    {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
