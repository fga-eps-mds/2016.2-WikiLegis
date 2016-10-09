package gppmds.wikilegis.controller;

import android.content.Context;
import android.util.Log;

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

    public void initControllerSegments() throws SegmentException, JSONException {

        segmentDAO = SegmentDAO.getInstance(context);
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

        numberRomanBiggerThan1000(number, numberRoman);

        numberRomanBiggerThan900(number, numberRoman);

        numberRomanBiggerThan500(number, numberRoman);

        numberRomanBiggerThan400(number,numberRoman);

        numberRomanBiggerThan100(number,numberRoman);

        numberRomanBiggerThan90(number,numberRoman);

        numberRomanBiggerThan50(number,numberRoman);

        numberRomanBiggerThan40(number,numberRoman);

        numberRomanBiggerThan10(number,numberRoman);

        numberRomanBiggerThan9(number,numberRoman);

        numberRomanBiggerThan5(number,numberRoman);

        numberRomanBiggerThan4(number,numberRoman);

        numberRomanBiggerThan1(number,numberRoman);


        return numberRoman;
    }

    private static void numberRomanBiggerThan1(Integer number, String numberRoman) {
        while (number >= 1) {
            numberRoman += "I";
            number -= 1;
        }
    }

    private static void numberRomanBiggerThan4(Integer number, String numberRoman) {
        while (number >= 4) {
            numberRoman += "IV";
            number -= 4;
        }
    }

    private static void numberRomanBiggerThan5(Integer number, String numberRoman) {
        while (number >= 5) {
            numberRoman += "V";
            number -= 5;
        }
    }

    private static void numberRomanBiggerThan9(Integer number, String numberRoman) {
        while (number >= 9) {
            numberRoman += "IX";
            number -= 9;
        }
    }

    private static void numberRomanBiggerThan10(Integer number, String numberRoman) {
        while (number >= 10) {
            numberRoman += "X";
            number -= 10;
        }
    }

    private static void numberRomanBiggerThan40(Integer number, String numberRoman) {
        while (number >= 40) {
            numberRoman += "XL";
            number -= 40;
        }
    }

    private static void numberRomanBiggerThan50(Integer number, String numberRoman) {
        while (number >= 50) {
            numberRoman += "L";
            number -= 50;
        }
    }

    private static void numberRomanBiggerThan90(Integer number, String numberRoman) {
        while (number >= 90) {
            numberRoman += "XC";
            number -= 90;
        }
    }

    private static void numberRomanBiggerThan100(Integer number, String numberRoman) {
        while (number >= 100) {
            numberRoman += "C";
            number -= 100;
        }
    }

    private static void numberRomanBiggerThan400(Integer number, String numberRoman) {
        while (number >= 400) {
            numberRoman += "CD";
            number -= 400;

        }
    }

    private static void numberRomanBiggerThan500(Integer number, String numberRoman) {
        while (number >= 500) {
            numberRoman += "D";
            number -= 500;

        }
    }

    private static void numberRomanBiggerThan900(Integer number, String numberRoman) {
        while (number >= 900) {
            numberRoman += "CM";
            number -= 900;

        }
    }

    private static void numberRomanBiggerThan1000(Integer number, String numberRoman) {
        while (number >= 1000) {
            numberRoman += "M";
            number -= 1000;
        }
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

    public static List<Segment> getProposalsOfSegment(List<Segment> segmentList , int id){
        List<Segment> aux = new ArrayList<>();
            for(int i = 0 ; i<segmentList.size();i++){
                if(segmentList.get(i).getReplaced()!=0 && segmentList.get(i).getReplaced()==id){
                    aux.add(segmentList.get(i));
                }
            }
        return aux;
    }
}
