package gppmds.wikilegis.dao;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static gppmds.wikilegis.dao.RequestTools.readStream;

//Ainda não funciona
public class PostRequest extends AsyncTask<String, Void, String>{

    private Exception exception;
    StringBuilder sb = new StringBuilder();

    protected void onPreExecute() {
        //progressBar.setVisibility(View.VISIBLE);
        //responseView.setText("");
    }

    protected String doInBackground(String... params) {
        //JSONObject jsonParam = new JSONObject();

        try {
                /*Log.d("OLAR"," TO AQUIIII");
                URL url = new URL(params[0]);
                //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


                    Log.d("OLAR"," TO AQUIIII2");
                    URLConnection urlConn;
                    DataOutputStream printout;
                    DataInputStream input;

                    urlConn = url.openConnection();

                    urlConn.setDoInput (true);
                    urlConn.setUseCaches (false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.setRequestMethod("POST");
                    Log.d("OLAR"," TO AQUIIII3");
                    jsonParam.put("email", params[1]);
                    jsonParam.put("first_name", params[2]);
                    jsonParam.put("last_name", params[3]);
                    jsonParam.put("password", params[4]);
                    Log.d("OLAR"," TO AQUIIII4");
                    printout = new DataOutputStream(urlConn.getOutputStream());
                    printout.writeBytes(URLEncoder.encode(jsonParam.toString(),"UTF-8"));
                    printout.flush ();
                    printout.close ();

                    Log.d("OLAR"," TO AQUIIII5");
                    urlConn.connect();

                    //Create JSONObject here


//                    OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
//                    out.write(jsonParam.toString());
//                    out.close();

                    /*int HttpResult = urlConnection.getResponseCode();
                    if(HttpResult ==HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                urlConnection.getInputStream(),"utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();

                        System.out.println(""+sb.toString());

                    }else{
                        System.out.println(urlConnection.getResponseMessage());
                    }

                    /*StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();*/

            StringBuilder sb = new StringBuilder();
            Log.d("OLAR"," TO AQUIIII");
            String http = params[0];

            HttpURLConnection urlConnection=null;
            Log.d("OLAR"," TO AQUIIII1");
            try {
                URL url = new URL(http);
                urlOperations(urlConnection, url);
                Log.d("OLAR"," TO AQUIIII3");

                //Create JSONObject here
                JSONObject jsonParam = new JSONObject();
                createJsonObjectWithParams(jsonParam, http);
                Log.d("JSON",jsonParam.toString());

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonParam.toString());
                out.close();
                Log.d("OLAR"," TO AQUIIII4");
                int HttpResult =urlConnection.getResponseCode();
                Log.d("OLAR", "RESULT: " +HttpResult);
                if (HttpResult == HttpURLConnection.HTTP_OK) {
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
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            }
            catch (IOException e) {

                e.printStackTrace();
            } finally{
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

    private void createJsonObjectWithParams(JSONObject jsonParam, String... params) throws JSONException {
        jsonParam.put("email", params[1]);
        jsonParam.put("first_name", params[2]);
        jsonParam.put("last_name", params[3]);
        jsonParam.put("password", params[4]);
    }

    private void urlOperations(HttpURLConnection urlConnection, URL url) throws IOException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(10000);
        urlConnection.setRequestProperty("Content-Type","application/json");

        Log.d("OLAR"," TO AQUIIII2");
        urlConnection.setRequestProperty("Host", "http://127.0.0.1:8000/api/user/create/");
        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
