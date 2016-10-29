package gppmds.wikilegis.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.VotesException;
import gppmds.wikilegis.model.Vote;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by shammyz on 9/30/16.
 */
public class VoteControllerTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testGetVotesByIdOfSegment(){
        VotesController votesController = VotesController.getInstance(context);
        try {
            votesController.initControllerVotes();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }
        List<Vote> voteList = new ArrayList<>();

        try{
            voteList = VotesController.getVotesByIdOfSegment(3927);

        }catch(VotesException e){
            e.printStackTrace();
        }
        assertEquals(voteList.size(), 18);
    }

    @Test
    public void testGetLikesOfSegment(){
        VotesController votesController = VotesController.getInstance(context);
        try {
            votesController.initControllerVotes();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        Integer likes = null;

        try{
            likes = VotesController.getLikesOfSegment(3927);

        }catch(VotesException e){
            e.printStackTrace();
        }
        assertTrue(likes == 18);
    }

    @Test
    public void testGetDislikesOfSegment(){
        VotesController votesController = VotesController.getInstance(context);
        try {
            votesController.initControllerVotes();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (VotesException e) {
            e.printStackTrace();
        } catch (SegmentException e) {
            e.printStackTrace();
        }

        Integer dislikes = null;

        try{
            dislikes = VotesController.getDislikesOfSegment(3944);

        }catch(VotesException e){
            e.printStackTrace();
        }
        assertTrue(dislikes == 1);
    }
}
