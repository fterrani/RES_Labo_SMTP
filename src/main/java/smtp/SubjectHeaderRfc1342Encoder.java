package smtp;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SubjectHeaderRfc1342Encoder
{
    public static String encode(String text)
    {
        byte[] utf8Bytes = text.getBytes( StandardCharsets.UTF_8 );
        String base64 = Base64.getEncoder().encodeToString( utf8Bytes );
        return  "=?utf-8?B?" + base64 + "?=";
    }
}
