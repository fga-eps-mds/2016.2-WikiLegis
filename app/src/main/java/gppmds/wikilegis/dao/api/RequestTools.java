package gppmds.wikilegis.dao.api;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public abstract class RequestTools {


    public static String readStream(final InputStream is) {

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
    public static String readPostStream(HttpURLConnection urlConnection) throws IOException {
        String line;
        StringBuffer jsonString = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        while((line = br.readLine()) != null){
            jsonString.append(line);
        }
        return jsonString.toString();
    }
}
