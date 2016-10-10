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

import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
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
        String url = "http://wikilegis.labhackercd.net/api/bills/";
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
