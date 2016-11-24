package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;


/**
 * Created by marcelo on 10/13/16.
 */
public class LoginControllerTest {

    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testConfirmLoginWithValidInputLogin() {
        LoginController loginController = LoginController.getInstance(context);

        final String email = "augusto.vilarins@gmail.com";
        final String password = "12345678";

        String feedback = loginController.confirmLogin(email, password);

        assertEquals(feedback, "SUCESS");
    }

    @Test
    public void testConfirmLoginWithInvalidInputLogin() {
        LoginController loginController = LoginController.getInstance(context);

        final String email = "augusto.vilarins@gmail.com";
        final String password = "12342678";

        String feedback = loginController.confirmLogin(email, password);

        assertEquals(feedback, "FAIL");
    }

    @Test
    public void testConfirmLoginWithEmptyEmail() {
        LoginController loginController = LoginController.getInstance(context);

        final String email = "";
        final String password = "12342678";

        String feedback = loginController.confirmLogin(email, password);

        assertEquals(feedback, "Inválido, o email "
                + "não pode ser vazio.");
    }

    @Test
    public void testConfirmLoginWithEmailHigherThan150Characters() {
        LoginController loginController = LoginController.getInstance(context);

        final String email = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com";
        final String password = "12342678";

        String feedback = loginController.confirmLogin(email, password);

        assertEquals(feedback, "Inválido, o "
                + "email deve ter no máximo 150 caractéres");
    }

    @Test
    public void testConfirmLoginWithInvalidEmail() {
        LoginController loginController = LoginController.getInstance(context);

        final String email = "augustomorenovilarinsasdf";
        final String password = "12342678";

        String feedback = loginController.confirmLogin(email, password);

        assertEquals(feedback, "Ops, esse e-mail é inválido.");
    }

    @Test
    public void testConfirmLoginWithEmptyPassword() {
        LoginController loginController = LoginController.getInstance(context);

        final String email = "augusto.vilarins@gmail.com";
        final String password = "";

        String feedback = loginController.confirmLogin(email, password);

        assertEquals(feedback, "Inválido, a senha não "
                + "pode ser vazia.");
    }

    @Test
    public void testConfirmLoginWithPasswordLesserMinLenght() {
        LoginController loginController = LoginController.getInstance(context);

        final String email = "augusto.vilarins@gmail.com";
        final String password = "12345";

        String feedback = loginController.confirmLogin(email, password);

        assertEquals(feedback, "Inválido, a senha"
                + " deve conter no mínimo 6 caractéres.");
    }

    @Test
    public void testConfirmLoginWithPasswordOverMaxLenght() {
        LoginController loginController = LoginController.getInstance(context);

        final String email = "augusto.vilarins@gmail.com";
        final String password = "12345678910";

        String feedback = loginController.confirmLogin(email, password);

        assertEquals(feedback, "Inválido, a senha"
                + " deve ter no máximo 10 caractéres");
    }

    @Test
    public void testCreateLoginSession() {

        final int id = 1000;
        final String email = "augusto.vilarins@gmail.com";
        final String token = "abcd";
        final String firstName = "Augusto";
        final String lastName = "Vilarins";

        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        LoginController loginController = LoginController.getInstance(context);
        loginController.createLoginSession(id, email,token, firstName, lastName, session);

        assertEquals(session.getInt("id", 0), id);
        assertTrue(session.getBoolean("IsLoggedIn", false));
        assertEquals(session.getString("email", null), email);
        assertEquals(session.getString("token", null), token);
        assertEquals(session.getString("firstName", null), firstName);
        assertEquals(session.getString("lastName", null), lastName);
    }

    @Test
    public void testCreateSessionIsNotLogged() {
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        LoginController loginController = LoginController.getInstance(context);
        loginController.createSessionIsNotLogged(session);

        assertFalse(session.getBoolean("IsLoggedIn", false));
    }

}
