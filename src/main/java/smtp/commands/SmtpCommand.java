package smtp.commands;

import smtp.SmtpResponseType;

import java.util.regex.Pattern;

public abstract class SmtpCommand
{
    private final String expectedResponseRegex;

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

    /**
     * Checks if the provided response code is positive for the SmtpCommand instance
     * @param responseCode the response code to check
     * @return True if the the server response code is positive for the SmtpCommand instance, false otherwise
     */
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
