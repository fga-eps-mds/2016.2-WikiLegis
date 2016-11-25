package gppmds.wikilegis.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Test;

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

public class ReportActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    Activity activityOnTest;


    public ReportActivityTest(){
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
    public void testReportErroIsDisplayed(){
        closeSoftKeyboard();

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        closeSoftKeyboard();
        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }
        onView(withId(R.id.loginAsVisitorText)).perform(click());

        onView(withId(R.id.action_profile_deslogged)).perform(click());
        onView(withText("Relatar Erro")).perform(click());
    }

    @Test
    public void testTextErroDisplayed(){
        closeSoftKeyboard();

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        closeSoftKeyboard();
        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }
        onView(withId(R.id.loginAsVisitorText)).perform(click());

        onView(withId(R.id.action_profile_deslogged)).perform(click());
        onView(withText("Relatar Erro")).perform(click());
        onView(withId(R.id.textViewErro)).check(matches(isDisplayed()));
    }

    public void testEditTextErroDisplayed(){
        closeSoftKeyboard();

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        closeSoftKeyboard();
        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }
        onView(withId(R.id.loginAsVisitorText)).perform(click());

        onView(withId(R.id.action_profile_deslogged)).perform(click());
        onView(withText("Relatar Erro")).perform(click());
        onView(withId(R.id.editTextSubject)).check(matches(isDisplayed()));
    }

    @Test
    public void testTextMessageDisplayed(){
        closeSoftKeyboard();

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        closeSoftKeyboard();
        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }
        onView(withId(R.id.loginAsVisitorText)).perform(click());

        onView(withId(R.id.action_profile_deslogged)).perform(click());
        onView(withText("Relatar Erro")).perform(click());
        onView(withId(R.id.textViewMessage)).check(matches(isDisplayed()));
    }

    public void testEditTextMessageDisplayed(){
        closeSoftKeyboard();

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        closeSoftKeyboard();
        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }
        onView(withId(R.id.loginAsVisitorText)).perform(click());

        onView(withId(R.id.action_profile_deslogged)).perform(click());
        onView(withText("Relatar Erro")).perform(click());
        onView(withId(R.id.editTextMessage)).check(matches(isDisplayed()));
    }

    public void testButtonSendMessageDisplayed(){
        closeSoftKeyboard();

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        closeSoftKeyboard();
        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }
        onView(withId(R.id.loginAsVisitorText)).perform(click());

        onView(withId(R.id.action_profile_deslogged)).perform(click());
        onView(withText("Relatar Erro")).perform(click());
        onView(withId(R.id.buttonSend)).check(matches(isDisplayed()));
    }

    public void testSendReportMessage() throws InterruptedException {
        closeSoftKeyboard();

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity());

        closeSoftKeyboard();
        if (session.getBoolean("IsLoggedIn", false)){
            onView(withId(R.id.action_profile_logged)).perform(click());
            onView(withText("Sair")).perform(click());
        }
        onView(withId(R.id.loginAsVisitorText)).perform(click());

        onView(withId(R.id.action_profile_deslogged)).perform(click());
        onView(withText("Relatar Erro")).perform(click());
        onView(withId(R.id.editTextSubject)).perform(typeText("ERRO TESTE"));
        onView(withId(R.id.editTextMessage)).perform(typeText("MENSAGEM TESTE"));
        onView(withId(R.id.buttonSend)).perform(click());
        Thread.sleep(2000);
        onView(withText("Reportado")).inRoot(withDecorView(not(is(getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));


    }
}
