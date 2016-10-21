package gppmds.wikilegis.view;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by josue on 10/20/16.
 */
public class CreateSuggestProposalTest extends ActivityInstrumentationTestCase2<LoadingActivity> {

    Activity activityOnTest;

    public CreateSuggestProposalTest() {
        super(LoadingActivity.class);

    }
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
        activityOnTest.runOnUiThread(wakeUpDevice);
    }

    public void testSuggestProposalLoggedOut(){

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(isLoggedIn){
            onView(withId(R.id.action_profile)).perform(click());
            onView(withText("Sair")).perform(click());
        }

        closeSoftKeyboard();
        onView(withText("Visitante")).perform(click());
        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewSegment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.floatingButton))
                .perform((click()));
        onView(withId(R.id.emailLoginField)).check(matches(isDisplayed()));

    }

    public void testEmptyProposalSuggestion(){

        closeSoftKeyboard();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(!isLoggedIn){
            onView(withId(R.id.emailLoginField)).perform(typeText("cizabelacristina@gmail.com"));
            closeSoftKeyboard();
            onView(withId(R.id.passwordLoginField)).perform(typeText("iza3bel"));
            closeSoftKeyboard();
            onView(withId(R.id.loginButton)).perform(click());
        }

        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewSegment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.floatingButton))
                .perform((click()));
        onView(withId(R.id.saveSuggestion)).perform(click());
        onView(withId(R.id.suggestionEditText)).check(matches(hasErrorText(getActivity()
                .getApplicationContext().getResources().getString(R.string.empty_segment))));

    }

    public void testCancelEmptyProposalSuggestion(){

        closeSoftKeyboard();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(!isLoggedIn){
            onView(withId(R.id.emailLoginField)).perform(typeText("cizabelacristina@gmail.com"));
            closeSoftKeyboard();
            onView(withId(R.id.passwordLoginField)).perform(typeText("iza3bel"));
            closeSoftKeyboard();
            onView(withId(R.id.loginButton)).perform(click());
        }

        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewSegment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.floatingButton)).perform((click()));
        Espresso.pressBack();
        onView(withId(R.id.floatingButton)).check(matches(isDisplayed()));

    }

    public void testCancelProposalSuggestion(){

        closeSoftKeyboard();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(!isLoggedIn){
            onView(withId(R.id.emailLoginField)).perform(typeText("cizabelacristina@gmail.com"));
            closeSoftKeyboard();
            onView(withId(R.id.passwordLoginField)).perform(typeText("iza3bel"));
            closeSoftKeyboard();
            onView(withId(R.id.loginButton)).perform(click());
        }

        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewSegment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.floatingButton)).perform((click()));
        onView(withId(R.id.suggestionEditText)).perform(typeText("Testando"));
        closeSoftKeyboard();
        Espresso.pressBack();
        onView(withText("Sim")).perform(click());
        onView(withId(R.id.floatingButton)).check(matches(isDisplayed()));
    }

    public void testKeepProposalSuggestion(){

        closeSoftKeyboard();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(!isLoggedIn){
            onView(withId(R.id.emailLoginField)).perform(typeText("cizabelacristina@gmail.com"));
            closeSoftKeyboard();
            onView(withId(R.id.passwordLoginField)).perform(typeText("iza3bel"));
            closeSoftKeyboard();
            onView(withId(R.id.loginButton)).perform(click());
        }

        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewSegment))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.floatingButton)).perform((click()));
        onView(withId(R.id.suggestionEditText)).perform(typeText("Testando"));
        closeSoftKeyboard();
        Espresso.pressBack();
        onView(withText("Não")).perform(click());
        onView(withId(R.id.suggestionEditText)).check(matches(withText("Testando")));
    }
}
