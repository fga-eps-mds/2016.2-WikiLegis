package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Votes;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by shammyz on 10/5/16.
 */
public class VotesDAOTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void isDatabaseEmpty(){
        VotesDAO votesDAO = VotesDAO.getInstance(context);

        assertFalse(votesDAO.isDatabaseEmpty());
    }

    @Test
    public void insertVoteDAOTest(){
        VotesDAO votesDAO = VotesDAO.getInstance(context);
        Votes vote = null;
        try {
            vote = new Votes(1, 2, 3, true);
        } catch (VotesException e) {
            e.printStackTrace();
        }
        assertTrue(votesDAO.insertVote(vote));
    }
}
