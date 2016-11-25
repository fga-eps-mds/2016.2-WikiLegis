package gppmds.wikilegis.view;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.core.deps.guava.base.Throwables;
import android.support.test.espresso.core.deps.guava.collect.Sets;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.WindowManager;

import org.junit.Before;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by izabela on 06/10/16.
 */
public class OpenBillListFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity>{

    public OpenBillListFragmentTest() {
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
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
    }

    /*
    public void testDefaultFilteringOption(){
        onView(withId(R.id.spinner_open)).check(matches(withText("Relevantes")));
    }
    */

    public void testChangFilteringOptionToRecent(){
        onView(withId(R.id.spinner_open)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Recentes"))).perform(click());
        onView(withId(R.id.spinner_open)).check(matches(withText("Recentes")));
    }

    public void testChangFilteringOptionToRelevant() throws InterruptedException{
        onView(withId(R.id.spinner_open)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Recentes"))).perform(click());
        onView(withId(R.id.spinner_open)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Relevantes"))).perform(click());
        onView(withId(R.id.spinner_open)).check(matches(withText("Relevantes")));
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
