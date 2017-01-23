import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import java.io.IOException;

/**
 * Created by Лера on 19.01.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String serverIP = "10.10.136.11";
        String keyspace = "practice_2017";
        String login = "practice_2017";
        String password = "saleurnalskdf";

        Cluster cluster = Cluster.builder()
                .addContactPoints(serverIP)
                .withCredentials(login, password)
                .withPort(9042)
                .build();
        Session session = cluster.connect(keyspace);

        /*Cluster cluster = Cluster.builder()
                .addContactPoints(args[1])
                .withCredentials(args[2], args[3])
                .build();
        Session session = cluster.connect(args[2]);*/

        RxLogic rxLogic = new RxLogic();
        rxLogic.processData("data.csv", session);
        cluster.close();
    }
}