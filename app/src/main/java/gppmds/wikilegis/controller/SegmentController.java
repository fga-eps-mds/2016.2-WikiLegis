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
                f.getString("number").equals("null") ? 0 : f.getInt("number"),
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

    public static String convertRoman(Integer number) {
        String numberRoman = "";

        while (number >= 1000) {
            numberRoman += "M";
            number -= 1000;
        }
        while (number >= 900) {
            numberRoman += "CM";
            number -= 900;
        }
        while (number >= 500) {
            numberRoman += "D";
            number -= 500;
        }
        while (number >= 400) {
            numberRoman += "CD";
            number -= 400;
        }
        while (number >= 100) {
            numberRoman += "C";
            number -= 100;
        }
        while (number >= 90) {
            numberRoman += "XC";
            number -= 90;
        }
        while (number >= 50) {
            numberRoman += "L";
            number -= 50;
        }
        while (number >= 40) {
            numberRoman += "XL";
            number -= 40;
        }
        while (number >= 10) {
            numberRoman += "X";
            number -= 10;
        }
        while (number >= 9) {
            numberRoman += "IX";
            number -= 9;
        }
        while (number >= 5) {
            numberRoman += "V";
            number -= 5;
        }
        while (number >= 4) {
            numberRoman += "IV";
            number -= 4;
        }
        while (number >= 1) {
            numberRoman += "I";
            number -= 1;
        }

        return numberRoman;
    }

    public static String addingTypeContent(Segment segment) {
        String alphabet = "abcdefghijklmnopqrstwxyz";
        String bufferAux = "";

        switch (segment.getType()) {
            case 1:
                bufferAux = "Art. " + segment.getNumber().toString() + "º " + segment.getContent();
                break;
            case 3:
                bufferAux = convertRoman(segment.getNumber()) + " - " + segment.getContent();
                break;
            case 4:
                bufferAux = "§ " + segment.getNumber() + "º " + segment.getContent();
                break;
            case 5:
                bufferAux = "    " + alphabet.charAt(segment.getNumber()-1) + ") " + segment.getContent();
                break;
            default:
                bufferAux = segment.getContent();
        }
        return bufferAux;
    }
}
