package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.dao.database.SegmentsOfBillDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.SegmentsOfBill;

public class SegmentsOfBillController {

    private static List<SegmentsOfBill> segmentsOfBillList = new ArrayList<SegmentsOfBill>();
    private static SegmentsOfBillDAO segmentsOfBillDAO;
    private static Context context;
    private static SegmentsOfBillController instance = null;

    private SegmentsOfBillController(final Context contextParamter) {
        this.context = contextParamter;
    }

    public static SegmentsOfBillController getInstance(final Context contextParamter) {
        if (instance == null) {
            instance = new SegmentsOfBillController(contextParamter);
        }
        return  instance;
    }

    public static List<SegmentsOfBill> getAllSegmentsOfBill(final  Integer id) {
        segmentsOfBillDAO = SegmentsOfBillDAO.getInstance(context);
        return segmentsOfBillDAO.getAllSegmentsOfBill(id);
    }

    public void initControllerSegmentsOfBill() throws BillException,
                                                      JSONException, SegmentException {

        segmentsOfBillDAO = SegmentsOfBillDAO.getInstance(context);
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);
        String date = session.getString(context.getResources().getString(R.string.last_downloaded_date),"2010-01-01");
        Log.d("data", date);


        BillController billController = BillController.getInstance(context);

        List<Bill> billList = billController.getAllBills();

        segmentsOfBillDAO.insertAllSegmentsOfBills(billList);

        segmentsOfBillList = segmentsOfBillDAO.getAllSegments();
    }
}
