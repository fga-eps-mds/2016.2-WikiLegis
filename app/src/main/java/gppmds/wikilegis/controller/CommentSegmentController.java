package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;

import gppmds.wikilegis.dao.PostRequest;
import gppmds.wikilegis.exception.CommentsException;
import gppmds.wikilegis.model.Comments;

public class CommentSegmentController {
    private final Context context;
    private static CommentSegmentController instance = null;

    private CommentSegmentController(final Context context) {
        this.context = context;
    }

    public static CommentSegmentController getInstance(final Context context) {
        if (instance == null) {
            instance = new CommentSegmentController(context);
        }
        return  instance;
    }

    public boolean registerComment (int objectPK, String comment, Context context)
            throws JSONException, CommentsException {

        Comments segment = new Comments(objectPK, comment);

        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);

        String url = "http://wikilegis-staging.labhackercd.net/api/comments/";
        String json = "{" +
                "\"object_pk\": " +segment.getObjectPk()+","+
                "\"comment\": " + segment.getComment()+","+
                "\"token\": \""+session.getString("token",null) +"\""+
                "}";

        Log.d("URL", url);
        Log.d("URL PARAMS", json);

        PostRequest postRequest = new PostRequest(context, url);
        postRequest.execute(json, "application/json");

        return true;
    }
}
