package gppmds.wikilegis.api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Request extends AsyncTask<String, String, String> {


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
            text = readStream(in);
        } catch (IOException e) {
            Log.e("Connection faild",", Try later");
        } finally {
            urlConnection.disconnect();
        }

        return text;
    }



    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Here you can use the data according to what you want
        Log.d("RESPONSE ------ :",result);
    }

    // Treats the response
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}