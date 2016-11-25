package gppmds.wikilegis.view;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.WindowManager;

import org.hamcrest.Matcher;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SegmentController;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
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
    //FIXME
    /*
    public void testByClickASegmentThatShouldNotBeClickable() throws InterruptedException {
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.main_content)).perform(swipeLeft());
        Thread.sleep(2000);
        onView(withId(R.id.recycler_view_closed))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.textViewProposal)).check(matches(isDisplayed()));
    }

    public void testByClickASegmentThatShouldBeClickable() throws InterruptedException {
        SegmentController segmentController =
                SegmentController.getInstance(getInstrumentation().getContext());

        if (segmentController.isSegmentDatabaseIsEmpty()) {
            onView(withId(R.id.button)).perform(click());
        }

        //Redirecting to ViewSegmentFragment
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.imageViewLike)).check(matches(isDisplayed()));

    }*/
    //FIXME: Fazer esse teste passar!

    @Rule
    public IntentsTestRule<LoginActivity> mActivityRule = new IntentsTestRule<>(LoginActivity.class,
            true, false);

    public static Matcher<Intent> chooser(Matcher<Intent> matcher) {
        return allOf(hasAction(Intent.ACTION_CHOOSER), hasExtra(is(Intent.EXTRA_INTENT), matcher));
    }

    /*public void testShareIntent () throws InterruptedException {
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.action_share_deslogged)).perform(click());
        Thread.sleep(400);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "this is my auth token");
        mActivityRule.launchActivity(intent);

        intended(chooser(allOf(
                hasAction(Intent.ACTION_SEND),
                hasExtra(Intent.EXTRA_TEXT, "Expected url")
        )));
    }*/

}
