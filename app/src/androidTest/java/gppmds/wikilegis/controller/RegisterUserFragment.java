package gppmds.wikilegis.controller;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;

import gppmds.wikilegis.R;
import gppmds.wikilegis.view.RegisterUserActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by freemanpivo on 8/28/16.
 */
public class RegisterUserFragment extends ActivityInstrumentationTestCase2<RegisterUserActivity>{

    public RegisterUserFragment(){
       super(RegisterUserActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testErrorWithEmptyName(){
        onView(withId(R.id.firstNameField)).perform(typeText(""));
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.firstNameField)).check(matches(hasErrorText("Inválido, o nome não pode ser vazio.")));
    }

}
