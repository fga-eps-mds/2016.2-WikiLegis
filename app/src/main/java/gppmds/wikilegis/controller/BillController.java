package gppmds.wikilegis.controller;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.dao.BillDAO;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.dao.SegmentDAO;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.view.FilteringFragment;

/**
 * Created by josue on 9/11/16.
 */
public class BillController {

    private static List<Bill> billList = new ArrayList<Bill>();
    FilteringFragment filteringFragment = new FilteringFragment();
    private BillDAO billDao;
    private Context context;

    public BillController(Context context) {
        this.context = context;
    }

    public List<Bill> getAllBills(){
        return billList;
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
