package gppmds.wikilegis.controller;

import android.content.Context;

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
    public String registerVote(final int object_id , boolean op) throws VotesException, JSONException {
        Vote vote = new Vote(object_id, op);

        JSONObject jsonParam = new JSONObject();
        jsonParam.put("object_id", vote.getObjectId());
        jsonParam.put("vote", vote.getVote());


        String url = "wikilegis-staging.labhackercd.net/api/segments/";

        PostRequest postRequest = new PostRequest(context,
                "http://wikilegis-staging.labhackercd.net/api/user/create/");
        postRequest.execute(jsonParam.toString(),"application/json");

        return " SUCCESS";
    }
}


