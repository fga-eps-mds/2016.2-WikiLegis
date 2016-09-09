package gppmds.wikilegis.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marcelo on 9/8/16.
 */
public class JSONHelper {


        public static void billListFromJSON(String typeSegList) throws JSONException {

            JSONObject segTypes = new JSONObject(typeSegList);
            JSONArray results = segTypes.getJSONArray("results");

            for(int i=0; i<results.length(); i++){
                JSONObject f = results.getJSONObject(i);

                Log.d("id: ", f.getString("id"));
                Log.d("SegType: ", f.getString("name"));
            }
        }
}
