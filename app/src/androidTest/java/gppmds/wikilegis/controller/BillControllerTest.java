package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.api.BillJsonHelper;
import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.dao.database.BillDAO;
import gppmds.wikilegis.dao.database.SegmentDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by freemanpivo on 8/28/16.
 */
public class BillControllerTest {

    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testGetSegmentsFromIdOfBill() throws BillException, JSONException, SegmentException {
        BillDAO billDAO = BillDAO.getInstance(context);
        billDAO.deleteAllBills();

        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);
        segmentDAO.deleteAllSegments();


        SegmentController segmentController = SegmentController.getInstance(context);

        BillController billController = BillController.getInstance(context);

        Bill billToInsert = new Bill(1, "Titulo", "Epigraph", "status", "Description", "theme",
                1, 20160008);

        billDAO.insertBill(billToInsert);

        final int BILL_ID = 1;

        List<Integer> segmentsList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Segment newSegment = new Segment(i, i, BILL_ID, 1, 0, 1, 1, 1, "Content", "2015-07-09");
            segmentDAO.insertSegment(newSegment);
            billToInsert.setSegments(newSegment.getId());
        }

        segmentsList = billToInsert.getSegments();

        segmentController.initControllerSegments();
        billController.initControllerBills();

        List<Segment> foundSegments = segmentController.getSegmentsByIdBill(BILL_ID);

        int countEquals = 0;

        for(int i = 0; i < segmentsList.size(); i++) {
            for(int j = 0; j < foundSegments.size(); j++) {
                if(segmentsList.get(i) == foundSegments.get(j).getId()) {
                    countEquals++;
                }
            }
        }

        final Integer NUMBER_OF_SEGMENTS_OF_BILL = 5;

        Log.d("segmentL s", segmentsList.size() + "");
        Log.d("foundS s", foundSegments.size() + "");
        Log.d("countEq", countEquals + "");

        assertTrue(segmentsList.size() == foundSegments.size()
                && NUMBER_OF_SEGMENTS_OF_BILL == countEquals);

        billDAO.deleteAllBills();
    }


    @Test
    public void testGetBillByValidId() throws BillException, SegmentException, JSONException{
        BillDAO billDAO = BillDAO.getInstance(context);
        billDAO.deleteAllBills();

        Bill billToInsert = new Bill(1, "Titulo", "Epigraph", "status", "Description", "theme",
        1, 20160008);

        billDAO.insertBill(billToInsert);

        BillController billController = BillController.getInstance(context);

        Bill bill = billController.getBillById(1);
        assertEquals(bill.getId()+"", "1");
    }

    @Test
    public void testGetBillByInvalidId() throws BillException, SegmentException, JSONException{
        BillDAO billDAO = BillDAO.getInstance(context);
        billDAO.deleteAllBills();

        Bill billToInsert = new Bill(1, "Titulo", "Epigraph", "status", "Description", "theme",
                1, 20160008);

        billDAO.insertBill(billToInsert);

        BillController billController = BillController.getInstance(context);

        Bill bill = billController.getBillById(666);

        assertNull(bill);
        billDAO.deleteAllBills();
    }

    @Test
    public void testGetAllBills() throws BillException, JSONException, SegmentException{
        final String date = "2010-01-01";
        List<Bill> billsFromAPI = new ArrayList<>();
        BillController billController;
        billController = BillController.getInstance(context);
        BillDAO billDAO = BillDAO.getInstance(context);
        billDAO.deleteAllBills();


        try {
            billsFromAPI = JSONHelper.billListFromJSON(JSONHelper.requestJsonObjectFromApi
                            ("http://wikilegis-staging.labhackercd.net/api/bills/?created="+date));

            SharedPreferences session = PreferenceManager.
                    getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = session.edit();
            editor.putString("date", date);
            editor.commit();

            DataDownloadController dataDownloadController = DataDownloadController.getInstance(context);
            dataDownloadController.updateBills();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }

        List<Bill> billsFromDB = billController.getAllBills();

        int numberOfEqualsBills = 0;
        for(int i = 0; i < billsFromDB.size(); i++) {
            for(int j = 0; j < billsFromAPI.size(); j++) {
                Bill billFromAPI = billsFromAPI.get(j);
                Bill billFromDB = billsFromDB.get(i);

                if(billFromAPI.equals(billFromDB)) {
                    numberOfEqualsBills++;
                }
            }
        }

        Log.d("bill api", billsFromAPI.size() + "");
        Log.d("bill db", billsFromDB.size() + "");
        Log.d("nb eql", numberOfEqualsBills + "");

        assertTrue(billsFromDB.size() > 0);

        assertTrue(billsFromAPI.size() == billsFromDB.size() &&
                numberOfEqualsBills == billsFromDB.size());
    }

    @Test
    public void testCountedNumberOfProposalsWithDifferentBills() {

        List<Segment> segmentList = new ArrayList<>();

        int count;


        try {
            Segment segment = new Segment(1, 10, 2, true, 3, 4, 5, 6, "content", "Date");
            Segment segment2 = new Segment(2, 10, 2, true, 11, 4, 5, 6, "content", "Date");
            Segment segment3 = new Segment(3, 10, 1, true, 23, 4, 5, 6, "content", "Date");
            Segment segment4 = new Segment(4, 10, 3, true, 48, 4, 5, 6, "content", "Date");

            segmentList.add(segment);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        count = BillController.countedTheNumberOfProposals(segmentList, 2);

        assertEquals(count, 2);

    }

    @Test
    public void testCountedNumberOfProposalsWithSegmentsWithoutProposals() {

        int count;
        List<Segment> segmentList = new ArrayList<>();

        try {
            Segment segment = new Segment(1, 10, 2, true, 0, 4, 5, 6, "content", "Date");
            Segment segment2 = new Segment(2, 10, 2, true, 11, 4, 5, 6, "content", "Date");
            Segment segment3 = new Segment(3, 10, 2, true, 0, 4, 5, 6, "content", "Date");
            Segment segment4 = new Segment(4, 10, 2, true, 48, 4, 5, 6, "content", "Date");

            segmentList.add(segment);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        count = BillController.countedTheNumberOfProposals(segmentList, 2);

        assertEquals(count, 2);

    }

    @Test

    public void testCountedNumberOfProposalsWithoutProposals() {
        int count;
        List<Segment> segmentList = new ArrayList<>();

        try {
            Segment segment = new Segment(1, 10, 2, true, 0, 4, 5, 6, "content", "Date");
            Segment segment2 = new Segment(2, 10, 2, true, 0, 4, 5, 6, "content", "Date");
            Segment segment3 = new Segment(3, 10, 2, true, 0, 4, 5, 6, "content", "Date");
            Segment segment4 = new Segment(4, 10, 2, true, 0, 4, 5, 6, "content", "Date");

            segmentList.add(segment);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        count = BillController.countedTheNumberOfProposals(segmentList, 2);

        assertEquals(count, 0);

    }


    @Test
    public void testCountedNumberOfProposalsFulltProposals() {

        int count;
        List<Segment> segmentList = new ArrayList<>();

        try {
            Segment segment = new Segment(1, 10, 2, true, 15, 4, 5, 6, "content", "Date");
            Segment segment2 = new Segment(2, 10, 2, true, 16, 4, 5, 6, "content", "Date");
            Segment segment3 = new Segment(3, 10, 2, true, 17, 4, 5, 6, "content", "Date");
            Segment segment4 = new Segment(4, 10, 2, true, 18, 4, 5, 6, "content", "Date");

            segmentList.add(segment);
            segmentList.add(segment2);
            segmentList.add(segment3);
            segmentList.add(segment4);

        } catch (SegmentException e) {
            e.printStackTrace();
        }

        count = BillController.countedTheNumberOfProposals(segmentList, 2);

        assertEquals(count, 4);

    }

    @Test
    public void testGetBill(){
        JSONObject jsonObject = new JSONObject();
        Bill bill;
        try{
            jsonObject.put("id", 2);
            jsonObject.put("title", "Alou");
            jsonObject.put("epigraph", "Iup");
            jsonObject.put("status", "closed");
            jsonObject.put("description", "blablablabla");
            jsonObject.put("theme", "Meio Ambiente");

            bill = BillController.getBill(666, 330, jsonObject);

            assertEquals(bill.getId().toString(), "2");
            assertEquals(bill.getTitle(), "Alou");
            assertEquals(bill.getEpigraph(), "Iup");
            assertEquals(bill.getStatus(), "closed");
            assertEquals(bill.getDescription(), "blablablabla");
            assertEquals(bill.getTheme(), "Meio Ambiente");

        }catch (JSONException e){
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }
    }

    @Test

    public void testGetBillWithNullValues() {
        JSONObject jsonObject = null;
        Bill bill;
        boolean isValid = true;
        try{
            bill = BillController.getBill(666, 330, jsonObject);


        }catch (JSONException e){
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }catch(NullPointerException e){
            isValid = false;
            e.printStackTrace();
        }
        assertFalse(isValid);
    }

    @Test
    public void testGetBillWithNullProposalValue() {
        JSONObject jsonObject = new JSONObject();
        Bill bill;
        boolean isValid = true;
        try{
            jsonObject.put("id", 2);
            jsonObject.put("title", "Alou");
            jsonObject.put("epigraph", "Iup");
            jsonObject.put("status", "closed");
            jsonObject.put("description", "blablablabla");
            jsonObject.put("theme", "Meio Ambiente");

            bill = BillController.getBill(null, 330, jsonObject);

        }catch (JSONException e){
            e.printStackTrace();
        } catch (BillException e) {
            isValid = false;
        }

        assertFalse(isValid);
    }

    @Test
    public void testGetBillWithNullDateValue() {
        JSONObject jsonObject = new JSONObject();
        Bill bill;
        boolean isValid = true;
        try{
            jsonObject.put("id", 2);
            jsonObject.put("title", "Alou");
            jsonObject.put("epigraph", "Iup");
            jsonObject.put("status", "closed");
            jsonObject.put("description", "blablablabla");
            jsonObject.put("theme", "Meio Ambiente");

            bill = BillController.getBill(666, null, jsonObject);

        }catch (JSONException e){
            e.printStackTrace();
        } catch (BillException e) {
            isValid = false;
            e.printStackTrace();
        }

        assertFalse(isValid);
    }

    @Test
    public void testFilteringForNumberOfProposals(){
        List<Bill> billList = new ArrayList<>();
        List<Bill> filteredBillList;

        try{
            Bill bill1 = new Bill(1, "Teste1", "teste1", "closed", "teste1", "teste1", 10, 1);
            Bill bill2 = new Bill(2, "Teste2", "teste2", "published", "teste2", "teste2", 2, 1);
            Bill bill3 = new Bill(3, "Teste3", "teste3", "closed", "teste3", "teste3", 5, 1);
            Bill bill4 = new Bill(4, "Teste4", "teste4", "published", "teste4", "teste4", 3, 1);
            Bill bill5 = new Bill(5, "Teste5", "teste5", "closed", "teste5", "teste5", 2, 1);

            billList.add(bill1);
            billList.add(bill2);
            billList.add(bill3);
            billList.add(bill4);
            billList.add(bill5);
        } catch (BillException e) {
            e.printStackTrace();
        }

        filteredBillList = BillController.filteringForNumberOfProposals(billList);

        assertEquals(filteredBillList.get(0).getNumberOfPrposals(),10);
        assertEquals(filteredBillList.get(1).getNumberOfPrposals(), 5);
        assertEquals(filteredBillList.get(2).getNumberOfPrposals(),3);
        assertEquals(filteredBillList.get(3).getNumberOfPrposals(),2);
        assertEquals(filteredBillList.get(4).getNumberOfPrposals(),2);
    }

    @Test
    public void testGetAllBillsFromApi() {
        BillController billController = BillController.getInstance(context);

        List<Bill> billsFromApi = new ArrayList<>();

        try {
            billsFromApi = BillJsonHelper.getAllBillFromApi();
            BillController.getAllBillsFromApi();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }

        List<Bill> bills = billController.getAllBills();

        int countEquals = 0;

        for(Bill bill : bills) {
            for(Bill billFromApi : billsFromApi) {
                if(bill.equals(billFromApi)) {
                    countEquals++;
                }
            }
        }

        Log.d("Size", billsFromApi.size() + "");

        assertTrue(countEquals == billsFromApi.size());
    }

    @Test
    public void testGetBillByIdFromApi() {
        List<Bill> bills = new ArrayList<>();
        int idBill = -1;
        Bill bill = null;
        try {
            bills = BillJsonHelper.getAllBillFromApi();
            BillController.getAllBillsFromApi();
            idBill = bills.get(0).getId();
            bill = BillController.getBillByIdFromApi(idBill);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BillException e) {
            e.printStackTrace();
        }

        assertTrue(idBill == bill.getId());
    }

    /*@Test
    public void testActivateNotification() {

        BillController billController = BillController.getInstance(context);
        String test = billController.activiteNotification("weekly")
        assertTrue("200".equals(test));
    }*/
}