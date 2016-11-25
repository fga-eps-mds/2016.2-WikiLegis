package gppmds.wikilegis.view;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.core.deps.guava.base.Throwables;
import android.support.test.espresso.core.deps.guava.collect.Sets;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Before;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
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

public class RegisterUserFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity>{

    public RegisterUserFragmentTest(){super(LoginActivity.class);
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

        WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);

        final boolean STATUS = true;

        wifiManager.setWifiEnabled(STATUS);

        try {
            Thread.sleep(400);
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

    public void testIButtonRegisterIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.registerButton)).check(matches(isDisplayed()));
    }

    public void testIconConfirmationPasswordIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.imageViewConfirmationPasswotd)).check(matches(isDisplayed()));
    }

    public void testIconEmailIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.imageViewEmailUser)).check(matches(isDisplayed()));
    }

    public void testIconLastNameIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.imageViewLastName)).check(matches(isDisplayed()));
    }

    public void testLogoIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.imageViewLogo)).check(matches(isDisplayed()));
    }

    public void testIconUserNameIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.imageViewUserName)).check(matches(isDisplayed()));
    }

    public void testIconPasswordUserIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.imageViewPasswordUser)).check(matches(isDisplayed()));
    }

    public void testErrorWithEmptyFirstName(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText(""));
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.firstNameField)).check(matches(hasErrorText("Inválido, o nome não pode ser vazio.")));
    }

    public void testErrorWithNumbersFirstName(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaa125"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).check(matches(hasErrorText("O nome deve ter apenas letras.")));
    }

    public void testErrorWithNumbersLastName(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaa125"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.lastNameField)).check(matches(hasErrorText("O sobrenome deve ter apenas letras.")));
    }

    public void testErrorWithEmptyEmail(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText(""));
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.emailField)).check(matches(hasErrorText("Inválido, o email não pode ser vazio.")));
    }

    public void testErrorWithOverMaxLengthEmail(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.emailField)).check(matches(hasErrorText("Inválido, o email deve ter no máximo 150 caractéres")));
    }

    public void testErrorWithEmptyPassword(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordField)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.passwordField)).check(matches(hasErrorText("Inválido, a senha não pode ser vazia.")));
    }

    public void testErrorWithLesserMinLengthPassword(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordField)).perform(typeText("1234"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.passwordField)).check(matches(hasErrorText("Inválido, a senha deve conter no mínimo 6 caractéres.")));
    }

    public void testErrorWithOverMaxLengthPassword(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordField)).perform(typeText("1234567891011"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.passwordField)).check(matches(hasErrorText("Inválido, a senha deve ter no máximo 10 caractéres")));
    }

    public void testErrorWithConfirmPasswordDifferentPassword(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordField)).perform(typeText("123456789"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordConfirmationField)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.passwordConfirmationField)).check(matches(hasErrorText("As senhas digitadas sao diferentes")));
    }

    public void testErrorWithOverMaxLengthFirstName(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());

        onView((withId(R.id.firstNameField))).check(matches(hasErrorText("Inválido, o nome deve ter no máximo 30 caractéres")));
    }

    public void testErrorWithEmptyLastName(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaaaaaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());
        onView((withId(R.id.lastNameField))).check(matches(hasErrorText("Inválido, o sobrenome não pode ser vazio.")));
    }

    public void testErrorWithOverMaxLengthLastName(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaaaaaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());
        onView((withId(R.id.lastNameField))).check(matches(hasErrorText("Inválido, o sobrenome deve ter no máximo 30 caractéres")));
    }

    public void testErrorWithInvalidEmail(){
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("Marcelo"));
        onView(withId(R.id.lastNameField)).perform(typeText("Augusto"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("mekmay@hotmailcom"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());
        onView((withId(R.id.emailField))).check(matches(hasErrorText("Ops, esse e-mail é inválido.")));
    }

    public void testEmailRepeatFields() throws InterruptedException {
        closeSoftKeyboard();
        onView(withId(R.id.registerText)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("Augusto"));
        onView(withId(R.id.lastNameField)).perform(typeText("Moreno"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView((withId(R.id.passwordField))).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordConfirmationField)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(ViewActions.scrollTo()).perform(click());
        Thread.sleep(400);
        onView(withText("Email já cadastrado!")).inRoot(withDecorView(not(is(getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Override
    protected void tearDown() throws Exception {
        closeAllActivities(getInstrumentation());
        super.tearDown();
    }

    public static void closeAllActivities(Instrumentation instrumentation) throws Exception {
        final int NUMBER_OF_RETRIES = 100;
        int i = 0;
        while (closeActivity(instrumentation)) {
            if (i++ > NUMBER_OF_RETRIES) {
                throw new AssertionError("Limit of retries excesses");
            }
            Thread.sleep(200);
        }
    }

    public static <X> X callOnMainSync(Instrumentation instrumentation, final Callable<X> callable) throws Exception {
        final AtomicReference<X> retAtomic = new AtomicReference<>();
        final AtomicReference<Throwable> exceptionAtomic = new AtomicReference<>();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                try {
                    retAtomic.set(callable.call());
                } catch (Throwable e) {
                    exceptionAtomic.set(e);
                }
            }
        });
        final Throwable exception = exceptionAtomic.get();
        if (exception != null) {
            Throwables.propagateIfInstanceOf(exception, Exception.class);
            Throwables.propagate(exception);
        }
        return retAtomic.get();
    }

    public static Set<Activity> getActivitiesInStages(Stage... stages) {
        final Set<Activity> activities = Sets.newHashSet();
        final ActivityLifecycleMonitor instance = ActivityLifecycleMonitorRegistry.getInstance();
        for (Stage stage : stages) {
            final Collection<Activity> activitiesInStage = instance.getActivitiesInStage(stage);
            if (activitiesInStage != null) {
                activities.addAll(activitiesInStage);
            }
        }
        return activities;
    }

    private static boolean closeActivity(Instrumentation instrumentation) throws Exception {
        final Boolean activityClosed = callOnMainSync(instrumentation, new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Set<Activity> activities = getActivitiesInStages(Stage.RESUMED,
                        Stage.STARTED, Stage.PAUSED, Stage.STOPPED, Stage.CREATED);
                activities.removeAll(getActivitiesInStages(Stage.DESTROYED));
                if (activities.size() > 0) {
                    final Activity activity = activities.iterator().next();
                    activity.finish();
                    return true;
                } else {
                    return false;
                }
            }
        });
        if (activityClosed) {
            instrumentation.waitForIdleSync();
        }
        return activityClosed;
    }
}
