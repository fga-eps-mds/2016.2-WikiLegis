package gppmds.wikilegis.controller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static Segment getSegmentById(Integer id) throws SegmentException {
        return segmentDAO.getSegmentById(id);
    }
    //Inicia todos os segmentos no banco local

    public static Segment getSegment(JSONObject f) throws SegmentException, JSONException {
        return new Segment(f.getInt("id"),
                f.getInt("order"),
                f.getInt("bill"),
                f.getBoolean("original"),
                //Mesma coisa das outras era replaced
                f.getString("replaced").equals("null") ? 0 : f.getInt("replaced"),
                //Tambem pode vir null, botei id pra testar e parent
                f.getInt("id"),
                f.getInt("type"),
                //Pode vir null???? Botei id pra testar again e number
                f.getInt("id"),
                f.getString("content"),
                //A partir desta está errada, botei apenas para testar.
                f.getInt("id"),
                f.getInt("id"),
                f.getInt("id"));
    }
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



        /*for(int index = 0 ; index <segmentList.size();index++){

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
        }*/
        return aux;
    }


}
