import config.PrankConfigReader;
import config.PrankConfig;

import java.io.*;
import java.nio.file.Path;

public class Program
{
    public static void main( String[] args ) throws IOException
    {
        // TODO Ask if JSON is ok

        // TODO add missing commands ECE
            // RCPT TO, DATA, RSET, QUIT
        // TODO implements mail sending ECE
        // TODO encoding subject and body => after
        // => TODO ask witch encoding the prof wants

        Path configPath = Path.of("config-files", "prank-config.json");
        PrankConfig config = PrankConfigReader.readPrankConfig( configPath );
        System.out.println();
    }
}
