package gppmds.wikilegis.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentTypesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentTypes;

/**
 * Created by marcelo on 9/8/16.
 */
public class JSONHelper {


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

    public static List<Bill> billListFromJSON(String billList ,List<Segment> aux) throws JSONException, BillException, SegmentException {

        List<Bill> billListApi = new ArrayList<>();

        JSONObject bills = new JSONObject(billList);
        JSONArray results = bills.getJSONArray("results");
        int id;
        Integer k;


        for(int i=0; i<results.length(); i++){
            JSONObject f = results.getJSONObject(i);


            id = f.getInt("id");
            k=BillController.countedTheNumberOfProposals(aux ,id);
            Bill billAux = new Bill(f.getInt("id"),
                                    f.getString("title"),
                                    f.getString("epigraph"),
                                    f.getString("status"),
                                    f.getString("description"),
                                    f.getString("theme"), k);
            Log.d("id qtd : " ,k.toString() );
            Log.d("id qtd : " ,f.getString("title"));

            JSONArray segments = f.getJSONArray("segments");

            for(int j = 0; j < segments.length(); j++) {

                billAux.setSegments(segments.getInt(j));

            }

            billListApi.add(billAux);
        }

        return billListApi;
    }

    public static List<SegmentTypes> segmentTypesListFromJSON(List<SegmentTypes> segmentTypesListApi)
                                                              throws JSONException, SegmentTypesException {

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

        return segmentTypesListApi;
    }

    public static List<Segment> segmentListFromJSON() throws JSONException, SegmentException {

        String url = "http://wikilegis.labhackercd.net/api/segments/";

        List<Segment> segmentListApi = new ArrayList<>();

        do {

            String segmentList = getJSONObjectApi(url);

            JSONObject segment = new JSONObject(segmentList);
            JSONArray results = segment.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject f = results.getJSONObject(i);
                        Log.d("huehue", "huheuhue");

                Segment segmentAux = new Segment(f.getInt("id"),
                        f.getInt("order"),
                        f.getInt("bill"),
                        f.getBoolean("original"),
                        //Mesma coisa das outras era replaced
                        f.getString("replaced").equals("null") ? 0 : f.getInt("replaced"),
                        //Tambem pode vir null, botei id pra testar e parent
                        f.getInt("id"),
                        f.getInt("id"),
                        //Pode vir null???? Botei id pra testar again e number
                        f.getInt("id"),
                        f.getString("id"),
                        //A partir desta está errada, botei apenas para testar.
                        f.getInt("id"),
                        f.getInt("id"),
                        f.getInt("id"));
                segmentListApi.add(segmentAux);
            }

            String nextUrl = segment.getString("next");
            url = nextUrl;

        } while(!url.equals("null"));

        return segmentListApi;
    }
}

