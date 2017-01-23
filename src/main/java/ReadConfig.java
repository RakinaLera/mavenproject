import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Лера on 23.01.2017.
 */

public class ReadConfig
{
    public final String fileName;
    public final String serverIP;
    public final String keyspace;
    public final String login;
    public final String password;

    public ReadConfig(File f) throws IOException
    {
        Properties props = new Properties();
        props.load(new FileInputStream(f));
        fileName = props.getProperty("FileName");
        serverIP= props.getProperty("ServerIP");
        keyspace = props.getProperty("Keyspace");
        login = props.getProperty("Login");
        password = props.getProperty("Password");
    }
}