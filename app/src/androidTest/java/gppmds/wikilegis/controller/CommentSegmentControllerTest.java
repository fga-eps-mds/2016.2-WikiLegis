package gppmds.wikilegis.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import gppmds.wikilegis.exception.CommentsException;
import static junit.framework.Assert.assertEquals;

public class CommentSegmentControllerTest {
    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testRegisterCommentSuccessful () {
        String expectedStatus = "SUCCESS";
        String receivedStatus = "";
        CommentSegmentController commentSegmentController = CommentSegmentController
                .getInstance(context);

        try {
            receivedStatus = commentSegmentController.registerComment(1, "Testando comentario", context);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (CommentsException e) {
            e.printStackTrace();
        }

        assertEquals(expectedStatus, receivedStatus);
    }

    @Test
    public void testRegisterCommentJsonFail () {

    }

    @Test
    public void testRegisterCommentFail () {

    }
}
