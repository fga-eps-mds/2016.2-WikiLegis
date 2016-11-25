package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.dao.api.PostRequest;
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

    public List<Comments> getCommentsByIdOfSegment(Integer idSegment) throws JSONException,
            CommentsException {
        List<Comments> commentsList = JSONHelper.getCommentsByIdOfSegment(idSegment);

        return commentsList;
    }

    public String registerComment (int objectPK, String comment, Context context)
            throws JSONException, CommentsException {

        String status;

        if(comment.isEmpty()){
            status = context.getResources().getString(R.string.empty_comment);
        }
        else{

            Comments segment = new Comments(objectPK, comment);

            SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);

            String url = "http://wikilegis-staging.labhackercd.net/api/comments/";
            String json = buildJson(segment, session);

            Log.d("URL", url);
            Log.d("URL PARAMS", json);

            PostRequest postRequest = new PostRequest(context, url);
            postRequest.execute(json, "application/json");

            status = "SUCCESS";
        }

        return status;
    }

    private String buildJson(Comments segment, SharedPreferences session){
         return  "{" +
                "\"object_id\": " + segment.getObjectPk() + "," +
                "\"comment\": \"" + segment.getComment() + "\"" + "," +
                "\"token\": \"" + session.getString("token", null) + "\"" +
                "}";
    }
}
