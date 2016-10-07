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
    VotesDAO votesDAO;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();

        votesDAO = VotesDAO.getInstance(context);

        votesDAO.clearVotesTable();
    }

    @Test
    public void isDatabaseEmptyWhenDatabaseIsEmptyTest() {
        assertTrue(votesDAO.isDatabaseEmpty());
    }

    @Test
    public void isDatabaseEmptyWhenDatabaseIsNotEmptyTest() {
        Votes vote = null;

        try {
            vote = new Votes(1, 2, 3, true);
        } catch (VotesException e) {
            e.printStackTrace();
        }

        votesDAO.insertVote(vote);

        assertFalse(votesDAO.isDatabaseEmpty());
    }

    @Test
    public void insertVoteDAOTest() throws VotesException {
        Votes vote = null;

        try {
            vote = new Votes(1, 2, 3, true);
        } catch (VotesException e) {
            e.printStackTrace();
        }

        votesDAO.insertVote(vote);

        List<Votes> votesList = new ArrayList<>();
        
        try {
            votesList = votesDAO.getAllVotes();
        } catch (VotesException e) {
            e.printStackTrace();
        }

        /*
        * final Integer userId, final Integer contentType,
                 final Integer objectId, final boolean vote
        * */


        int countEqualsVotes = 0;
        for(int i = 0; i < votesList.size(); i++){
            Votes voteDB = votesList.get(i);
            if(voteDB.equals(vote)) {
                countEqualsVotes++;
            }
        }
        Log.i("COUNTEQUALS - ", ""+countEqualsVotes);
        Log.i("Voteslist", ""+votesList.size());

        assertTrue(countEqualsVotes == 1 && votesList.size() == 1);

    }

    @Test
    public void insertAllVotesAndGetAllVotesTest(){
        List<Votes> votesList1 = new ArrayList<>();

        try {
            for(int i = 1; i <= 5; i++){
                Votes vote = new Votes(i, 2, 3, true);
                votesList1.add(vote);
            }
        } catch (VotesException e) {
            e.printStackTrace();
        }

        votesDAO.insertAllVotes(votesList1);

        List<Votes> votesList2 = new ArrayList<>();

        try {
            votesList2 = votesDAO.getAllVotes();
        } catch (VotesException e) {
            e.printStackTrace();
        }


        int count = 0;
        for(int i = 0; i < votesList2.size(); i++) {
            if(votesList2.get(i).equals(votesList1.get(i))) {
                count++;
            }
        }

        assertTrue(count == votesList2.size());
    }

    @Test
    public void getVotesByIdOfSegmentTest(){
        VotesDAO votesDAO = VotesDAO.getInstance(context);
        List<Votes> votesList = null;

        try {
            votesList = votesDAO.getVotesByIdOfSegment(13172);
        } catch (VotesException e) {
            e.printStackTrace();
        }

        assertTrue(votesList.size() == 0);
    }
}
