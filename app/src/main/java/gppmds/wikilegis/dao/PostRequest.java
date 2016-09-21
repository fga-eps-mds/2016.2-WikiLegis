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
    private final static String url = "http://wikilegis-staging.labhackercd.net/api/user/create/";
    private Exception exception;
    private User user;
    private Context context;

    public PostRequest(final User user, final Context context){
        this.user = user;
        this.context = context;
    }

    protected void onPreExecute() {
        //Empty constructor;
    }

    protected Integer doInBackground(final Void... params) {
        int httpResult = 400;
        try {

            StringBuilder sb = new StringBuilder();
            String http = url;

            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(http);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                Log.d("Info", "Connenction body set");

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("email", user.getEmail());
                jsonParam.put("first_name", user.getFirstName());
                jsonParam.put("last_name", user.getLastName());
                jsonParam.put("password", user.getPassword());

                OutputStream out = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(jsonParam.toString());
                writer.flush();
                writer.close();
                urlConnection.connect();
                Log.d("Info", "Connection sucess");

                httpResult = urlConnection.getResponseCode();
                Log.d("Info", "RESULT: " + httpResult);
            }
            catch (MalformedURLException e) {
                Log.d("Error", "URL com problema");
            }
            catch (IOException e) {
                Log.d("Error", "Houve no post");
            }
            catch (JSONException e) {
                Log.d("Error", "Informações com problema");
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
}
