package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentsOfBillException;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentsOfBill;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SegmentsOfBillDAOTest {
    Context context;
    SegmentsOfBillDAO segmentsOfBillDAO;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
        segmentsOfBillDAO = SegmentsOfBillDAO.getInstance(context);
        segmentsOfBillDAO.clearSegmentsOfBillDaoTable();
    }

    @Test
    public void isDatabaseEmptyWhenDatabaseIsEmptyTest() {
        assertTrue(segmentsOfBillDAO.isDatabaseEmpty());
    }

    @Test
    public void isDatabaseEmptyWhenDatabaseIsNotEmptyTest() {
        // idDoBill, idDoSegment, posicao
        SegmentsOfBill segmentsOfBill = null;
        try {
            segmentsOfBill = new SegmentsOfBill(1,1,1);
        } catch (SegmentsOfBillException e) {
            e.printStackTrace();
        }
        segmentsOfBillDAO.insertSegmentsOfBill(segmentsOfBill);

        assertFalse(segmentsOfBillDAO.isDatabaseEmpty());
    }

    @Test
    public void insertSegmentsOfBillTest() {
        SegmentsOfBill segmentsOfBill = null;
        boolean result = false;

        try {
            segmentsOfBill = new SegmentsOfBill(1,1,1);
            result = segmentsOfBillDAO.insertSegmentsOfBill(segmentsOfBill);
        } catch (SegmentsOfBillException e) {
            e.printStackTrace();
        }
        int numberOfSegmentsOfBill = 0;
        List<SegmentsOfBill> segmentOfBillList = new ArrayList<>();
        try {
            segmentOfBillList = segmentsOfBillDAO.getAllSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < segmentOfBillList.size(); i++){
            if (segmentsOfBill.equals(segmentOfBillList.get(i))){
                numberOfSegmentsOfBill++;
            } else {
                //Do Nothing.
            }
        }
        
        assertTrue(result && (numberOfSegmentsOfBill == 1));
    }

    @Test
    public void insertAllSegmentsOfBillsTest() {

    }

    @Test
    public void deleteAllSegmentsOfBillTest() {

    }

    @Test
    public void getAllSegmentsTest() {

    }

    @Test
    public void getAllSegmentsOfBillTest() {

    }
}
