package gppmds.wikilegis.dao.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostRequest extends AsyncTask<String, String, String>{

    private String url = "";
    private Context context;
    private int response = 400;

    public PostRequest(Context context, String url){
        this.url = url;
        this.context = context;
    }

    protected void onPreExecute() {
    }

    protected String doInBackground(String... params) {
        String httpResult = null;
        HttpURLConnection urlConnection = null;

        try {
            String http = url;
            urlConnection = setBody(http,params[1]);

            Log.d("Info", "Connenction body set");

            httpResult = makeResult(urlConnection, params[0]);
            Log.d("Info", "RESULT: " + httpResult);

        } catch (MalformedURLException e) {
            Log.d("Error", "URL com problema");
        } catch (IOException e) {
            Log.d("Error", "Houve no erro post");
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } finally{
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return httpResult;
    }

    protected void onPostExecute(String bodyTextResponse) {
        super.onPostExecute(bodyTextResponse);
    }

    private HttpURLConnection setBody(String http, String requestType) throws IOException {
        URL url = new URL(http);
        HttpURLConnection urlConnection;
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", requestType);
        urlConnection.connect();

        return urlConnection;
    }
    private String makeResult(HttpURLConnection urlConnection, String bodyMessage) throws IOException {

        OutputStream out = urlConnection.getOutputStream();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

        writer.write(bodyMessage);
        writer.flush();
        writer.close();

        String result = RequestTools.readPostStream(urlConnection);

        response = urlConnection.getResponseCode();
        Log.d("Info", urlConnection.getResponseMessage()+ "   "+ urlConnection.getResponseCode()+ "   " + result);


        return result;
    }
    public int getResponse() {
        return response;
    }
}
