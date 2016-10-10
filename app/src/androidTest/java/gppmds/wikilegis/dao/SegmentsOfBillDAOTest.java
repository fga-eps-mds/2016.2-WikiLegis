package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentsOfBillException;
import gppmds.wikilegis.model.Bill;
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
        List<Bill> billList = new ArrayList<>();

        try {
            for(int i = 1; i <= 5; i++) {
                Bill bill = new Bill(i, "Teste", "teste", "closed", "description", "Meio Ambiente",
                        666, 13);

                billList.add(bill);
            }
        } catch (BillException e) {
            e.printStackTrace();
        }

        boolean result = false;

        try {
            result = segmentsOfBillDAO.insertAllSegmentsOfBills(billList);
        } catch (SegmentException e) {
            e.printStackTrace();
        }
       // assertFalse(segmentsOfBillDAO.isDatabaseEmpty());
        SegmentsOfBill segmentsOfBill = null;

        List <SegmentsOfBill> segmentsOfBillsList = new ArrayList<>();
        Log.d("LOG FUNCIONA: ", "SQN");
        for(int i = 0; i < billList.size(); i++) {
            Log.d("PRIMEIRO FOR", "BLAH BLAH");
            for(int j=0; j<billList.get(i).getSegments().size(); j++) {
                Log.d("SEGUNDO FOR - ENTREEEI ", "  sadsad");
                try {
                    segmentsOfBill = new SegmentsOfBill(billList.get(i).getId(),
                            billList.get(i).getSegments().get(j), j);

                    segmentsOfBillsList.add(segmentsOfBill);
                } catch (SegmentsOfBillException e) {
                    e.printStackTrace();
                }
            }
        }

        List<SegmentsOfBill> segmentsOfBillListDB = new ArrayList<>();

        try {
            segmentsOfBillListDB = segmentsOfBillDAO.getAllSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        int numberOfSegmentsOfBill = 0;
        for(int i = 0; i < segmentsOfBillListDB.size(); i++){
            if(segmentsOfBillsList.get(i).equals(segmentsOfBillListDB.get(i))){
                numberOfSegmentsOfBill++;
            }
        }

        assertTrue(result && numberOfSegmentsOfBill == segmentsOfBillsList.size());
    }

    @Test
    public void deleteAllSegmentsOfBillTest() {

        /*List<Bill> billList = new ArrayList<>();

        try {
            Bill bill = null;
            for (int i = 1; i <= 5; i++) {
                bill = new Bill (i, "Teste", "teste", "closed", "description", "Meio Ambiente",
                        666, 13);
                billList.add(bill);
            }

        } catch (BillException e) {
            e.printStackTrace();
        }

        boolean hasInserted = false;
        try {
            hasInserted = segmentsOfBillDAO.insertAllSegmentsOfBills(billList);
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        long deletedSegments = segmentsOfBillDAO.deleteAllSegmentsOfBill();
        //long deletedSegments = 5;
        Log.d("deletedSegments = ", + deletedSegments + " billListSIZE = " + billList.size());
        Log.d("HasInserted: ", ""+hasInserted);

        boolean isDbEmpty = segmentsOfBillDAO.isDatabaseEmpty();
        assertTrue(isDbEmpty && deletedSegments == billList.size());*/

    }

    @Test
    public void getAllSegmentsTest() {

    }

    @Test
    public void getAllSegmentsOfBillTest() {

    }
}
