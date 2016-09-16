package gppmds.wikilegis.dao;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//Ainda não funciona
public class PostRequest extends AsyncTask< String, Void, Void>{

    @Override
    protected Void doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            Log.e(params[0]," MALFORMED URL");
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            //writeStream(out);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            RequestTools.readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return null;
    }


}
