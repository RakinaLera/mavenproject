import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.io.*;

/**
 * Created by Лера on 19.01.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("InitialData.ini");
        if (f.exists()) {
            ReadConfig rc = new ReadConfig(f);
            RxLogic rxLogic = new RxLogic();
            if (rxLogic.correctData(rc.fileName, rc.serverIP, rc.keyspace, rc.login, rc.password)) {
                Cluster cluster = Cluster.builder()
                        .addContactPoints(rc.serverIP)
                        .withCredentials(rc.login, rc.password)
                        .withPort(9042)
                        .build();
                Session session = cluster.connect(rc.keyspace);
                rxLogic.processData(rc.fileName, session);
                cluster.close();
            } else
                System.out.println("В файле содержатся некорректные данные!");
        }
        else
            System.out.println("Файл с исходными данными не найден");
    }
}