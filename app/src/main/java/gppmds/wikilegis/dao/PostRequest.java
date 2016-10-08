package gppmds.wikilegis.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import gppmds.wikilegis.model.User;

public class PostRequest extends AsyncTask<Void, Void, Integer>{
    private String url = "";
    private Exception exception;
    private Context context;
    private JSONObject objectToBeSend;

    public PostRequest(final Context context, JSONObject jsonObject, String url){
        this.objectToBeSend = jsonObject;
        this.url = url;
        this.context = context;
    }

    protected void onPreExecute() {
        //Empty constructor;
    }

    protected Integer doInBackground(final Void... params) {
        int httpResult = 400;
        try {
            String http = url;

            HttpURLConnection urlConnection = null;
            try {
                urlConnection = setBody(http);

                Log.d("Info", "Connenction body set");

                httpResult = makeResult(urlConnection, objectToBeSend);
                Log.d("Info", "RESULT: " + httpResult);
            }
            catch (MalformedURLException e) {
                Log.d("Error", "URL com problema");
            }
            catch (IOException e) {
                Log.d("Error", "Houve no post");
            }
            finally{
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
        return httpResult;
    }

    protected void onPostExecute(final Integer response) {
        if (response == 201) {
            Toast.makeText(context, "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Email já cadastrado!", Toast.LENGTH_SHORT).show();
        }
        Log.i("INFO", ""+ response);
    }

    private HttpURLConnection setBody(String http) throws IOException {
        URL url = new URL(http);
        HttpURLConnection urlConnection;
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        return urlConnection;
    }

    private int makeResult(HttpURLConnection urlConnection, JSONObject jsonParam) throws IOException {
        OutputStream out = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.write(jsonParam.toString());
        writer.flush();
        writer.close();
        urlConnection.connect();
        Log.d("Info", "Connection sucess");
        return urlConnection.getResponseCode();
    }
}
