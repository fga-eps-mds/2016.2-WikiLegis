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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class LoginFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    public LoginFragmentTest() {
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
    }

    public void testLogoIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.imageViewLogo)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    public void testEmailLoginFieldIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).check(matches(isDisplayed()));
    }

    public void testPasswordLoginFieldIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).check(matches(isDisplayed()));
    }

    public void testNotHaveCadastreIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.editText)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    public void testTextLoginAsVisitantIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.textView2)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    public void testIconUserIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.imageViewUserName)).check(matches(isDisplayed()));
    }

    public void testIconPasswordIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.imageViewPasswordUser)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    public void testButtonRegisterTextIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    public void testButtonLoginAsVisitorTextIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.loginAsVisitorText)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }
}
