package gppmds.wikilegis.view;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Before;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
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

    public void testRedirectionByClickingRegister() {
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.imageViewLogo)).check(matches(isDisplayed()));
    }

    public void testLogoIsDisplayed() {
        onView(withId(R.id.imageViewLogo)).check(matches(isDisplayed()));
    }

    public void testEmailLoginFieldIsDisplayed() {
        onView(withId(R.id.emailLoginField)).check(matches(isDisplayed()));
    }

    public void testPasswordLoginFieldIsDisplayed() {
        onView(withId(R.id.passwordLoginField)).check(matches(isDisplayed()));
    }

    public void testButtonLoginIsDisplayed() {
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    public void testNotHaveCadastreIsDisplayed() {
        onView(withId(R.id.editText)).check(matches(isDisplayed()));
    }

    public void testTextLoginAsVisitantIsDisplayed() {
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
    }

    public void testIconUserIsDisplayed() {
        onView(withId(R.id.imageViewUserName)).check(matches(isDisplayed()));
    }

    public void testIconPasswordIsDisplayed() {
        onView(withId(R.id.imageViewPasswordUser)).check(matches(isDisplayed()));
    }

    public void testButtonRegisterTextIsDisplayed() {
        onView(withId(R.id.registerText)).check(matches(isDisplayed()));
    }

    public void testButtonLoginAsVisitorTextIsDisplayed() {
        onView(withId(R.id.loginAsVisitorText)).check(matches(isDisplayed()));
    }
}
