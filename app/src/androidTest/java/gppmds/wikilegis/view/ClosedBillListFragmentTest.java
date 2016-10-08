package gppmds.wikilegis.view;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Before;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by izabela on 06/10/16.
 */
public class ClosedBillListFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity>{

    public ClosedBillListFragmentTest() {
        super(LoginActivity.class);
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

        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.main_content)).perform(swipeLeft());
        Thread.sleep(2000);
    }

    public void testDefaultFilteringOption(){
        onView(withId(R.id.spinner_closed)).check(matches(withText("Relevantes")));
    }

    public void testChangFilteringOptionToRecent(){
        onView(withId(R.id.spinner_closed)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Recentes"))).perform(click());
        onView(withId(R.id.spinner_closed)).check(matches(withText("Recentes")));
    }

    public void testChangFilteringOptionToRelevant(){
        onView(withId(R.id.spinner_closed)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Recentes"))).perform(click());
        onView(withId(R.id.spinner_closed)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Relevantes"))).perform(click());
        onView(withId(R.id.spinner_closed)).check(matches(withText("Relevantes")));
    }
}
