package gppmds.wikilegis.controller;

import android.util.Log;

import org.json.JSONException;

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

    public List<Bill> getAllBills(){
        return billList;
    }

    public void initControllerBills() throws BillException, JSONException, SegmentException {
        if (billDao.isDatabaseEmpty()) {

            billList = JSONHelper.billListFromJSON(JSONHelper.getJSONObjectApi("http://wikilegis.labhackercd.net/api/bills/"),
                    SegmentController.getAllSegments());
        } else {
            billList = billDao.getAllBills();
        }
    }

    public static int countedTheNumberOfProposals(List<Segment> segmentList, int id){

        int counter = 0;

        for (int index = 0; index < segmentList.size(); index++) {

            if (segmentList.get(index).getBill() == id) {
                if (segmentList.get(index).getReplaced() != 0) {
                        counter++;
                }
            }
        }
        return counter;
    }


}
