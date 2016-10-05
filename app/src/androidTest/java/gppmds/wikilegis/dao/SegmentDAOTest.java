package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Segment;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by shammyz on 10/5/16.
 */
public class SegmentDAOTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void isDatabaseEmptyTest(){
        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);

        assertFalse(segmentDAO.isDatabaseEmpty());
    }

    @Test
    public void insertSegmentTest(){
        SegmentDAO segmentDAO = SegmentDAO.getInstance(context);
        try {
            Segment segment = new Segment(1, 2, 8, true, 55, 10, 1, 6, "blablabla", "13/12/2006");
            assertTrue(segmentDAO.insertSegment(segment));
        } catch (SegmentException e) {
            e.printStackTrace();
        }
    }
}
