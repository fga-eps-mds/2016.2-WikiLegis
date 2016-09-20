package gppmds.wikilegis.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import gppmds.wikilegis.model.User;
import gppmds.wikilegis.view.MainActivity;
import gppmds.wikilegis.view.RegisterUserFragment;

import static gppmds.wikilegis.dao.RequestTools.readStream;

//Ainda não funciona
public class PostRequest extends AsyncTask<Void, Void, String>{
    private final static String url = "wikilegis-staging.labhackercd.net/api/user/create/";
    private Exception exception;
    //StringBuilder sb = new StringBuilder();
    private User user;
    private Context context;

    public PostRequest(User user, Context context){
        this.user = user;
        this.context = context;
    }

    protected void onPreExecute() {
        //progressBar.setVisibility(View.VISIBLE);
        //responseView.setText("");
    }

    protected String doInBackground(Void... params) {
        //JSONObject jsonParam = new JSONObject();

        try {

            StringBuilder sb = new StringBuilder();
            Log.d("OLAR"," TO AQUIIII");
            String http = url;


            HttpURLConnection urlConnection=null;
            Log.d("OLAR"," TO AQUIIII1");
            try {
                URL url = new URL(http);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");

                Log.d("Info","Connenction body set");

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("email", user.getEmail());
                jsonParam.put("first_name", user.getFirstName());
                jsonParam.put("last_name", user.getLastName());
                jsonParam.put("password", user.getPassword());
                Log.d("JSON",jsonParam.toString());

                OutputStream out = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(jsonParam.toString());
                writer.flush();
                writer.close();
                urlConnection.connect();
                Log.d("Info","Connection sucess");

                int httpResult =urlConnection.getResponseCode();
                Log.d("Info", "RESULT: " +httpResult);
                /*if(httpResult == 400){
                    Toast.makeText(context, "Email já cadastrado!", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(context, "Usuário cadastrado!", Toast.LENGTH_SHORT);
                }
                /*if(HttpResult == HttpURLConnection.HTTP_OK){
                    Log.d("OLAR"," TO AQUIIII5");
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        Log.d("OLAR"," TO AQUIIII6");
                        sb.append(line + "\n");
                    }
                    br.close();
                    Log.d("OLAR"," TO AQUIIII7");
                    System.out.println(""+sb.toString());

                }else{
                    Log.d("OLAR"," TO AQUIIII8");
                    System.out.println(urlConnection.getResponseMessage());
                }*/

            } catch (MalformedURLException e) {
                Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT);
            }
            catch (IOException e) {
                Toast.makeText(context, "Ocorreu um erro", Toast.LENGTH_SHORT);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                Toast.makeText(context, "Ocorreu um erro", Toast.LENGTH_SHORT);
            }finally{
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }

        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
        return "";
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        //progressBar.setVisibility(View.GONE);
        Log.i("INFO", response);
        //responseView.setText(response);
    }
}
