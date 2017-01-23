import com.datastax.driver.core.Session;
import rx.Observable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Лера on 19.01.2017.
 */
public class RxLogic {
    public Observable<String> getObservable(String fileName) throws IOException {
        return Observable.create(subscribe -> {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    subscribe.onNext(line);
                }
                if(line == null)
                {
                    subscribe.onCompleted();
                }
            } catch (IOException e) {
                subscribe.onError(e);
            }
        });
    }

    public void processData(String fileName, Session ses) throws IOException{
        getObservable(fileName).subscribe(x -> correct(x, ses));
    }

    private void correct(String str, Session ses) {
        if (str.charAt(0) != '#' && str != null) {
            int k = 0;
            for (char element : str.toCharArray())
                if (element == '|')
                    k++;
            if (k == 14) {
                //writeCassandra(partitionString(str), ses);
            }
        }
        else System.out.println("Incorrect line: " + str);
    }

    private void writeCassandra(InitialData ob, Session ses) {
        ses.execute("INSERT INTO practice_2017.practice_2017 (pspaynum,account,errcode,errtext,flags,indate,instatus,parentpayid,payamount,paytype,servcode,servtype,termid,totalpayamount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) IF NOT EXISTS",
                ob.psPayNum, ob.account, ob.errCode, ob.errText, ob.flags, ob.inDate, ob.inStatus,
                ob.parentPayId, ob.payAmount, ob.payType, ob.servCode, ob.servType,ob.termId,ob.totalPayAmount);
    }

    private InitialData partitionString(String initialDataString) {
        String[] s = initialDataString.split("\\|", 14);
        InitialData obj = new InitialData(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9],
                s[10], s[11], s[12], s[13].substring(0, s[13].length() - 1));
        return obj;
    }

    public boolean correctData(String name, String IP, String space, String login, String password) {
        boolean f = false;
        if (name != null && IP != null && space != null && login != null && password != null){
            File file = new File(name);
            if (file.exists()) {
                f = true;
                int k = 0;
                for (char element : IP.toCharArray())
                    if (element == '.')
                        k++;
                if (k != 3) {
                    f = false;
                }
            }
        }
        return f;
    }
}
