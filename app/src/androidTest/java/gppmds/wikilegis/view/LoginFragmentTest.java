package gppmds.wikilegis.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.view.WindowManager;

import org.junit.Before;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.LoginController;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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

    public void testEmptyEmailMessageIsDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.emailLoginField)).perform(ViewActions.scrollTo()).check(matches(hasErrorText("Inválido, o email "
                + "não pode ser vazio.")));
    }

    public void testEmailHigherThan150MessageIsDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.emailLoginField)).perform(ViewActions.scrollTo()).check(matches(hasErrorText("Inválido, o "
                + "email deve ter no máximo 150 caractéres")));
    }

    public void testInvalidEmailMessageIsDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augustomorenovilarinsasdf"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.emailLoginField)).perform(ViewActions.scrollTo()).check(matches(hasErrorText("Ops, esse e-mail é inválido.")));
    }

    public void testEmptyPasswordMessageIsDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.passwordLoginField)).perform(ViewActions.scrollTo()).check(matches(hasErrorText("Inválido, a senha não "
                + "pode ser vazia.")));
    }

    public void testErrorWithLesserMinLengthPasswordMessageIsDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).perform(typeText("12345"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.passwordLoginField)).perform(ViewActions.scrollTo()).check(matches(hasErrorText("Inválido, a senha"
                + " deve conter no mínimo 6 caractéres.")));
    }

    public void testErrorWithOverMaxLengthPasswordMessageIsDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).perform(typeText("12345678910"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions    .scrollTo()).perform(click());
        onView(withId(R.id.passwordLoginField)).perform(ViewActions.scrollTo()).check(matches(hasErrorText("Inválido, a senha"
                + " deve ter no máximo 10 caractéres")));
    }

    public void testInvalidInputToastIsDisplayed() {
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).perform(typeText("abcdefg"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withText("Usuário ou senha inválidos!")).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    public void testValidInput() {
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.spinner_open)).check(matches(withText("Relevantes")));
    }

    public void testAboutTextIsDisplayed(){
        closeSoftKeyboard();
        onView(withId(R.id.aboutApp)).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    public void testWithOnlyWifiIfSelectedInActionBarWithUserLogged() {
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.action_profile_logged)).perform(click());
        onView(withText("Configurações de download")).perform(click());
        onView(withText("Apenas wifi")).perform(click());
        onView(withText("Confirmar")).perform(click());

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity().getBaseContext());

        assertTrue(session.getInt("NetworkSettings", -1) == 0);
    }

    public void testWithWifiAndDataMobileIfSelectedInActionBarWithUserLogged() {
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.action_profile_logged)).perform(click());
        onView(withText("Configurações de download")).perform(click());
        onView(withText("Wifi e dados")).perform(click());
        onView(withText("Confirmar")).perform(click());

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity().getBaseContext());

        assertTrue(session.getInt("NetworkSettings", -1) == 1);
    }

    public void testWithNeverDownloadMobileIfSelectedInActionBarWithUserLogged() {
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.action_profile_logged)).perform(click());
        onView(withText("Configurações de download")).perform(click());
        onView(withText("Nunca")).perform(click());
        onView(withText("Confirmar")).perform(click());

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity().getBaseContext());

        assertTrue(session.getInt("NetworkSettings", -1) == 2);
    }

    public void testWithOnlyWifiIfSelectedInActionBarWithUserDislogged() {
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.action_profile_deslogged)).perform(click());

        onView(withText("Configurações de download")).perform(click());
        onView(withText("Apenas wifi")).perform(click());
        onView(withText("Confirmar")).perform(click());

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity().getBaseContext());

        assertTrue(session.getInt("NetworkSettings", -1) == 0);
    }

    public void testWithWifiAndMobileDataIfSelectedInActionBarWithUserDislogged() {
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.action_profile_deslogged)).perform(click());

        onView(withText("Configurações de download")).perform(click());
        onView(withText("Wifi e dados")).perform(click());
        onView(withText("Confirmar")).perform(click());

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity().getBaseContext());

        assertTrue(session.getInt("NetworkSettings", -1) == 1);
    }

    public void testWithNeverDownloadIfSelectedInActionBarWithUserDislogged() {
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.action_profile_deslogged)).perform(click());

        onView(withText("Configurações de download")).perform(click());
        onView(withText("Nunca")).perform(click());
        onView(withText("Confirmar")).perform(click());

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getActivity().getBaseContext());

        assertTrue(session.getInt("NetworkSettings", -1) == 2);
    }

    public void testOptionBarWithVisitor() {
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).perform(click());
        onView(withId(R.id.action_profile_deslogged)).perform(click());
        onView(withText("Entrar")).perform(click());
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
    }

    public void testOptionBarWithUserLogged() {
        closeSoftKeyboard();
        onView(withId(R.id.emailLoginField)).perform(typeText("augusto.vilarins@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordLoginField)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(ViewActions.scrollTo()).perform(click());

        onView(withId(R.id.action_profile_logged)).perform(click());
        onView(withText("Sair")).perform(click());
        closeSoftKeyboard();
        onView(withText("Visitante")).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(InstrumentationRegistry.getContext());

        LoginController loginController =
                LoginController.getInstance(InstrumentationRegistry.getContext());
        loginController.createSessionIsNotLogged(session);

        assertFalse(session.getBoolean("IsLoggedIn", false));
    }

}
