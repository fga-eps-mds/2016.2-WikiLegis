package gppmds.wikilegis.dao.api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetRequest extends AsyncTask<String, String, String> {


    protected String doInBackground(String... params) {
        String text = "";

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            text = RequestTools.readStream(in);
        } catch (MalformedURLException MalformedURL) {
            Log.e(params[0]," MALFORMED URL");
        } catch (IOException InOutException) {
            Log.e("Connection failed",",Internet may be off");
        } finally {
            urlConnection.disconnect();
        }

        return text;
    }

    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}