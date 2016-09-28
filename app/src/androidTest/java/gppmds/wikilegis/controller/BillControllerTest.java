package gppmds.wikilegis.controller;

import android.util.Log;

import gppmds.wikilegis.controller.BillController;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.BillDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by freemanpivo on 8/28/16.
 */
public class BillControllerTest {


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

    public void testGetBillWithNullProposalValue() {
        JSONObject jsonObject = null;
        Bill bill;
        boolean isValid = true;
        try{
            bill = BillController.getBill(null, 330, jsonObject);


        }catch (JSONException e){
            e.printStackTrace();
        } catch (BillException e) {
            isValid = false;
            e.printStackTrace();
        }
        assertFalse(isValid);
    }

    public void testGetBillWithNullDateValue() {
        JSONObject jsonObject = null;
        Bill bill;
        boolean isValid = true;
        try{
            bill = BillController.getBill(666, null, jsonObject);


        }catch (JSONException e){
            e.printStackTrace();
        } catch (BillException e) {
            isValid = false;
            e.printStackTrace();
        }
        assertFalse(isValid);
    }

}