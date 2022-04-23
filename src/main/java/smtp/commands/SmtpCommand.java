package smtp.commands;

import smtp.SmtpResponseType;

import java.util.regex.Pattern;

// TODO Create subclasses for all smtp.commands
public abstract class SmtpCommand
{
    private String expectedResponseRegex = null;

    public SmtpCommand(SmtpResponseType expectedResponseType)
    {
        if (expectedResponseType.equals(SmtpResponseType.POSITIVE_COMPLETION))
            expectedResponseRegex = "2..";
        else if (expectedResponseType.equals(SmtpResponseType.POSITIVE_INTERMEDIATE))
            expectedResponseRegex = "3..";
        else if (expectedResponseType.equals(SmtpResponseType.NEGATIVE_COMPLETION_TRANSIENT))
            expectedResponseRegex = "4..";
        else if (expectedResponseType.equals(SmtpResponseType.NEGATIVE_COMPLETION_PERMANENT))
            expectedResponseRegex = "5..";
        else
            throw new IllegalArgumentException();
    }

    public SmtpCommand(String expectedResponseCode)
    {
        if ( expectedResponseCode.length() != 3)
            throw new IllegalArgumentException();

        expectedResponseRegex = Pattern.quote( expectedResponseCode );
    }

    public SmtpCommand()
    {
    }

    public boolean isResponseCodeExpected(String responseCode)
    {
        if ( expectedResponseRegex != null)
            return responseCode.matches( expectedResponseRegex );
        else
            return true;
    }

    public String getExpectedResponseRegex()
    {
        return expectedResponseRegex;
    }
}
