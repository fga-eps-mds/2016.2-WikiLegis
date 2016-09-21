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
import java.net.URL;

import gppmds.wikilegis.model.User;
public class PostRequest extends AsyncTask<Void, Void, Integer>{
    private final static String url = "http://wikilegis-staging.labhackercd.net/api/user/create/";
    private Exception exception;
    //StringBuilder sb = new StringBuilder();
    private User user;
    private Context context;

    public PostRequest(final User user, final Context context){
        this.user = user;
        this.context = context;
    }

    protected void onPreExecute() {
        //progressBar.setVisibility(View.VISIBLE);
        //responseView.setText("");
    }

    protected Integer doInBackground(final Void... params) {
        int httpResult = 400;
        try {

            StringBuilder sb = new StringBuilder();
            Log.d("OLAR", "TO AQUIIII");
            String http = url;



            HttpURLConnection urlConnection = null;
            Log.d("OLAR", "TO AQUIIII1");
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
                Log.d("JSON", jsonParam.toString());

                OutputStream out = urlConnection.getOutputStream();
                Log.d("OLAR", "TO AQUIIII1a");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                Log.d("OLAR", "TO AQUIIII1b");
                writer.write(jsonParam.toString());
                Log.d("OLAR", "TO AQUIIII1c");
                writer.flush();
                Log.d("OLAR", "TO AQUIIII1t");
                writer.close();
                Log.d("OLAR", "TO AQUIIII1g");
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
            Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show();
        }
        //progressBar.setVisibility(View.GONE);
        Log.i("INFO", ""+ response);
        //responseView.setText(response);
    }
}