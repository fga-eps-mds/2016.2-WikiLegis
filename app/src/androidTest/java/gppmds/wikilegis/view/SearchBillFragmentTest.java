package gppmds.wikilegis.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;

import org.junit.Before;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by marcelo on 11/4/16.
 */
public class SearchBillFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {


    private Activity activityOnTest;

    public SearchBillFragmentTest(){super(LoginActivity.class);}

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activityOnTest = getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activityOnTest.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
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

        activityOnTest.runOnUiThread(wakeUpDevice);
    }

    public void testResultSearchExistWithInternet() {
        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(isLoggedIn){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }

        closeSoftKeyboard();

        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.action_search)).perform(click());

        onView(isAssignableFrom(EditText.class)).perform(typeText("Gr"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        closeSoftKeyboard();

        onView(withId(R.id.recycler_view_search))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.textViewTitleBill)).check(matches(isDisplayed()));
    }

    public void testResultSearchNotExistWithInternet() {
        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(isLoggedIn){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }

        closeSoftKeyboard();

        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.action_search)).perform(click());

        onView(isAssignableFrom(EditText.class)).perform(typeText("Gremio"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        closeSoftKeyboard();

        onView(withText("Nenhum resultado encontrado!")).inRoot(withDecorView(not(is(getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    private void disabledInternet() {
        final ConnectivityManager conman =
                (ConnectivityManager) getActivity().getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass;
        try {
            conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        WifiManager wifiManager = (WifiManager)getActivity().getBaseContext().getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = false;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testResultSearchExistWithoutInternet() {
        disabledInternet();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(isLoggedIn){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }

        closeSoftKeyboard();

        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.action_search)).perform(click());

        onView(isAssignableFrom(EditText.class)).perform(typeText("Gr"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        closeSoftKeyboard();

        onView(withId(R.id.recycler_view_search))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.textViewTitleBill)).check(matches(isDisplayed()));
    }

    public void testResultSearchNotExistWithoutInternet() {
        disabledInternet();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(isLoggedIn){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }

        closeSoftKeyboard();

        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.action_search)).perform(click());

        onView(isAssignableFrom(EditText.class)).perform(typeText("Gremio"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        closeSoftKeyboard();

        onView(withText("Nenhum resultado encontrado!")).inRoot(withDecorView(not(is(getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

}
