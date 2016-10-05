package gppmds.wikilegis.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void insertAllVotesTest(){
        VotesDAO votesDAO = VotesDAO.getInstance(context);
        List<Votes> votesList = new ArrayList<>();
        Votes vote1 = null;
        Votes vote2 = null;
        Votes vote3 = null;
        Votes vote4 = null;
        Votes vote5 = null;

        try {
            vote1 = new Votes(1, 2, 3, true);
            vote2 = new Votes(2, 2, 3, true);
            vote3 = new Votes(3, 2, 3, true);
            vote4 = new Votes(4, 2, 3, true);
            vote5 = new Votes(5, 2, 3, true);
        } catch (VotesException e) {
            e.printStackTrace();
        }

        votesList.add(vote1);
        votesList.add(vote2);
        votesList.add(vote3);
        votesList.add(vote4);
        votesList.add(vote5);

        assertTrue(votesDAO.insertAllVotes(votesList));
    }

    @Test
    public void getAllVotesTest(){
        VotesDAO votesDAO = VotesDAO.getInstance(context);
        List<Votes> votesList = null;

        try {
            votesList = votesDAO.getAllVotes();
        } catch (VotesException e) {
            e.printStackTrace();
        }

        assertTrue(votesList.get(0).getUserId() == 1);
    }
}
