package gppmds.wikilegis.dao.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.CommentsException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Comments;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.Vote;

public class JSONHelper {

    public static String requestJsonObjectFromApi(final String URL) {
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

        Log.d("JSON",getApi);
        return getApi;
    }

    public static List<Bill> billListFromJSON(final String billListJson)
            throws JSONException, BillException, SegmentException {

        JSONObject bills = new JSONObject(billListJson);
        JSONArray results = bills.getJSONArray("results");

        List<Bill> billListApi = getListBill(results);

        return billListApi;
    }

    public static List<Comments>getCommentsByIdOfSegment(Integer idSegment) throws JSONException,
            CommentsException{
        String url = "http://wikilegis-staging.labhackercd.net/api/comments/?object_pk=" + idSegment;

        List<Comments> commentsListAPI = new ArrayList<>();

        String commentsListJSON = requestJsonObjectFromApi(url);

        try {
            JSONObject commentsJSONObject = new JSONObject(commentsListJSON);
            JSONArray results = commentsJSONObject.getJSONArray("results");


            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);

                Comments comments = new Comments(idSegment, jsonObject.getString("comment"));
                commentsListAPI.add(comments);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (CommentsException e) {
            e.printStackTrace();
        }

        return commentsListAPI;
    }

    public static List<Vote> votesListFromJSON(String urlDate) throws JSONException, VotesException {
        String url = "http://wikilegis-staging.labhackercd.net/api/votes/"+urlDate;
        List<Vote> voteListApi = new ArrayList<>();

        do {
            String votesList = requestJsonObjectFromApi(url);

            JSONObject votes = new JSONObject(votesList);
            JSONArray results = votes.getJSONArray("results");

            populateListVotes(results, voteListApi);

            String nextUrl = votes.getString("next");
            url = nextUrl; //updateDomain(nextUrl);

            Log.d("Url", url);

        } while (!url.equals("null"));

        return voteListApi;
    }

    public static void populateListVotes(JSONArray results, List<Vote> voteListApi)
            throws JSONException, VotesException {
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonObject = results.getJSONObject(i);

            voteListApi.add(setVotesAttributes(jsonObject));
        }
    }


    public static List<Segment> segmentListFromJSON(String urlDomain, String urlDate) throws JSONException, SegmentException {
        String url = urlDomain + urlDate;
        List<Segment> segmentListApi = new ArrayList<>();

        do {
            String segmentList = requestJsonObjectFromApi(url);

            JSONObject segment = new JSONObject(segmentList);
            JSONArray results = segment.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);

                segmentListApi.add(setSegmentAttributes(jsonObject));
            }
            String nextUrl = segment.getString("next");
            url = nextUrl; //updateDomain(nextUrl);
            Log.d("URL",nextUrl);

        } while (!url.equals("null"));

        return segmentListApi;
    }

    public static List<Segment> getSegmentFromBill(String billId, String replaced) throws JSONException, SegmentException {
        String url = "http://wikilegis-staging.labhackercd.net/api/segments/?bill="+billId+"&replaced="+replaced;
        List<Segment> segmentListApi = new ArrayList<>();

        do {
        String segmentList = requestJsonObjectFromApi(url);

        JSONObject segment = new JSONObject(segmentList);
        JSONArray results = segment.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonObject = results.getJSONObject(i);

            segmentListApi.add(setSegmentAttributes(jsonObject));
        }

        String nextUrl = segment.getString("next");
        url = nextUrl; //updateDomain(nextUrl);
        Log.d("URL",nextUrl);

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

    private static Vote setVotesAttributes(JSONObject jsonObject) throws JSONException, VotesException {
        Vote voteAux = new Vote(jsonObject.getInt("id"),
                1,//jsonObject.getInt("user"),
                1,//jsonObject.getInt("content_type"),
                jsonObject.getInt("object_id"),
                jsonObject.getString("vote").equals("false") ? false : true);
        return voteAux;
    }

    private static Segment setSegmentAttributes(JSONObject jsonObject) throws JSONException, SegmentException {
        Segment segmentAux = new Segment(jsonObject.getInt("id"),
                jsonObject.getInt("order"),
                jsonObject.getInt("bill"),
                jsonObject.getBoolean("original") ? 1 : 0,
                jsonObject.getString("replaced").equals("null") ? 0 : jsonObject.getInt("replaced"),
                jsonObject.getInt("id"),
                jsonObject.getInt("type"),
                jsonObject.getString("number").equals("null") ? 0 : jsonObject.getInt("number"),
                jsonObject.getString("content"),
                jsonObject.getString("created"));
        return segmentAux;
    }

    private static List<Bill> getListBill(JSONArray results)
            throws JSONException, BillException {
        int id;
        Integer numberOfProposals;
        Integer date;

        List<Bill> billListApi = new ArrayList<>();

        for (int index = 0; index < results.length(); index++){
            JSONObject jsonObject = results.getJSONObject(index);

            id = jsonObject.getInt("id");
            numberOfProposals = jsonObject.getInt("proposals_count");
            date = SegmentController.getMinDate(id);

            Bill billAux = BillController.getBill(numberOfProposals, date, jsonObject);
            JSONArray segments = jsonObject.getJSONArray("segments");

            for (int j = 0; j < segments.length(); j++) {
                billAux.setSegments(segments.getInt(j));
            }

            Log.d("billauxs", billAux.getSegments().size() + "");

            billListApi.add(billAux);
        }

        return billListApi;
    }


}
