package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.api.PostRequest;
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

        String json = "{" +
                "\"object_id\": " +object_id+","+
                "\"vote\": " + vote+","+
                "\"token\": \""+session.getString("token",null) +"\""+
                "}";
        Log.d("URL PARAMS", json);

        PostRequest postRequest = new PostRequest(context, url);
        postRequest.execute(json, "application/json");
        return "SUCCESS";
    }
}


