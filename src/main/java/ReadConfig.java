/**
 * Created by Лера on 23.01.2017.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig
{
    public ReadConfig(File f) throws IOException
    {
        Properties props = new Properties();
        props.load(new FileInputStream(f));
        Main m = new Main();
        m.fileName = props.getProperty("FileName");
        m.serverIP= props.getProperty("ServerIP");
        m.keyspace = props.getProperty("Keyspace");
        m.login = props.getProperty("Login");
        m.password = props.getProperty("Password");
    }
}