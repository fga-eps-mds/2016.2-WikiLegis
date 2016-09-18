package gppmds.wikilegis.controller;

import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.dao.SegmentsOfBillDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.SegmentsOfBill;

/**
 * Created by marcelo on 9/16/16.
 */
public class SegmentsOfBillController {

    private static List<SegmentsOfBill> segmentsOfBillList = new ArrayList<SegmentsOfBill>();
    private static SegmentsOfBillDAO segmentsOfBillDAO;
    private static Context context;
    private static SegmentsOfBillController instance = null;

    private SegmentsOfBillController(Context context) {
        this.context = context;
    }

    public static SegmentsOfBillController getInstance(Context context){
        if(instance == null){
            instance = new SegmentsOfBillController(context);
        }
        return  instance;
    }

    public static List<SegmentsOfBill> getAllSegmentsOfBill(Integer id){
        segmentsOfBillDAO = SegmentsOfBillDAO.getInstance(context);
        return segmentsOfBillDAO.getAllSegmentsOfBill(id);
    }

    public void initControllerSegmentsOfBill() throws BillException, JSONException, SegmentException {

        segmentsOfBillDAO = SegmentsOfBillDAO.getInstance(context);

        if (segmentsOfBillDAO.isDatabaseEmpty()) {
            List<Bill> billList = JSONHelper.billListFromJSON(JSONHelper.getJSONObjectApi("http://wikilegis.labhackercd.net/api/bills/"),
                    SegmentController.getAllSegments());
            segmentsOfBillDAO.insertAllSegmentsOfBills(billList);
        } else {
            segmentsOfBillList = segmentsOfBillDAO.getAllSegments();
        }
    }

}
