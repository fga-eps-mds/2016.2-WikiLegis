package gppmds.wikilegis.view;

import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import gppmds.wikilegis.R;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class RegisterUserFragmentTest extends ActivityInstrumentationTestCase2<RegisterUserActivity>{

    public RegisterUserFragmentTest(){
       super(RegisterUserActivity.class);
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

    public void testErrorWithEmptyFirstName(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText(""));
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.firstNameField)).check(matches(hasErrorText("Inválido, o nome não pode ser vazio.")));
    }

    public void testErrorWithNumbersFirstName(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaa125"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.firstNameField)).check(matches(hasErrorText("O nome deve ter apenas letras.")));
    }

    public void testErrorWithNumbersLastName(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaa125"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.lastNameField)).check(matches(hasErrorText("O sobrenome deve ter apenas letras.")));
    }

    public void testErrorWithEmptyEmail(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText(""));
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.emailField)).check(matches(hasErrorText("Inválido, o email não pode ser vazio.")));
    }

    public void testErrorWithOverMaxLengthEmail(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.emailField)).check(matches(hasErrorText("Inválido, o email deve ter no máximo 150 caractéres")));
    }

    public void testErrorWithEmptyPassword(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordField)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.passwordField)).check(matches(hasErrorText("Inválido, a senha não pode ser vazia.")));
    }

    public void testErrorWithLesserMinLengthPassword(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordField)).perform(typeText("1234"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.passwordField)).check(matches(hasErrorText("Inválido, a senha deve conter no mínimo 6 caractéres.")));
    }

    public void testErrorWithOverMaxLengthPassword(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordField)).perform(typeText("1234567891011"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.passwordField)).check(matches(hasErrorText("Inválido, a senha deve ter no máximo 10 caractéres")));
    }

    public void testErrorWithConfirmPasswordDifferentPassword(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("aaaaa@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordField)).perform(typeText("123456789"));
        closeSoftKeyboard();
        onView(withId(R.id.passwordConfirmationField)).perform(typeText("12345678"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.passwordConfirmationField)).check(matches(hasErrorText("As senhas digitadas sao diferentes")));
    }

    public void testErrorWithOverMaxLengthFirstName(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());

        onView((withId(R.id.firstNameField))).check(matches(hasErrorText("Inválido, o nome deve ter no máximo 30 caractéres")));
    }

    public void testErrorWithEmptyLastName(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaaaaaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());
        onView((withId(R.id.lastNameField))).check(matches(hasErrorText("Inválido, o sobrenome não pode ser vazio.")));
    }

    public void testErrorWithOverMaxLengthLastName(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("aaaaaaaaaaa"));
        onView(withId(R.id.lastNameField)).perform(typeText("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());
        onView((withId(R.id.lastNameField))).check(matches(hasErrorText("Inválido, o sobrenome deve ter no máximo 30 caractéres")));
    }

    public void testErrorWithInvalidEmail(){
        onView(withId(R.id.registerText)).perform(click());
        onView(withId(R.id.firstNameField)).perform(typeText("Marcelo"));
        onView(withId(R.id.lastNameField)).perform(typeText("Augusto"));
        closeSoftKeyboard();
        onView(withId(R.id.emailField)).perform(typeText("mekmay@hotmailcom"));
        closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());
        onView((withId(R.id.emailField))).check(matches(hasErrorText("Ops, esse e-mail é inválido.")));
    }


}
