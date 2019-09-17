package bbdn.sample.b2rest.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import blackboard.platform.plugin.PlugInException;
import blackboard.platform.plugin.PlugInUtil;

public class CredentialUtil {

    public static void saveCreds( String unencodedCreds )
    {
        BufferedWriter writer = null;
        File myConfigFile = getConfigFile();
        
        try {
            String encodedCreds = Base64.getEncoder().encodeToString(unencodedCreds.getBytes());
            
            writer = new BufferedWriter(new FileWriter(myConfigFile));
            writer.write(encodedCreds);
        
        } catch (IOException e) {
            e.printStackTrace();
            return;
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

    public static HashMap<String,String> readCreds () {
        BufferedReader reader = null;
        HashMap<String,String> creds = new HashMap<String,String>();
        File myConfigFile = getConfigFile();
        
        try {
            reader = new BufferedReader(new FileReader(myConfigFile));
            String encodedCreds = reader.readLine();
            byte[] decodedBytes = Base64.getDecoder().decode(encodedCreds);
            String decodedCreds = new String(decodedBytes);

            String[] credsArray = decodedCreds.split(":", 2);

            if ( credsArray.length == 2 ) {
                creds.put("key", credsArray[0]);
                creds.put("secret", credsArray[1]);
            }

            return creds;
        } catch (IOException e) {
            HashMap<String,String> error = new HashMap<String,String>();
            error.put("error", e.getMessage());
            return error;
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

    public static boolean isAuthorized (String authHeader) {
        File myConfigFile = getConfigFile();
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(myConfigFile));
            String creds = reader.readLine();
           
            return authHeader.equals(creds);
        } catch (IOException e) {
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

    private static File getConfigFile() {
        File myConfigDir = null;
        try {
            myConfigDir = PlugInUtil.getConfigDirectory("bbdn", "CourseTocRest");
        } catch (PlugInException e) {
            e.printStackTrace();
		}
        return new File(myConfigDir, "config.txt");
    }
}