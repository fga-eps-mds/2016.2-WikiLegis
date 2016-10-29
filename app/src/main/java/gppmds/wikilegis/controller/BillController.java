package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.dao.database.BillDAO;
import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentsOfBill;

public class BillController {

    private static List<Bill> billList = new ArrayList<Bill>();
    private static BillDAO billDao;
    private static Context context;
    private static BillController instance = null;


    private BillController(final Context context) {
        this.context = context;
    }

    public static BillController getInstance(final Context context) {
        if (instance == null) {
            instance = new BillController(context);
        }
        return  instance;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public List<Bill> getAllBills(){
        return billList;
    }

    public static Bill getBill(final Integer numberOfProposals, final Integer date,
                               final JSONObject jsonObject) throws BillException, JSONException {
        Bill billAux = new Bill(jsonObject.getInt("id"),
                jsonObject.getString("title"),
                jsonObject.getString("epigraph"),
                jsonObject.getString("status"),
                jsonObject.getString("description"),
                jsonObject.getString("theme"),
                numberOfProposals,
                date);
        return billAux;
    }

    public void initControllerBills() throws BillException, JSONException, SegmentException {

        billDao = BillDAO.getInstance(context);

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);
        String date = session.getString(context.getResources().getString(R.string.last_downloaded_date), "2010-01-01");

        List<Bill> newBills = JSONHelper.billListFromJSON(JSONHelper.requestJsonObjectFromApi
                        ("http://wikilegis-staging.labhackercd.net/api/bills/?created="+date),
                SegmentController.getAllSegments());
        Log.d("data", date);

        billDao.insertAllBills(newBills);

        billList = billDao.getAllBills();
    }
    public void DownloadBills() throws BillException, JSONException, SegmentException {
        billList = JSONHelper.billListFromJSON(JSONHelper.requestJsonObjectFromApi("http://wikilegis.labhackercd.net/api/bills/"),
                SegmentController.getAllSegments());
    }


    public static List<Segment> getSegmentsFromIdOfBill(final int idBill) {
        List<Segment> listSegment;
        List<SegmentsOfBill> segmentsOfBillList;

        listSegment = new ArrayList<Segment>();

        segmentsOfBillList = SegmentsOfBillController.getAllSegmentsOfBill(idBill);

        Log.d("HAHAHA", segmentsOfBillList.size() + "");

        for (int i = 0; i < segmentsOfBillList.size(); i++) {
            try {
                Segment segmentAux = SegmentController.getSegmentById(segmentsOfBillList.get(i)
                        .getIdSegment());
                if (segmentAux.getOrder() != 0)
                    listSegment.add(segmentAux);
            } catch (SegmentException e) {
                e.printStackTrace();
            }
        }

        SegmentComparatorOrder segmentComparatorOrder = new SegmentComparatorOrder();
        Collections.sort(listSegment, segmentComparatorOrder);

        return listSegment;
    }

    public static int countedTheNumberOfProposals(final List<Segment> segmentList,
                                                  final int idBill) {

        int numberOfProposals = 0;

        for (int index = 0; index < segmentList.size(); index++) {
            if (segmentList.get(index).getBill() == idBill) {
                if (segmentList.get(index).getReplaced() != 0) {
                        numberOfProposals++;
                }
            }
        }
        return numberOfProposals;
    }

    public static Bill getBillByIdFromList(final int id){
        for (Bill bill : billList){
            if(bill.getId() == id){
                return bill;
            }
        }
        return null;
    }

    public static Bill getBillById(final int id) throws BillException {
        billDao = BillDAO.getInstance(context);

        return billDao.getBillById(id);
    }

    public List<Bill> filterigForStatusClosed(final List<Bill> listToFiltering) {
        List<Bill> billListWithStatusClosed = new ArrayList<Bill>();

        for (int index = 0; index < listToFiltering.size(); index++) {
            if (listToFiltering.get(index).getStatus().equals("closed")) {
                billListWithStatusClosed.add(listToFiltering.get(index));
            }
        }
        return billListWithStatusClosed;
    }

    public List<Bill> filterigForStatusPublished(final List<Bill> listToFiltering) {
        List<Bill> billListWithStatusPublished = new ArrayList<Bill>();


        for (int index = 0; index < listToFiltering.size(); index++) {
            if (listToFiltering.get(index).getStatus().equals("published")) {
                billListWithStatusPublished.add(listToFiltering.get(index));
            }
        }
        return billListWithStatusPublished;
    }

    public static List<Bill> filteringForNumberOfProposals(final List<Bill> listToFiltering) {
        List<Bill> billListAux = new ArrayList<>();

        BillComparatorProposals billComparatorDProposals = new BillComparatorProposals();

        for (int i = 0; i < listToFiltering.size(); i++) {
            billListAux.add(listToFiltering.get(i));
        }

        Collections.sort(billListAux, billComparatorDProposals);

        return billListAux;

    }

    public List<Bill> filteringForDate(final List<Bill> listToFiltering) {
        List<Bill> billListAux = new ArrayList<>();

        BillComparatorDate comparator = new BillComparatorDate();

        for (int i = 0; i < listToFiltering.size(); i++) {
            billListAux.add(listToFiltering.get(i));
        }

        Collections.sort(billListAux, comparator);

        return billListAux;
    }
}
