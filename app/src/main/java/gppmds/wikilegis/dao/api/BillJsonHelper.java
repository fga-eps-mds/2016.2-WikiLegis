package gppmds.wikilegis.dao.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

/**
 * Created by lucas on 10/19/16.
 */
public class BillJsonHelper {

    public static List<Bill> getAllBillFromApi() throws JSONException, BillException {
        String url = "http://wikilegis-staging.labhackercd.net/api/bills/";

        List<Bill> billListApi = new ArrayList<>();

        do {
            String billList = JSONHelper.requestJsonObjectFromApi(url);

            JSONObject jsonBill = new JSONObject(billList);
            JSONArray results = jsonBill.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonObject = results.getJSONObject(i);

                billListApi.add(setBillAttributes(jsonObject));
            }

            String nextUrl = jsonBill.getString("next");
            url = nextUrl; //updateDomain(nextUrl);
            Log.d("URL", nextUrl);

        } while (!url.equals("null"));

        return billListApi;

    }

    public static Bill getBillFromApiById(int id) throws JSONException, BillException {
        List<Bill> list = getAllBillFromApi();
        Bill foundBill = null;
        for(Bill bill:list){
            if(bill.getId() == id){
                foundBill = bill;
                break;
            }
        }

        return foundBill;
    }

    private static Bill setBillAttributes(JSONObject billJson) throws BillException, JSONException {
        Bill bill = null;
        bill = new Bill(billJson.getInt("id"),billJson.getString("title"),
                billJson.getString("epigraph"),billJson.getString("status"),
                billJson.getString("description"),billJson.getString("theme"),billJson.getInt("proposals_count"),
                convertDate(billJson.getString("created")));

        JSONArray segments = billJson.getJSONArray("segments");

        for(int i = 0; i < segments.length();i++){
            bill.setSegments(segments.getInt(i));
        }

        return bill;
    }

    private static int convertDate (String date){
        Integer day, month, year;
        Integer aux = 20170000, result;

        String array[] = new String[2];
        String arrayDate[] = new String[3];

        array = date.split("T");
        arrayDate = array[0].split("-");
        day = Integer.parseInt(arrayDate[2]);
        month = Integer.parseInt(arrayDate[1]);
        year = Integer.parseInt(arrayDate[0]);
        result = year * 10000 + month * 1000 + day;

        if (result < aux) {
            aux = result;
        }
        return aux;
    }

}
