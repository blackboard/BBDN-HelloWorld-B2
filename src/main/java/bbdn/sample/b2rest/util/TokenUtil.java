package bbdn.sample.b2rest.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import bbdn.sample.b2rest.model.Token;
import blackboard.platform.plugin.PlugInException;
import blackboard.platform.plugin.PlugInUtil;

public class TokenUtil {

    private static String message = "TokenUtil: ";

    public static boolean cacheToken( Token token )
    {
        flushCache();

        BufferedWriter writer = null;
        File tokenCache = getTokenCache();
        
        try {
            String encodedToken = Base64.getEncoder().encodeToString(token.getToken().getBytes());
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiry = now.plusSeconds(Long.parseLong(token.getExpiry()));
            String tokenAndExpiry = encodedToken + " " + expiry;
            
            writer = new BufferedWriter(new FileWriter(tokenCache));
            writer.write(tokenAndExpiry);

            return true;
        
        } catch (IOException e) {
            message += "{ \"error\": \"" + e.getMessage() + "\" }";
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isAuthorized(String bearerToken) {
        message += "Checking Authorization... ";
        
        BufferedReader reader = null;
        File tokenCache = getTokenCache();
        
        try {
            message += "Get buffered reader from " + tokenCache.getName() + " ";
            reader = new BufferedReader(new FileReader(tokenCache));
            String tokenAndExpiry = reader.readLine();
            message += "tokenAndExpiry <" + tokenAndExpiry + "> ";
            String[] tokenArray = tokenAndExpiry.split(" ", 2);

            String encodedToken = "";
            LocalDateTime expiry = null;

            if ( tokenArray.length != 2 ) {
                message += "Token Array length is not 2, returning false";
                return false;
            }

            encodedToken = tokenArray[0];
            message += "encoded token <" + encodedToken + "> ";
            expiry = LocalDateTime.parse(tokenArray[1]);
            message += "expiry <" + expiry + "> ";

            byte[] decodedBytes = Base64.getDecoder().decode(encodedToken);
            String decodedToken = new String(decodedBytes);
            message += "decodedToken <" + decodedToken + "> ";
            message += "Time now <" + LocalDateTime.now() + "> ";

            if (bearerToken.equals(decodedToken) && LocalDateTime.now().isBefore(expiry)) {
                return true;
            } else {
                flushCache();
                return false;
            }
        } catch (IOException ioe) {
            message += "{ \"error\": \"" + ioe.getMessage() + "\" }";
            return false;
        } catch (Exception e) {
            message += "{ \"error\": \"" + e.getMessage() + "\" }";
            return false;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean revokeToken() {
        return flushCache();
    }

    public static String getMessage() {
        return message;
    }

    private static File getTokenCache() {
        File configDir = null;
        try {
            configDir = PlugInUtil.getConfigDirectory("bbdn", "CourseTocRest");
        } catch (PlugInException e) {
            e.printStackTrace();
		}
        return new File(configDir, "tokenCache.txt");
    }

    private static boolean flushCache() {
        return getTokenCache().delete();
    }
}