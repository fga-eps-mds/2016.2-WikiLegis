package gppmds.wikilegis.dao;

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

        try {
            url = new URL(params[0]);
        } catch (MalformedURLException e) {
            Log.e(params[0]," MALFORMED URL");
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Log.e("Connection failed",",Internet may be off");
        }

        try{
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            text = RequestTools.readStream(in);
        } catch (IOException e) {
            Log.e("Connection faild",", Try later");
        } finally {
            urlConnection.disconnect();
        }

        return text;
    }

    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);
        //Here you can use the data according to what you want
        //Log.d("RESPONSE ------ :", result);

    }

}