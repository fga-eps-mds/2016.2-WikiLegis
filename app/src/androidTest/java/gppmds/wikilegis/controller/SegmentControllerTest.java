package gppmds.wikilegis.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by shammyz on 9/30/16.
 */
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
        segmentController.initControllerSegments();

        Segment segment = segmentController.getSegmentById(3927);
        assertTrue(segment.getId() == 3927);
    }

    @Test
    public void testGetSegmentByInvalidId() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);
        segmentController.initControllerSegments();

        Segment segment = segmentController.getSegmentById(0);
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
    public void testGetProposalsOfSegment(){
        SegmentController segmentController = SegmentController.getInstance(context);
        List<Segment> proposalList = new ArrayList<>();
        try {
            segmentController.initControllerSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        proposalList = segmentController.getProposalsOfSegment(segmentController.getAllSegments(),
                3946);

        assertTrue(proposalList.size() == 4);
    }

    @Test
    public void testRegisterSegmentWithEmptySuggestion() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);

        String status= segmentController.registerSegment(13, 131, "", context);

        assertEquals(status, "Por favor, digite uma sugestão");
    }

    @Test
    public void testRegisterSegmentWithValidSuggestion() throws SegmentException, JSONException{
        SegmentController segmentController = SegmentController.getInstance(context);

        String status= segmentController.registerSegment(13, 121, "Testando", context);

        assertEquals(status, "SUCCESS");
    }
}
