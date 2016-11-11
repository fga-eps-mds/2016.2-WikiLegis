package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.dao.api.BillJsonHelper;
import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.Vote;

/**
 * Created by marcelo on 10/17/16
 */
public class DataDownloadController {
    private static Context context;
    private static DataDownloadController instance = null;

    private DataDownloadController(final Context context) {
        this.context = context;
    }

    public static DataDownloadController getInstance(final Context context) {
        if (instance == null) {
            instance = new DataDownloadController(context);
        }
        return  instance;
    }

    public int connectionType() {
        final ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        int connectionType = 0;

        if (wifi.isConnectedOrConnecting ()) {
            Log.i("Connection", "Wifi");

            connectionType = 0;
        } else if (mobile.isConnectedOrConnecting ()) {
            Log.i("Connection", "Mobile 3G");

            connectionType = 1;
        } else {
            Log.i("Connection", "No network");

            connectionType = 2;
        }
        return connectionType;
    }

    public void updateData() throws SegmentException, JSONException, BillException, VotesException {

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        final String keyPreferencesConnection = context.getResources().getString(R.string.network_settings);

        int preferencesConnection = session.getInt(keyPreferencesConnection, 0);
        int actualConnection  = connectionType();

        if((preferencesConnection <= 1 && actualConnection == 0)
                || (preferencesConnection == 1 && actualConnection == 1)){
            Log.d("TO BAIXANDO AS COISAS", "updateData");

            updateSegments();

            updateModifiedSegments();

            updateBills();

            SharedPreferences.Editor editor = session.edit();
            editor.putString("date", getLocalTime());
            editor.commit();

            Log.d("Data salva", session.getString("date", "Nada salvo"));
        }
    }

    public void updateSegments() throws SegmentException, JSONException {
        SegmentController segmentController = SegmentController.getInstance(context);
        segmentController.initControllerSegments();
    }

    public void updateModifiedSegments() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);
        segmentController.initModifiedSegments();
    }

    public void updateBills() throws BillException, JSONException, SegmentException {
        BillController billController = BillController.getInstance(context);
        billController.initControllerBills();
    }

    public static String getLocalTime(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());

        return formatted;
    }

    //Pq esse metodo esta estatico?
    public static List<Vote> getVoteBySegmentId(String id) throws JSONException, BillException, VotesException {
        List<Vote> listVotes = null;
        listVotes = JSONHelper.votesListFromJSON("?user=&object_id="+id);
        return listVotes;
    }

    //Pq esse metodo esta estatico?
    public static int getNumberOfVotesbySegment(int id, Boolean isLike) throws BillException, VotesException, JSONException {
        List<Vote> votes = getVoteBySegmentId(""+id);
        int numberOfVotes = 0;
        for(Vote vote : votes) {
            if (isLike) {
                if (vote.isVote()) {
                    numberOfVotes++;
                }
            }else{
                if(!vote.isVote()){
                    numberOfVotes++;
                }
            }
        }
        return numberOfVotes;
    }

}
