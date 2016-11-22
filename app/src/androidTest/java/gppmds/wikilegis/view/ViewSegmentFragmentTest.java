package gppmds.wikilegis.view;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.After;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.controller.SegmentController;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by thiago on 9/30/16.
 */
public class ViewSegmentFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public ViewSegmentFragmentTest(){
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        final Activity activityOnTest = getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activityOnTest.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);;
            }
        };

        activityOnTest.runOnUiThread(wakeUpDevice);

        WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = true;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }
        SegmentController segmentController =
                SegmentController.getInstance(getActivity().getBaseContext());

        if(segmentController.isSegmentDatabaseIsEmpty()) {
            onView(withId(R.id.button)).perform(click());
        }

        //Redirecting to ViewSegmentFragment
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    public void tearDown() throws Exception {
        goBackN();

        super.tearDown();
    }

    private void goBackN() {
        final int N = 50; // how many times to hit back button
        try {
            for (int i = 0; i < N; i++)
                Espresso.pressBack();
        } catch (Exception e) {
        }
    }

    public void testProposalIsDisplayed(){

        onView(withId(R.id.textViewProposal)).check(matches(isDisplayed()));
    }

    public void testTitleBillIsDisplayed(){

        onView(withId(R.id.titleBill)).check(matches(isDisplayed()));
    }

    public void testContentSegmentIsDisplayed(){

        onView(withId(R.id.contentSegment)).check(matches(isDisplayed()));
    }
}