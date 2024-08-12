package Configs;

import java.io.InputStream;
import java.util.Properties;

public class GTKconfig {
    private static Properties properties;

    static {
        try (InputStream input = GTKconfig.class.getClassLoader().getResourceAsStream("GetToKnow.properties")) {
            properties = new Properties();
            properties.load(input);
            //System.out.println("Loaded properties: " + properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getSearchBarId() {
        return properties.getProperty("SearchBar_Id");
    }
    public static String getProfileCardXpath() {
        return properties.getProperty("ClickCard_xpath");
    }
    public static String getGetToKnowformId(){
    	return properties.getProperty("AddGetToKnow_Id");
    }
    public static String getWhomToKnowPersonXpath() {
    	return properties.getProperty("WhomToKnow_xpath");
    }
    public static String getTypeaheadListXpath() {
    	return properties.getProperty("Typeaheadlist_xpath");
    }
    public static String getReasonXpath() {
    	return properties.getProperty("Reason_xpath");
    }
    public static String getAddButtonXpath() {
    	return properties.getProperty("AddButton_xpath");
    }
}
