package gppmds.wikilegis.view;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.test.ActivityInstrumentationTestCase2;

import android.view.WindowManager;

import org.json.JSONException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.LoginController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.VotesController;
import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.VotesException;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class ViewSegmentFragmentTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    Activity activityOnTest;
    Context context;

    public ViewSegmentFragmentTest(){
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        final Activity activityOnTest = getActivity();
        context = getInstrumentation().getContext();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activityOnTest.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);;
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
        SegmentController segmentController =
                SegmentController.getInstance(getActivity().getBaseContext());

        if(segmentController.isSegmentDatabaseIsEmpty()) {
            onView(withId(R.id.button)).perform(click());
        }

        //Redirecting to ViewSegmentFragment
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.recycler_view_open))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recycler_viewBill))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    public void tearDown() throws Exception {
        goBackN();

        super.tearDown();
    }

    private void goBackN() {
        final int N = 50; // how many times to hit back button
        try {
            for (int i = 0; i < N; i++)
                Espresso.pressBack();
        } catch (Exception e) {
        }
    }

    public void testProposalIsDisplayed(){

        onView(withId(R.id.textViewProposal)).check(matches(isDisplayed()));
    }

    public void testTitleBillIsDisplayed(){

        onView(withId(R.id.titleBill)).check(matches(isDisplayed()));
    }

    public void testContentSegmentIsDisplayed(){

        onView(withId(R.id.contentSegment)).check(matches(isDisplayed()));
    }

    public void testNumberOfLikeIsDisplayed(){

        onView(withId(R.id.textViewNumberLike)).check(matches(isDisplayed()));
    }

    public void testImageLikeIsDisplayed(){

        onView(withId(R.id.imageViewLike)).check(matches(isDisplayed()));
    }

    public void testNumberOfDislikeIsDisplayed(){

        onView(withId(R.id.textViewNumberLike)).check(matches(isDisplayed()));
    }

    public void testImageDislikeIsDisplayed(){

        onView(withId(R.id.imageViewDislike)).check(matches(isDisplayed()));
    }
    public void testLikeButton(){
        closeSoftKeyboard();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (getInstrumentation().getContext()).getBoolean("IsLoggedIn", false);


        final Integer idUser = 118;

        if(!isLoggedIn){
            final String email = "ab@gmail.com";
            final String token = "a9a22051724e71356331306a0b3c5b2184e58492";
            final String firstName = "arbo";
            final String lastName = "rigen";

            SharedPreferences session = PreferenceManager.
                    getDefaultSharedPreferences(context);

            LoginController loginController = LoginController.getInstance(context);
            loginController.createLoginSession(idUser, email,token, firstName, lastName, session);
        }

        final Integer idSegment = 30;

        VotesController votesController = VotesController.getInstance(context);

        boolean evaluatedTrue = votesController.getVoteByUserAndIdSegment(idUser, idSegment, true);
        boolean evaluatedFalse = votesController.getVoteByUserAndIdSegment(idUser, idSegment, false);

        if(evaluatedTrue || evaluatedFalse) {
            try {
                votesController.deleteVote(idSegment, idUser);
            } catch (VotesException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (BillException e) {
                e.printStackTrace();
            }
        }

        onView(withId(R.id.imageViewLike))
                .perform((click()));
        onView(withText("like")).inRoot(withDecorView(not(is(getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
    public void testDesLikeButton(){

        closeSoftKeyboard();

        Boolean isLoggedIn = PreferenceManager.getDefaultSharedPreferences
                (context).getBoolean("IsLoggedIn", false);

        final Integer idUser = 118;

        if(!isLoggedIn){
            final String email = "ab@gmail.com";
            final String token = "a9a22051724e71356331306a0b3c5b2184e58492";
            final String firstName = "arbo";
            final String lastName = "rigen";

            SharedPreferences session = PreferenceManager.
                    getDefaultSharedPreferences(context);

            LoginController loginController = LoginController.getInstance(context);
            loginController.createLoginSession(idUser, email, token, firstName, lastName, session);
        }

        final Integer idSegment = 30;

        VotesController votesController = VotesController.getInstance(context);

        boolean evaluatedTrue = votesController.getVoteByUserAndIdSegment(idUser, idSegment, true);
        boolean evaluatedFalse = votesController.getVoteByUserAndIdSegment(idUser, idSegment, false);

        if(evaluatedTrue || evaluatedFalse) {
            try {
                votesController.deleteVote(idSegment, idUser);
            } catch (VotesException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (BillException e) {
                e.printStackTrace();
            }
        }

        onView(withId(R.id.imageViewDislike))
                .perform((click()));
        onView(withText("deslike")).inRoot(withDecorView(not(is(getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

}