package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.dao.api.GetRequest;
import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

import static org.junit.Assert.assertTrue;

public class JSONHelperTest {
    Context context;
    JSONHelper jsonHelper;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getJSONObjectApiTest() {
        // return String, http://wikilegis.labhackercd.net/api/bills/
        String getApi = "";
        String url = context.getString(R.string.bills_url);
        GetRequest request = new GetRequest();
        boolean result = false;
        getApi = request.execute(url).toString();

        try {
            getApi = request.get().toString();
            result = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertTrue(result && !getApi.isEmpty());
    }

    @Test
    public void billListFromJSONTest() {
        String billList = JSONHelper.requestJsonObjectFromApi(context.getString(R.string.bills_url));
        List<Bill> billListApi = new ArrayList<>();
        JSONObject bills = null;

        try {
            bills = new JSONObject(billList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray results = new JSONArray();
        try {
            results = bills.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Integer numberOfProposals = 0;
        Integer date = 0;
        int id = 0;
        boolean canGetId = false;
        boolean canGetBill = false;
        boolean canAssignSegment = false;
        boolean canSetSegment = false;

        List<Segment> aux = SegmentController.getAllSegments();
        for (int index = 0; index < results.length(); index++){

            JSONObject jsonObject = null;
            try {
                jsonObject = results.getJSONObject(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                id = jsonObject.getInt("id");
                canGetId = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            numberOfProposals = BillController.countedTheNumberOfProposals(aux, id);

            date= SegmentController.getMinDate(id);

            Bill billAux = null;
            try {
                billAux = BillController.getBill(numberOfProposals, date, jsonObject);
                canGetBill = true;
            } catch (BillException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray segments = null;
            try {
                segments = jsonObject.getJSONArray("segments");
                canAssignSegment = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < segments.length(); j++) {

                try {
                    billAux.setSegments(segments.getInt(j));
                    canSetSegment = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            billListApi.add(billAux);
        }

        assertTrue(canGetId && canAssignSegment && canGetBill && canSetSegment);
    }

    @Test
    public void votesListFromJSONTest() {

    }

    @Test
    public void segmentListFromJSONTest() {

    }

    @Test
    public void updateDomainTest() {

    }
}
