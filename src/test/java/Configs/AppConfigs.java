package Configs;

import java.io.InputStream;
import java.util.Properties;

public class AppConfigs {

    private static Properties properties;

    static {
        try (InputStream input = AppConfigs.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties = new Properties();
            properties.load(input);
            //System.out.println("Loaded properties: " + properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUsername() {
        return properties.getProperty("username");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
    
    public static String getJDBCurl() {
        return properties.getProperty("jdbcurl");
    }
    public static String getOutlookUsername() {
        return properties.getProperty("outlookusername");
    }
    public static String getOutlookPassword() {
        return properties.getProperty("outlookpassword");
    }
    public static String getDevLinkURL() {
        return properties.getProperty("devlinkurl");
    }
    public static String getDevUserAdminURL() {
        return properties.getProperty("devuseradminurl");
    }
    public static String getDevDealManagerURL() {
    	return properties.getProperty("devdealmanagerurl");
    }
}

