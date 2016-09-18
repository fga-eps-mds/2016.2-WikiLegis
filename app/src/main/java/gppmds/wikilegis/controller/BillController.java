package gppmds.wikilegis.controller;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.BillDAO;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

/**
 * Created by josue on 9/11/16.
 */
public class BillController {

    private static List<Bill> billList = new ArrayList<Bill>();
    private static BillDAO billDao;
    private static Context context;


    public BillController(Context context) {
        this.context = context;
    }

    public List<Bill> getAllBills(){
        return billList;
    }


    public static Bill getBill(Integer numberOfProposals, Integer date, JSONObject f) throws BillException, JSONException {
        Bill billAux = new Bill(f.getInt("id"),
                f.getString("title"),
                f.getString("epigraph"),
                f.getString("status"),
                f.getString("description"),
                f.getString("theme"), numberOfProposals, date);
        return billAux;
    }

    public void initControllerBills() throws BillException, JSONException, SegmentException {

        billDao = BillDAO.getInstance(context);

        if (billDao.isDatabaseEmpty()) {

            billList = JSONHelper.billListFromJSON(JSONHelper.getJSONObjectApi("http://wikilegis.labhackercd.net/api/bills/"),
                    SegmentController.getAllSegments());

            billDao.insertAllBills(billList);

        } else {
            billList = billDao.getAllBills();
        }
    }

    public static int countedTheNumberOfProposals(List<Segment> segmentList, int idBill){

        int counter = 0;

        for (int index = 0; index < segmentList.size(); index++) {

            if (segmentList.get(index).getBill() == idBill) {
                if (segmentList.get(index).getReplaced() != 0) {
                        counter++;
                }
            }
        }
        return counter;
    }

    public static int countedNumberOfParticipants(List<Segment> segmentList, int idBill) {

        int counter = 0;

        for(int index = 0; index < segmentList.size(); index++) {
            if(segmentList.get(index).getBill() == idBill) {

            }
        }
        return counter;
    }

    public static Bill getBillById(int id) throws BillException {
        billDao = BillDAO.getInstance(context);

        return billDao.getBillById(id);
    }

}
