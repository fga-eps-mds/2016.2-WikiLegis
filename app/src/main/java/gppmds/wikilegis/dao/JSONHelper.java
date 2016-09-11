package gppmds.wikilegis.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentTypesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.SegmentTypes;

/**
 * Created by marcelo on 9/8/16.
 */
public class JSONHelper {

    public static List<Bill> billListFromJSON(String billList) throws JSONException, BillException {

        List<Bill> billListApi = new ArrayList<>();

        JSONObject bills = new JSONObject(billList);
        JSONArray results = bills.getJSONArray("results");

        for(int i=0; i<results.length(); i++){
            JSONObject f = results.getJSONObject(i);

            Bill billAux = new Bill(f.getInt("id"),
                                    f.getString("title"),
                                    f.getString("epigraph"),
                                    f.getString("status"),
                                    f.getString("description"),
                                    f.getString("theme"));

            JSONArray segments = f.getJSONArray("segments");

            for(int j = 0; j < segments.length(); j++) {
                billAux.setSegments(segments.getInt(j));
            }

            billListApi.add(billAux);
        }

        return billListApi;
    }

    public static String getJSONObjectApi(final String URL) {
        String getApi = null;

        GetRequest request = new GetRequest();

        getApi = request.execute(URL).toString();

        try {
            getApi = request.get().toString();
        } catch (ExecutionException e){
            Log.d("ExecutionException", URL);
            //Não faço ideia do que fazer
        } catch (InterruptedException e){
            Log.d("InterruptedException", URL);
            //Não faço ideia do que fazer
        }
        return getApi;
    }
    
    public static List<SegmentTypes> segmentTypesListFromJSON(List<SegmentTypes> segmentTypesListApi)
                                                              throws JSONException, SegmentTypesException {

        //segmentTypesListApi = new ArrayList<>();

        String url = "http://wikilegis.labhackercd.net/api/segment_types/";

        do {
            String segmentTypesList = getJSONObjectApi(url);

            JSONObject segmentTypes = new JSONObject(segmentTypesList);
            JSONArray results = segmentTypes.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject f = results.getJSONObject(i);

                SegmentTypes segmentTypeAux = new SegmentTypes(f.getInt("id"),
                        f.getString("name"));

                segmentTypesListApi.add(segmentTypeAux);
            }

            String nextUrl = segmentTypes.getString("next");
            url = nextUrl;

        } while(!url.equals("null"));

        for (int i = 0; i < segmentTypesListApi.size(); i++) {
            Log.d("id: ", segmentTypesListApi.get(i).getId().toString());
            Log.d("name: ", segmentTypesListApi.get(i).getName());
        }

        return segmentTypesListApi;
    }
}

