package gppmds.wikilegis.view;

import android.app.Activity;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by josue on 10/20/16.
 */
public class CreateSuggestProposalTest extends ActivityInstrumentationTestCase2<LoadingActivity> {

    public CreateSuggestProposalTest(Class<LoadingActivity> activityClass) {
        super(activityClass);

    }
    @Before
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

    public void test(){

        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewSegment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.floatingButton))
                .perform((click()));
        onView(withId(R.id.emailLoginField)).check(matches(isDisplayed()));

    }
}
