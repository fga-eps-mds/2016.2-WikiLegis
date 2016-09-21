package gppmds.wikilegis.controller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.dao.PostRequest;
import gppmds.wikilegis.dao.SegmentDAO;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

public class SegmentController {

    private static List<Segment> segmentList = new ArrayList<Segment>();
    private static SegmentDAO segmentDAO;
    private Context context;
    private static SegmentController instance = null;

    private SegmentController(final Context context) {
        this.context = context;
    }

    public static SegmentController getInstance(final Context context) {
        if (instance == null) {
            instance = new SegmentController(context);
        }
        return  instance;
    }

    public static List<Segment> getAllSegments() {
        return segmentList;
    }

    public static Segment getSegmentById(final Integer id) throws SegmentException {
        return segmentDAO.getSegmentById(id);
    }
    //Inicia todos os segmentos no banco local

    public static Segment getSegment(final JSONObject jsonObject) throws SegmentException,
            JSONException {
        return new Segment(jsonObject.getInt("id"),
                jsonObject.getInt("order"),
                jsonObject.getInt("bill"),
                jsonObject.getBoolean("original"),
                jsonObject.getString("replaced").equals("null") ? 0 : jsonObject.getInt("replaced"),
                jsonObject.getInt("id"),
                jsonObject.getInt("type"),
                jsonObject.getString("number").equals("null") ? 0 : jsonObject.getInt("number"),
                jsonObject.getString("content"),
                jsonObject.getString("created"));
    }

    public void initControllerSegments() throws SegmentException, JSONException {

        segmentDAO = SegmentDAO.getInstance(context);
        PostRequest postRequest = new PostRequest();
        postRequest.execute("http://127.0.0.1:8000/api/user/create/", "thiagoteste@gmail.com",
                "ThiagoTeste", "Teste", "111222");

        if (segmentDAO.isDatabaseEmpty()) {
            segmentList = JSONHelper.segmentListFromJSON();
            segmentDAO.insertAllSegments(segmentList);
        } else {
            segmentList = segmentDAO.getAllSegments();
        }
    }

    public static int getMinDate(final int id) {
        Integer day, month, year;
        Integer aux = 20170000, result;

        String array[] = new String[2];
        String arrayDate[] = new String[3];

        for (int index = 0; index < segmentList.size(); index++) {

            if (segmentList.get(index).getBill() == id) {

                array = segmentList.get(index).getDate().split("T");
                arrayDate = array[0].split("-");
                day = Integer.parseInt(arrayDate[2]);
                month = Integer.parseInt(arrayDate[1]);
                year = Integer.parseInt(arrayDate[0]);
                result = year * 10000 + month * 1000 + day;

                if (result < aux) {
                    aux = result;
                }
            }
        }
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

    public boolean isSegmentDatabaseIsEmpty() {
        return SegmentDAO.getInstance(context).isDatabaseEmpty();
    }

    public static String addingTypeContent(final Segment segment) {
        String alphabet = "abcdefghijklmnopqrstwxyz";
        String bufferAux = "";

        switch (segment.getType()) {
            case 1:
                bufferAux = "Art. " + segment.getNumber().toString() + "º " + segment.getContent();
                break;
            case 2:
                bufferAux = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTITULO "
                        + convertRoman(segment.getNumber()) + "\n" + segment.getContent();
                break;
            case 3:
                bufferAux = "\t\t\t" + convertRoman(segment.getNumber()) + " - "
                        + segment.getContent();
                break;
            case 4:
                bufferAux = "§ " + segment.getNumber() + "º " + segment.getContent();
                break;
            case 5:
                bufferAux = "\t\t\t\t\t" + alphabet.charAt(segment.getNumber() - 1)
                        + ") " + segment.getContent();
                break;
            case 7:
                bufferAux = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tCAPITULO "
                        + convertRoman(segment.getNumber()) + "\n" + segment.getContent();
                break;
            case 8:
                bufferAux = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tLIVRO "
                        + convertRoman(segment.getNumber()) + "\n" + segment.getContent();
                break;
            case 9:
                bufferAux = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tSEÇAO "
                        + convertRoman(segment.getNumber()) + "\n" + segment.getContent();
                break;
            case 10:
                bufferAux = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tSUBSEÇAO "
                        + convertRoman(segment.getNumber()) + "\n" + segment.getContent();
                break;
            default:
                bufferAux = segment.getContent();
        }
        return bufferAux;
    }
}
