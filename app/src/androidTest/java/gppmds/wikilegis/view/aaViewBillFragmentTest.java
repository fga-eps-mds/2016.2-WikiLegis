package gppmds.wikilegis.view;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.test.ActivityInstrumentationTestCase2;

import android.view.WindowManager;

import org.hamcrest.Matcher;

import org.junit.Test;
import org.junit.Rule;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;

import static org.hamcrest.Matchers.allOf;
public class aaViewBillFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    Activity activityOnTest;


    public aaViewBillFragmentTest(){
        super(LoginActivity.class);
    }

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
        activityOnTest.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void testActiveNotificationViewMessageIsDisplayed(){
        closeSoftKeyboard();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(!isLoggedIn) {
            onView(withId(R.id.emailLoginField)).perform(typeText("cizabelacristina@gmail.com"));
            closeSoftKeyboard();
            onView(withId(R.id.passwordLoginField)).perform(typeText("iza3bel"));
            closeSoftKeyboard();
            onView(withId(R.id.loginButton)).perform(click());
        }

        onView(withId(R.id.action_profile_logged)).perform(click());
        onView(withText("Ativar Notificação")).perform(click());
        onView(withText("diariamente")).perform(click());
        onView(withText("Confirmar")).perform(click());
        onView(withText("Você receberá informações deste projeto.")).inRoot(withDecorView(not(is(getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));

    }


}
