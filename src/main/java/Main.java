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
    public static void main(String[] args) throws IOException {
        String fileName = "InitialData.txt";
        File file = new File(fileName);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            List<String> data = new ArrayList<String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
            RxLogic rxLogic = new RxLogic();

            if (rxLogic.correctData(data)) {
                Cluster cluster = Cluster.builder()
                        .addContactPoints(data.get(1))
                        .withCredentials(data.get(3),data.get(4))
                        .withPort(9042)
                        .build();
                Session session = cluster.connect(data.get(2));
                rxLogic.processData(data.get(0), session);
                cluster.close();
            }
            else
                System.out.println("В файле содержатся некорректные данные!");
        }
        else
            System.out.println("Файл с исходными данными не найден!");
    }
}