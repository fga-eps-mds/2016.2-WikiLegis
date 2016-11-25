package gppmds.wikilegis.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import gppmds.wikilegis.dao.api.JSONHelper;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by josue on 11/4/16.
 */
public class VotesControllerTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    /*

    @Test
    public void testRegisterVote() throws SegmentException, JSONException, VotesException {

        VotesController votesController = VotesController.getInstance(context);

        String status =votesController.registerVote(118 ,true);

        assertEquals(status, "SUCCESS");
    }
    @Test
    public void testTetVoteByUserAndIdSegmentTrue() {
        boolean returnValue ;
        returnValue= JSONHelper.getVoteByUserAndIdSegment(118, 91, true);

        assertTrue(returnValue);
    }
    @Test
    public void testTetVoteByUserAndIdSegmentFalse() {
        boolean returnValue ;
        returnValue= JSONHelper.getVoteByUserAndIdSegment(118, 91, false);

        assertTrue(returnValue);
    }

    */

}

