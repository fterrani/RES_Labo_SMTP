package config;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class PrankConfigReader
{
    /**
     * Reads the JSON configuration found at configPath and creates a PrankConfig instance
     * @param configPath Path to configuration file
     * @return The configuration file represented by a PrankConfig instance
     * @throws IOException If an error occurs when reading the file
     */
    public static PrankConfig readPrankConfig( Path configPath ) throws IOException
    {
        String json = Files.readString(configPath, StandardCharsets.UTF_8);
        JSONObject root = new JSONObject( json );
        JSONObject smtpServer = root.getJSONObject( "smtpServer" );

        return new PrankConfig(
            smtpServer.getString("host"),
            smtpServer.getInt("port"),
            root.getInt("nbPrankGroups"),
            getPrankMailsArray( root.getJSONArray( "prankMails" ) ),
            getVictimsArray( root.getJSONArray( "victims" ) )
        );
    }

    private static PrankMail[] getPrankMailsArray(JSONArray jsonPrankMails)
    {
        PrankMail[] prankMails = new PrankMail[ jsonPrankMails.length() ];

        for (int i = 0; i < prankMails.length; ++i)
        {
            JSONObject jsonPrankMail = jsonPrankMails.getJSONObject( i );
            prankMails[i] = new PrankMail(
                    jsonPrankMail.getString( "subject" ),
                    jsonPrankMail.getString( "body" )
            );
        }

        return prankMails;
    }

    private static String[] getVictimsArray(JSONArray jsonVictims)
    {
        String[] victims = new String[ jsonVictims.length() ];

        for (int i = 0; i < victims.length; ++i)
        {
            victims[i] = jsonVictims.getString( i );
        }
        return victims;
    }
}
