import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Лера on 19.01.2017.
 */
public class Main {

    public static String fileName = null;
    public static String serverIP = null;
    public static String keyspace = null;
    public static String login = null;
    public static String password = null;

    public static void main(String[] args) throws IOException {
        File f = new File("InitialData.ini");
        if (f.exists()) {
            new ReadConfig(f);
            RxLogic rxLogic = new RxLogic();
            if (rxLogic.correctData(fileName, serverIP, keyspace, login, password)) {
                Cluster cluster = Cluster.builder()
                        .addContactPoints(serverIP)
                        .withCredentials(login, password)
                        .withPort(9042)
                        .build();
                Session session = cluster.connect(keyspace);
                rxLogic.processData(fileName, session);
                cluster.close();
            } else
                System.out.println("В файле содержатся некорректные данные!");
        }
        else
            System.out.println("Файл с исходными данными не найден");
    }
}