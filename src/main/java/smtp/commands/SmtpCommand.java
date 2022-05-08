package smtp.commands;

public abstract class SmtpCommand
{
    private final String expectedResponseRegex;

    public SmtpCommand(String expectedResponseCode)
    {
        expectedResponseRegex = expectedResponseCode;
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
