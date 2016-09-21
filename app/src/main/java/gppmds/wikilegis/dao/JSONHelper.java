package gppmds.wikilegis.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentTypeController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentTypesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentTypes;

public class JSONHelper {

    public static String getJSONObjectApi(final String URL) {
        String getApi = null;

        GetRequest request = new GetRequest();

        getApi = request.execute(URL).toString();

        try {
            getApi = request.get().toString();
        } catch (ExecutionException e){
            Log.d("ExecutionException", URL);
        } catch (InterruptedException e){
            Log.d("InterruptedException", URL);
        }
        return getApi;
    }

    public static List<Bill> billListFromJSON(final String billList,
                                              final List<Segment> aux) throws JSONException,
                                               BillException, SegmentException {
        int id;

        List<Bill> billListApi = new ArrayList<>();

        JSONObject bills = new JSONObject(billList);
        JSONArray results = bills.getJSONArray("results");

        Integer numberOfProposals, date;



        for (int index = 0; index < results.length(); index++){

            JSONObject jsonObject = results.getJSONObject(index);

            id = jsonObject.getInt("id");

            numberOfProposals = BillController.countedTheNumberOfProposals(aux, id);

            date= SegmentController.getMinDate(id);

            Bill billAux = BillController.getBill(numberOfProposals, date, jsonObject);

            JSONArray segments = jsonObject.getJSONArray("segments");

            for (int j = 0; j < segments.length(); j++) {

                billAux.setSegments(segments.getInt(j));
            }
            billListApi.add(billAux);
        }

        return billListApi;
    }

    public static List<String> emailListFromJSON(final List<String> emailListApi)
            throws JSONException {

        String url = "http://wikilegis.labhackercd.net/api/users/?api_key=9944b09199c62bcf9418ad846dd0e4bbdfc6ee4b&page=2";

        do {
            String emailList = getJSONObjectApi(url);
            JSONObject email = new JSONObject(emailList);
            JSONArray results = email.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject f = results.getJSONObject(i);

                String emailAux = f.getString("email");

                emailListApi.add(emailAux);
            }

            String nextUrl = email.getString("next");
            url = nextUrl;

        } while (!url.equals("null"));

        for (int i = 0; i < emailListApi.size(); i++)
            Log.d("email: ", emailListApi.get(i).toString());

        return emailListApi;
    }



    public static List<SegmentTypes> segmentTypesListFromJSON(final List<SegmentTypes> segmentTypesListApi)
                                                              throws JSONException, SegmentTypesException {

        String url = "http://beta.edemocracia.camara.leg.br/wikilegis/api/segment_types/";

        do {
            String segmentTypesList = getJSONObjectApi(url);

            JSONObject segmentTypes = new JSONObject(segmentTypesList);
            JSONArray results = segmentTypes.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);

                SegmentTypes segmentTypeAux = SegmentTypeController.getSegmentTypes(jsonObject);

                segmentTypesListApi.add(segmentTypeAux);
            }

            String nextUrl = segmentTypes.getString("next");
            url = updateDomain(nextUrl);

        } while (!url.equals("null"));

        return segmentTypesListApi;
    }



    public static List<Segment> segmentListFromJSON() throws JSONException, SegmentException {

        String url = "http://beta.edemocracia.camara.leg.br/wikilegis/api/segments/";

        List<Segment> segmentListApi = new ArrayList<>();

        do {

            String segmentList = getJSONObjectApi(url);

            JSONObject segment = new JSONObject(segmentList);
            JSONArray results = segment.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);
                Segment segmentAux = SegmentController.getSegment(jsonObject);
                segmentListApi.add(segmentAux);
            }
            String nextUrl = segment.getString("next");
            url = updateDomain(nextUrl);

        } while (!url.equals("null"));

        return segmentListApi;
    }

    public static String updateDomain(final String nextUrl){


        if (nextUrl.equals("null"))
            return "null";
        String correctAdress = nextUrl.substring(22);
        String correctDomain = "http://beta.edemocracia.camara.leg.br/" + correctAdress;
        Log.d("Aqui", correctDomain);
        return correctDomain;
    };
}
