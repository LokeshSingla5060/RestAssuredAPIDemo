package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadProperties {
	
//	public static void main(String[] args) {
//		System.out.println(ReadProperties.readProperties("TestDataPath"));
//	}
	
	public static String readProperties(String props)
    {
        Properties pr = new Properties();
        FileInputStream f= null;
        try
        {
            f= new FileInputStream(System.getProperty("user.dir")+"/resources/config.properties");
            pr.load(f);
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        String propvalue=pr.getProperty(props);
        return propvalue;
    }

}
