package smtp;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

// TODO Ask teacher how far we should go for the encoding (notably, should line length constraint be respected?)
public class SubjectHeaderRfc1342Encoder
{
    private static boolean doesNotRequireQEncoding(int codePoint)
    {
        return 32 <= codePoint
                && codePoint <= 126
                && codePoint != (int) '='
                && codePoint != (int) '?'
                && codePoint != (int) '_'
                && codePoint != (int) ' ';
    }

    public static String encode(String text)
    {
        StringBuilder encodedText = new StringBuilder();

        for (int i = 0; i < text.length(); ++i)
        {
            encodedText.append( qEncodeChar( text, i ) );
        }
        return  "=?utf-8?Q?" + encodedText.toString() + "?=";
    }

    public static String qEncodeChar(String text, int index)
    {
        int codePoint = text.codePointAt( index );

        if ( codePoint == (int) ' ')
            return "_"; // or "=20" for more compatibility
        else if ( doesNotRequireQEncoding( codePoint ) )
            return text.substring( index, index + 1 );

        byte[] utf8CharBytes = text.substring( index, index + 1 ).getBytes( StandardCharsets.UTF_8 );

        StringBuilder sb = new StringBuilder();

        for (byte b : utf8CharBytes)
        {
            sb.append( String.format("=%2X", b) );
        }

        return sb.toString();
    }
}
