package gppmds.wikilegis.view;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Before;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by shammyz on 10/13/16.
 */
public class AboutFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    public AboutFragmentTest(){super(LoginActivity.class);}

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

    public void testLogoIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.aboutApp)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.imageViewLogoAbout)).check(matches(isDisplayed()));
    }

    public void testTextViewIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.aboutApp)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.aboutText)).check(matches(isDisplayed()));
    }
}
