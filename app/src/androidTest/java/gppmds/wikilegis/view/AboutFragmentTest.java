package gppmds.wikilegis.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
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
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }

        WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = true;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
