package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.dao.database.SegmentDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class SegmentControllerTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testGetMinDate() throws SegmentException, JSONException {
        SegmentController segmentController = SegmentController.getInstance(context);
        segmentController.initControllerSegments();

        int minDateOfBill = SegmentController.getMinDate(53);

        assert 20168018 == minDateOfBill;
    }

    @Test
    public void testAddingTypeContentWithTypeContentOne() {
        final Integer TYPE = 1;
        final Integer NUMBER = 1;
        final String CONTENT = "Content do artigo.";
        Segment segment = null;
        try {
             segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("Art. 1º Content do artigo."));
    }

    @Test
    public void testAddingTypeContentWithTypeContentTwo() {
        final Integer TYPE = 2;
        final Integer NUMBER = 1;
        final String CONTENT = "Content do titulo.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("\t\t\t\t\t\t\t" +
                "\t\t\t\t\t\t\t\t\tTITULO I\nContent do titulo."));
    }

    @Test
    public void testAddingTypeContentWithTypeContentThree() {
        final Integer TYPE = 3;
        final Integer NUMBER = 1;
        final String CONTENT = "Content do inciso.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("\t\t\t" +
                "I - Content do inciso."));
    }

    @Test
    public void testAddingTypeContentWithTypeContentFour() {
        final Integer TYPE = 4;
        final Integer NUMBER = 1;
        final String CONTENT = "Content do paragrafo.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("§ 1º Content do paragrafo."));
    }

    @Test
    public void testAddingTypeContentWithTypeContentFive() {
        final Integer TYPE = 5;
        final Integer NUMBER = 1;
        final String CONTENT = "Content da alinea.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("\t\t\t\t\t" +
                "a) Content da alinea."));
    }

    @Test
    public void testAddingTypeContentWithTypeContentSeven() {
        final Integer TYPE = 7;
        final Integer NUMBER = 1;
        final String CONTENT = "Content do paragrafo.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("\t\t\t\t\t\t\t\t\t\t" +
                "\t\t\t\t\tCAPITULO I\nContent do paragrafo."));
    }

    @Test
    public void testAddingTypeContentWithTypeContentEight() {
        final Integer TYPE = 8;
        final Integer NUMBER = 1;
        final String CONTENT = "Content do livro.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("\t\t\t\t\t\t\t\t\t\t\t" +
                "\t\t\t\t\tLIVRO I\nContent do livro."));
    }

    @Test
    public void testAddingTypeContentWithTypeContentNine() {
        final Integer TYPE = 9;
        final Integer NUMBER = 1;
        final String CONTENT = "Content da secao.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("\t\t\t\t\t\t\t\t\t\t\t" +
                "\t\t\t\t\tSEÇAO I\nContent da secao."));
    }

    @Test
    public void testAddingTypeContentWithTypeContentTen() {
        final Integer TYPE = 10;
        final Integer NUMBER = 1;
        final String CONTENT = "Content da subsecao.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("\t\t\t\t\t\t\t\t\t\t\t" +
                "\t\t\t\t\tSUBSEÇAO I\nContent da subsecao."));
    }

    @Test
    public void testIsSegmentDatabaseIsEmptyWithDatabaseIsNotEmpty() throws SegmentException,
            JSONException {
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = session.edit();

        final String keyDate = "date";
        editor.putString(keyDate, "2010-01-01");

        editor.commit();

        SegmentController segmentController = SegmentController.getInstance(context);
        segmentController.initControllerSegments();

        assertFalse(segmentController.isSegmentDatabaseIsEmpty());
    }

    @Test
    public void testAddingTypeContentWithAnotherValue() {
        final Integer TYPE = 11;
        final Integer NUMBER = 1;
        final String CONTENT = "Content da subsecao.";
        Segment segment = null;
        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(SegmentController.addingTypeContent(segment).equals("Content da subsecao."));
    }

    @Test
    public void testGetAllSegments(){
        SegmentController segmentController = SegmentController.getInstance(context);
        try {
            segmentController.initControllerSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertTrue(segmentController.getAllSegments().size() > 0);
    }

    @Test
    public void testGetSegmentByValidId() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);
        List<Segment> proposalList = new ArrayList<>();

        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);
        segmentDAO.deleteAllSegments();

        final Integer TYPE = 11;
        final Integer NUMBER = 1;
        final String CONTENT = "Content da subsecao.";
        Segment segment = null;

        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");

            segmentDAO.insertSegment(segment);
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        Segment segmentById = segmentController.getSegmentById(1, context);
        assertTrue(segmentById.getId() == 1);

        segmentDAO.deleteAllSegments();
    }

    @Test
    public void testGetSegmentByInvalidId() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);
        segmentController.initControllerSegments();

        Segment segment = segmentController.getSegmentById(0, context);
        assertNull(segment);
    }

    @Test
    public void testConvertRomanWith1999() {
        String roman;

        roman=SegmentController.convertRoman(1999);
        assertEquals(roman, "MCMXCIX");
    }
    @Test
    public void testConvertRomanWith555() {
        String roman;

        roman=SegmentController.convertRoman(555);
        assertEquals(roman, "DLV");
    }
    @Test
    public void testConvertRomanWith944() {
        String roman;

        roman=SegmentController.convertRoman(944);
        assertEquals(roman, "CMXLIV");
    }

    @Test
    public void testConvertRomanWith401() {
        String roman;

        roman=SegmentController.convertRoman(401);
        assertEquals(roman, "CDI");
    }

    @Test
    public void testConvertRomanWith10() {
        String roman;

        roman=SegmentController.convertRoman(10);
        assertEquals(roman, "X");
    }

    @Test
    public void testConvertRomanWith100() {
        String roman;

        roman = SegmentController.convertRoman(100);
        assertEquals(roman, "C");
    }

    @Test
    public void testGetSegmentsOfBillById() {
        SegmentController segmentController = SegmentController.getInstance(context);

        List<Segment> segmentList = new ArrayList<>();

        try {
            segmentList = segmentController.getSegmentsOfBillById(10 + "", "", false);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(segmentList.size() == 3);
    }

    @Test
    public void testGetProposalsOfBillById() {
        SegmentController segmentController = SegmentController.getInstance(context);

        List<Segment> segmentList = new ArrayList<>();

        try {
            segmentList = segmentController.getSegmentsOfBillById("10", "75", true);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        assertTrue(segmentList.size() == 1);
    }

    @Test
    public void testGetSegmentByIdFromList() {
        SegmentController segmentController = SegmentController.getInstance(context);

        final String DATE = "2010-01-01";
        List<Segment> newSegments = new ArrayList<>();

        try {

            newSegments = JSONHelper.segmentListFromJSON(
                    context.getString(R.string.created_segments_url), DATE);

            segmentController.setSegmentList(newSegments);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        final int SEGMENT_ID = newSegments.get(0).getId();

        Segment segment = segmentController.getSegmentByIdFromList(SEGMENT_ID);

        assert (segment.getId() == SEGMENT_ID);
    }

    //FIXME
    /*
    @Test
    public void testGetProposalsOfSegment(){
        SegmentController segmentController = SegmentController.getInstance(context);
        List<Segment> proposalList = new ArrayList<>();

        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);
        segmentDAO.deleteAllSegments();

        final Integer TYPE = 11;
        final Integer NUMBER = 1;
        final String CONTENT = "Content da subsecao.";
        Segment segment = null;
        Segment sugestedOne = null;
        Segment sugestedTwo = null;
        Segment sugestedThree = null;

        try {
            segment = new Segment(1, 1, 1, true, 0, 1, TYPE, NUMBER, CONTENT, "1");

            sugestedOne = new Segment(2, 1, 1, true, 1, 1, TYPE, NUMBER, CONTENT, "1");
            sugestedTwo = new Segment(3, 1, 1, true, 1, 1, TYPE, NUMBER, CONTENT, "1");
            sugestedThree = new Segment(4, 1, 1, true, 1, 1, TYPE, NUMBER, CONTENT, "1");

            segmentDAO.insertSegment(segment);
            segmentDAO.insertSegment(sugestedOne);
            segmentDAO.insertSegment(sugestedTwo);
            segmentDAO.insertSegment(sugestedThree);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        try {
            segmentController.initControllerSegmentsOffline();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        proposalList = segmentController.getProposalsOfSegment(segmentController.getAllSegments(),
                1);

        assertTrue(proposalList.size() == 3);
    }*/

    @Test
    public void testRegisterSegmentWithEmptySuggestion() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);

        String status= segmentController.registerSegment(13, 131, "", context);

        assertEquals(status, "Por favor, digite uma sugestão");
    }

    @Test
    public void testRegisterSegmentWithValidSuggestion() throws SegmentException, JSONException{

        SegmentController segmentController = SegmentController.getInstance(context);

        String status = segmentController.registerSegment(13, 121, "Testando", context);

        assertEquals(status, "SUCCESS");
    }
}
