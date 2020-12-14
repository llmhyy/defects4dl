package JavaBasedConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configs {
    private static Properties prop = new Properties();
    private final static String KEY_PATH = "path";
    private  final  static  String KEY_JAVA_HOME="JAVA_HOME";
    private static String JAVA_HONE="";
    public static String envPath = "";
    private final static String CONFIGPATH = "env.properties";

    public static void refresh() {
        try (
                InputStream inStream = new FileInputStream(new File(CONFIGPATH));) {
            prop.load(inStream);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        envPath = prop.getProperty(KEY_PATH);
        JAVA_HONE = prop.getProperty(KEY_JAVA_HOME);
        envPath=JAVA_HONE+";"+envPath;
    }

}
