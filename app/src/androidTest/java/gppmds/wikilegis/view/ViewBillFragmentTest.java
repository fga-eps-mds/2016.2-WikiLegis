package gppmds.wikilegis.view;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Before;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ViewBillFragmentTest extends ActivityInstrumentationTestCase2<LoadingActivity> {


    public ViewBillFragmentTest(){
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
    }

    public void testByClickASegmentThatShouldNotBeClickable() throws InterruptedException {
        //Redirecting to ViewSegmentFragment
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.main_content)).perform(swipeLeft());
        Thread.sleep(2000);
        onView(withId(R.id.recycler_view_closed))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.textViewProposal)).check(matches(isDisplayed()));
    }

    public void testByClickASegmentThatShouldBeClickable() throws InterruptedException {
        //Redirecting to ViewSegmentFragment
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.main_content)).perform(swipeLeft());
        Thread.sleep(2000);
        onView(withId(R.id.recycler_view_closed))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.imageViewLike)).check(matches(isDisplayed()));
    }
}
