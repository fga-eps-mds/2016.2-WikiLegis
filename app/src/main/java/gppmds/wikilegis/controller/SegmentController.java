package gppmds.wikilegis.controller;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.dao.SegmentDAO;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

/**
 * Created by marcelo on 9/13/16.
 */
public class SegmentController {

    private static List<Segment> segmentList = new ArrayList<Segment>();
    private static SegmentDAO segmentDAO;
    private Context context;

    public SegmentController(Context context) {
        this.context = context;
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
    public static int getMinDate(int id){
       Integer day , month, year;
        int aux = 20170000 , result;
        String array[] = new String[2];
        String arrayDate[] = new String[3];



        for(int index = 0 ; index <segmentList.size();index++){

            if(segmentList.get(index).getBill()==id){

                array = segmentList.get(index).getContent().split("T");
                arrayDate = array[0].split("-");
                day = Integer.parseInt(arrayDate[2]);
                month = Integer.parseInt(arrayDate[1]);
                year = Integer.parseInt(arrayDate[0]);
                result = year*10000+month*1000+day;

                if(result<aux){
                    aux = result;
                }
            }
        }
        return aux;
    }


}
