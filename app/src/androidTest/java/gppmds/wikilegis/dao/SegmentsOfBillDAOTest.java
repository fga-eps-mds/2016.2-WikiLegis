package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
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
            segmentsOfBill = new SegmentsOfBill(1,1);
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
            segmentsOfBill = new SegmentsOfBill(1,1);
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

                //Para cada bill, vão ser inseridos segmentos com id de [1 a 5], [6 a 10] e assim
                // sucessivamente
                for(int j = 5 * (i-1) + 1; j <= 5 * (i-1) + 5; j++) {
                    bill.setSegments(j);
                }

                billList.add(bill);
            }
        } catch (BillException e) {
            e.printStackTrace();
        }

        BillController billController = BillController.getInstance(context);
        SegmentsOfBillController segmentsOfBillController =
                SegmentsOfBillController.getInstance(context);

        try {
            billController.initControllerBills();
            segmentsOfBillController.initControllerSegmentsOfBill();
        } catch (BillException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        List<SegmentsOfBill> segmentsOfBillListDB = new ArrayList<>();

        try {
            segmentsOfBillListDB = segmentsOfBillDAO.getAllSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        Log.d("AUX antes", segmentsOfBillListDB.size() + "");


        boolean result = false;

        try {
            result = segmentsOfBillDAO.insertAllSegmentsOfBills(billList);
        } catch (SegmentException e) {
            e.printStackTrace();
        }


        SegmentsOfBill segmentsOfBill = null;

        List <SegmentsOfBill> segmentsOfBillsList = new ArrayList<>();
        for(int i = 0; i < billList.size(); i++) {
            for(int j = 0; j < billList.get(i).getSegments().size(); j++) {
                try {
                    segmentsOfBill = new SegmentsOfBill(billList.get(i).getId(),
                            billList.get(i).getSegments().get(j));

                    segmentsOfBillsList.add(segmentsOfBill);
                } catch (SegmentsOfBillException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            segmentsOfBillListDB = segmentsOfBillDAO.getAllSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        Log.d("AUX depois", segmentsOfBillListDB.size() + "");

        int numberOfSegmentsOfBill = 0;
        for(int i = 0; i < segmentsOfBillListDB.size(); i++) {
            for(int j = 0; j < segmentsOfBillsList.size(); j++) {
                if(segmentsOfBillListDB.get(i).equals(segmentsOfBillsList.get(j))) {
                    numberOfSegmentsOfBill++;
                }
            }
        }

        Log.d("Result: ", result + "");
        Log.d("N segments of bill: ", numberOfSegmentsOfBill + "");
        Log.d("size()", segmentsOfBillsList.size() + "");

        assertTrue(result && numberOfSegmentsOfBill == segmentsOfBillsList.size());
    }

    @Test
    public void deleteAllSegmentsOfBillTest() {

        List<Bill> billList = new ArrayList<>();

        int numberOfSegmentsAdded = 0;

        try {
            Bill bill = null;
            for (int i = 1; i <= 5; i++) {
                bill = new Bill (i, "Teste", "teste", "closed", "description", "Meio Ambiente",
                        666, 13);

                //Para cada bill, vão ser inseridos segmentos com id de [1 a 5], [6 a 10] e assim
                // sucessivamente
                for(int j = 5 * (i-1) + 1; j <= 5 * (i-1) + 5; j++) {
                    bill.setSegments(j);
                    numberOfSegmentsAdded++;
                }

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
        Log.d("deletedSegments = ", + deletedSegments + " billListSIZE = " + billList.size());
        Log.d("HasInserted: ", ""+hasInserted);

        final boolean isDbEmpty = segmentsOfBillDAO.isDatabaseEmpty();
        assertTrue(isDbEmpty && deletedSegments == numberOfSegmentsAdded);
    }


    @Test
    public void getAllSegmentsTest() {
        List<SegmentsOfBill> segmentsOfBillList= new ArrayList<>();
        try {
            for(int i = 1; i <= 5; i++) {
                SegmentsOfBill segmentsOfBill1 = new SegmentsOfBill(1, i);
                segmentsOfBillList.add(segmentsOfBill1);
            }
        } catch (SegmentsOfBillException e) {
            e.printStackTrace();
        }

        boolean result = true;
        for(int i = 0; i < segmentsOfBillList.size(); i++){
            result &= segmentsOfBillDAO.insertSegmentsOfBill(segmentsOfBillList.get(i));
        }

        List<SegmentsOfBill> segmentsOfBillListDAO = null;
        try {
            segmentsOfBillListDAO = segmentsOfBillDAO.getAllSegments();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        Log.d("SegmentsDAOSize: ", segmentsOfBillListDAO.size() + "");

        int numberOfEqualsSegments = 0;
        for(int i = 0; i < segmentsOfBillList.size(); i++) {
            for(int j = 0; j < segmentsOfBillListDAO.size(); j++) {
                if(segmentsOfBillList.get(i).equals(segmentsOfBillListDAO.get(j))){
                    numberOfEqualsSegments++;
                }
            }
        }

        Log.d("Result: ", result + "");
        Log.d("NumberEqualsSegm.: ", numberOfEqualsSegments + "");
        Log.d("SegmentsOfBillSize: ", segmentsOfBillList.size() + "");

        assertTrue(result && numberOfEqualsSegments == segmentsOfBillList.size());
    }

    @Test
    public void getAllSegmentsOfBillTest() {
        List<SegmentsOfBill> segmentsOfBillList= new ArrayList<>();
        try {
            for(int i = 1; i <= 5; i++) {
                SegmentsOfBill segmentsOfBill1 = new SegmentsOfBill(1, i);
                segmentsOfBillList.add(segmentsOfBill1);
            }
        } catch (SegmentsOfBillException e) {
            e.printStackTrace();
        }

        boolean result = true;
        for(int i = 0; i < segmentsOfBillList.size(); i++){
            result &= segmentsOfBillDAO.insertSegmentsOfBill(segmentsOfBillList.get(i));
        }

        List<SegmentsOfBill> segmentsOfBillListDAO = segmentsOfBillDAO.getAllSegmentsOfBill(1);

        int numberOfEqualsSegments = 0;
        for(int i = 0 ; i < segmentsOfBillList.size(); i++) {
            for(int j = 0; j < segmentsOfBillListDAO.size(); j++){
                if(segmentsOfBillList.get(i).equals(segmentsOfBillListDAO.get(j))){
                    numberOfEqualsSegments++;
                }
            }
        }

        assertTrue(result && numberOfEqualsSegments == segmentsOfBillList.size());
    }

}
