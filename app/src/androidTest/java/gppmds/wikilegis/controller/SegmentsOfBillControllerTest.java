package gppmds.wikilegis.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.SegmentsOfBill;

/**
 * Created by marcelo on 9/30/16.
 */
public class SegmentsOfBillControllerTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }


    @Test
    public void testGetAllSegmentsOfBill() throws BillException, JSONException, SegmentException {
        SegmentsOfBillController segmentsOfBillController =
                SegmentsOfBillController.getInstance(context);
        SegmentController segmentController = SegmentController.getInstance(context);

        segmentController.initControllerSegments();
        segmentsOfBillController.initControllerSegmentsOfBill();

        List<SegmentsOfBill> segmentsOfBillList = new ArrayList<>();
        segmentsOfBillList = SegmentsOfBillController.getAllSegmentsOfBill(46);

        assert segmentsOfBillList.size() == 751;
    }

}
