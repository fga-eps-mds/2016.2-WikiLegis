package gppmds.wikilegis.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marcelo on 9/8/16.
 */
public class JSONHelper {

    public static void processRequest(String jsonString)throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject bills = jsonObject.getJSONObject("count");
        JSONArray arrayBills = bills.getJSONArray("results");

        for(int index =0 ; index<arrayBills.length();index++){

            JSONObject requestBill = arrayBills.getJSONObject(index);
            Log.d("id :" , requestBill.getString("title"));


        }
    }

}
