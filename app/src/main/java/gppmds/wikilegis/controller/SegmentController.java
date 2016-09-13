package gppmds.wikilegis.controller;

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

    public static List<Segment> getAllSegments(){
        return segmentList;
    }

    public void initControllerSegments() throws SegmentException, JSONException {
        if (segmentDAO.isDatabaseEmpty()) {
            segmentList = JSONHelper.segmentListFromJSON();
            segmentDAO.insertAllSegments(segmentList);
        } else {
            segmentList = segmentDAO.getAllSegments();
        }
    }

}
