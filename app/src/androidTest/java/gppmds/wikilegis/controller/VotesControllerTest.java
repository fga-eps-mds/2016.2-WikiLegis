package gppmds.wikilegis.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;

import static junit.framework.Assert.assertEquals;

/**
 * Created by josue on 11/4/16.
 */
public class VotesControllerTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testRegisterVote() throws SegmentException, JSONException, VotesException {

        VotesController votesController = VotesController.getInstance(context);

        String status =votesController.registerVote(118 ,true);

        assertEquals(status, "SUCCESS");
    }
}

