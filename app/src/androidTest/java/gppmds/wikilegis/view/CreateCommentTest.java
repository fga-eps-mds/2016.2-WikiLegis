package gppmds.wikilegis.view;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CreateCommentTest extends ActivityInstrumentationTestCase2<LoadingActivity> {
    Activity activityOnTest;

    public CreateCommentTest() {
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

    @After
    public void tearDown() throws Exception {
        goBackNFragments();
        super.tearDown();
    }

    private void goBackNFragments() {
        final int N = 20; // how many times to hit back button
        try {
            for (int i = 0; i < N; i++)
                Espresso.pressBack();
        } catch (Exception e) {
        }
    }

    public void testCommentSegmentLoggedOut () {

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (activityOnTest.getBaseContext()).getBoolean("IsLoggedIn", false);

        if(isLoggedIn){
            onView(withId(R.id.action_profile_logged)).perform(click());
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

    public void testEmptyComment(){

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

        // FIXME: Clicar no imageViewProposalCard da posiçao 0 do adapter view
        onData(withId(R.id.imageViewProposalCard)).inAdapterView(withId(R.id.frameCardViewSegment))
                .atPosition(0).perform(click());
        onView(withId(R.id.saveComment))
                .perform(click());
        onView(withId(R.id.suggestionProposalEditText)).check(matches(hasErrorText(getActivity()
                .getApplicationContext().getResources().getString(R.string.empty_comment))));

    }

    public void testUserWriteValidComment () throws InterruptedException {
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

        // FIXME: Clicar no imageViewProposalCard da posiçao 0 do adapter view
        onData(withId(R.id.imageViewProposalCard)).inAdapterView(withId(R.id.frameCardViewSegment))
                .atPosition(0).perform(click());
        onView(withId(R.id.commentEditText))
                .perform(typeText("Não gostei da proposta! Seu Madruga, pra Presidente!"));
        Thread.sleep(400);
        onView(withText("Obrigado pelo comentário!")).inRoot(withDecorView(not(is(getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
