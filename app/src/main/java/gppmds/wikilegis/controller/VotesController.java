package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.api.DeleteRequest;
import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.dao.api.PostRequest;
import gppmds.wikilegis.dao.api.PutRequest;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.Vote;


public class VotesController {

    private static List<Vote> votesList = new ArrayList<Vote>();
    private static Context context;
    private static VotesController instance = null;

    private VotesController(final Context context) {
        this.context = context;
    }

    public static VotesController getInstance(final Context context) {
        if (instance == null) {
            instance = new VotesController(context);
        }
        return  instance;
    }
    public String registerVote(final int object_id , boolean vote) throws VotesException, JSONException {

        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);

        String url ="http://wikilegis-staging.labhackercd.net/api/votes/";
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("object_id" ,object_id);
        jsonObject.put("vote", vote ? "True" : "False");
        jsonObject.put("token",session.getString("token", ""));

        Log.d("token", session.getString("token",""));

        PostRequest postRequest = new PostRequest(context, url);
        postRequest.execute(jsonObject.toString(), "application/json");
        return "SUCCESS";
    }

    public void updateVote(final Integer idSegment, Integer idUser, boolean vote)
            throws BillException, VotesException, JSONException {
        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);

        Vote voteObject = DataDownloadController.getVoteBySegmentAndUserID(idSegment, idUser);

        Integer idVote = voteObject.getId();

        Log.d("id do voto update: ", idVote + "");

        String url ="http://wikilegis-staging.labhackercd.net/api/votes/update/" + idVote;

        JSONObject jsonObject =  new JSONObject();

        jsonObject.put("object_id", idSegment);
        jsonObject.put("token", session.getString("token",null));
        jsonObject.put("vote", vote ? "True" : "False");

        Log.d("token: ", session.getString("token", null));

        Log.d("json do put ", jsonObject.toString());

        PutRequest putRequest = new PutRequest(context, url);
        putRequest.execute(jsonObject.toString(), "application/json");
    }

    public String deleteVote(final Integer idSegment , Integer idUser)
            throws VotesException, JSONException, BillException {
        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);

        Log.d("VotesController", "deleteVote");

        Vote voteObject = DataDownloadController.getVoteBySegmentAndUserID(idSegment, idUser);

        Integer idVote = voteObject.getId();

        Log.d("id do voto: ", idVote + "");

        String url ="http://wikilegis-staging.labhackercd.net/api/votes/update/" + idVote;
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("object_id", idSegment);
        jsonObject.put("vote", voteObject.getVote() ? "True" : "False");
        jsonObject.put("token", session.getString("token",null));

        DeleteRequest deleteRequest= new DeleteRequest(context, url);
        deleteRequest.execute(jsonObject.toString(), "application/json");
        return "SUCCESS";
    }

    public boolean getVoteByUserAndIdSegment(Integer idUser, Integer idSegment, boolean vote) {
        boolean returnValue = JSONHelper.getVoteByUserAndIdSegment(idUser, idSegment, vote);

        return returnValue;
    }

}


