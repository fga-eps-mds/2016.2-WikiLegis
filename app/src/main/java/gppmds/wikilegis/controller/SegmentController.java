package gppmds.wikilegis.controller;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.dao.SegmentDAO;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

/**
 * Created by marcelo on 9/13/16.
 */
public class SegmentController {

    private static List<Segment> segmentList = new ArrayList<Segment>();
    private static SegmentDAO segmentDAO;
    private Context context;
    private static SegmentController instance = null;

    private SegmentController(Context context) {
        this.context = context;
    }

    public static SegmentController getInstance(Context context){
        if(instance == null){
            instance = new SegmentController(context);
        }
        return  instance;
    }

    public static List<Segment> getAllSegments(){
        return segmentList;
    }

    //Inicia todos os segmentos no banco local

    public void initControllerSegments() throws SegmentException, JSONException {

        segmentDAO = SegmentDAO.getInstance(context);

        if (segmentDAO.isDatabaseEmpty()) {
            segmentList = JSONHelper.segmentListFromJSON();
            segmentDAO.insertAllSegments(segmentList);
        } else {
            segmentList = segmentDAO.getAllSegments();
        }
    }

}
