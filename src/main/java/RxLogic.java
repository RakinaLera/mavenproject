import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import rx.Observable;
import rx.Subscriber;

import java.io.BufferedReader;
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
                //System.out.println(str);
                writeCassandra(partitionString(str), ses);
            }
        }
        else System.out.println("Incorrect line: " + str);
    }

    private void writeCassandra(InitialData ob, Session ses) {
        ses.execute("INSERT INTO practice_2017.practice_2017 (pspaynum,account,errcode,errtext,flags,indate,instatus,parentpayid,payamount,paytype,servcode,servtype,termid,totalpayamount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                ob.psPayNum.toString(),
                ob.account.toString(),
                ob.errCode.toString(),
                ob.errText.toString(),
                ob.flags.toString(),
                ob.inDate.toString(),
                ob.inStatus.toString(),
                ob.parentPayId.toString(),
                ob.payAmount.toString(),
                ob.payType.toString(),
                ob.servCode.toString(),
                ob.servType.toString(),
                ob.termId.toString(),
                ob.totalPayAmount.toString());
    }

    private InitialData partitionString(String initialDataString) {
        String[] s = initialDataString.split("\\|", 14);
        InitialData obj = new InitialData(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9],
                s[10], s[11], s[12], s[13].substring(0, s[13].length() - 1));
        return obj;
    }
}
