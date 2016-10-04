package gppmds.wikilegis.view;

import android.app.Activity;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by thiago on 9/30/16.
 */
public class ViewSegmentFragmentTest extends ActivityInstrumentationTestCase2<LoadingActivity> {

    public ViewSegmentFragmentTest(){
        super(LoadingActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        final Activity activityOnTest = getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activityOnTest.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activityOnTest.runOnUiThread(wakeUpDevice);

        //Redirecting to ViewSegmentFragment
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(click());
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    public void testTitleBillIsDisplayed(){
        onView(withId(R.id.titleBill)).check(matches(isDisplayed()));
    }

    public void testConstentSegmentIsDisplayed(){
        onView(withId(R.id.contentSegment)).check(matches(isDisplayed()));
    }

    public void testNumberOfLikeIsDisplayed(){
        onView(withId(R.id.textViewNumberLike)).check(matches(isDisplayed()));
    }

    public void testImageLikeIsDisplayed(){
        onView(withId(R.id.imageViewLike)).check(matches(isDisplayed()));
    }

    public void testNumberOfDislikeIsDisplayed(){
        onView(withId(R.id.textViewNumberLike)).check(matches(isDisplayed()));
    }

    public void testImageDislikeIsDisplayed(){
        onView(withId(R.id.imageViewDislike)).check(matches(isDisplayed()));
    }

    public void testProposalIsDisplayed(){
        onView(withId(R.id.textViewProposal)).check(matches(isDisplayed()));
    }

    public void testImageProposalIsDisplayed(){
        onView(withId(R.id.imageViewProposal)).check(matches(isDisplayed()));
    }
}