package gppmds.wikilegis.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marcelo on 10/1/16.
 */
public class RegisterUserControllerTest {

    Context context;

    @Before
    public void setup() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testRegisterUserWithEmptyFirstName() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("", "Cardoso", "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("Inválido, o nome não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithNullFirstName() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser(null, "Cardoso", "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("Inválido, o nome não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithFirstNameWithOnlySpace() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("      ", "Cardoso", "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("Inválido, o nome não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithMaxLengthFirstName() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "Cardoso", "a@a.com", "123456", "123456");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithMaxMoreOneLengthFirstName() throws JSONException,
            UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "Cardoso", "a@a.com", "123456", "123456");

        assertTrue(message.equals("Inválido, o nome deve ter no máximo 30 caractéres"));
    }

    @Test
    public void testRegisterUserWithMaxMinusOneLengthFirstName() throws JSONException,
            UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "Cardoso", "a@a.com", "123456", "123456");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithMinLenghtFirstName() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("a", "Cardoso", "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithNameWithNumber() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("1asa", "Cardoso", "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("O nome deve ter apenas letras."));
    }


    @Test
    public void testRegisterUserWithNameWithSpecialCharacters() throws JSONException,
            UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("l@sa", "Cardoso", "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("O nome deve ter apenas letras."));
    }

    @Test
    public void testRegisterUserWithEmptySecondName() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "", "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("Inválido, o sobrenome não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithNullSecondName() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", null, "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("Inválido, o sobrenome não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithSecondNameWithOnlySpace() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "         ", "a@a.com",
                "123456", "123456");

        assertTrue(message.equals("Inválido, o sobrenome não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithMaxLengthSecondName() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "aaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaa", "a@a.com", "123456", "123456");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithMaxMoreOneLengthSecondName() throws JSONException,
            UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaa", "a@a.com", "123456", "123456");

        assertTrue(message.equals("Inválido, o sobrenome deve ter no máximo 30 caractéres"));
    }

    @Test
    public void testRegisterUserWithMaxMinusOneLengthSecondName() throws JSONException,
            UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "a@a.com", "123456", "123456");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithMinLenghtSecondName() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", "123456", "123456");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithLastNameWithNumber() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "Nasc1mento" +
                "aaa", "a@a.com", "123456", "123456");

        assertTrue(message.equals("O sobrenome deve ter apenas letras."));
    }

    @Test
    public void testRegisterUserWithLastNameWithSpecialCharacters() throws JSONException,
            UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "N&re" +
                "aaa", "a@a.com", "123456", "123456");

        assertTrue(message.equals("O sobrenome deve ter apenas letras."));
    }

    @Test
    public void testRegisterUserWithMaxLenghtPassword() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", "1234567890", "1234567890");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithMinLenghtPassword() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", "123456", "123456");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithMaxMoreOneLenghtPassword() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", "12345678901", "12345678901");

        assertTrue(message.equals("Inválido, a senha deve ter no máximo 10 caractéres"));
    }

    @Test
    public void testRegisterUserWithMinMinusOneLenghtPassword() throws JSONException,
            UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", "12345", "12345");

        assertTrue(message.equals("Inválido, a senha deve conter no mínimo 6 caractéres."));
    }

    @Test
    public void testRegisterUserWithNullPassword() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", null, "12345");

        assertTrue(message.equals("Inválido, a senha não pode ser vazia."));
    }

    @Test
    public void testRegisterUserWithPasswordOnlySpace() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", "    ", "12345");

        assertTrue(message.equals("Inválido, a senha não pode ser vazia."));
     }

    @Test
    public void testRegisterUserWithEmptyPassword() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", "", "12345");

        assertTrue(message.equals("Inválido, a senha não pode ser vazia."));
    }

    @Test
    public void testRegisterUserWithDifferenceBetwenPasswords() throws JSONException,
            UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("lasa", "a" +
                "aaa", "a@a.com", "123456", "000000");

        assertTrue(message.equals("As senhas digitadas sao diferentes"));
    }

    @Test
    public void testRegisterUserWithMaxLengthEmail() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("Cardoso", "Nere",
                "aaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaa" + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "@aaa.aaaa", "123456", "123456");

        assertTrue(message.equals("400"));
    }

    @Test
    public void testRegisterUserWithEmptyEmail() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("Nere", "Cardoso", "", "123456",
                "123456");

        assertTrue(message.equals("Inválido, o email não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithNullEmail() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("Nere", "Cardoso", null, "123456",
                "123456");

        assertTrue(message.equals("Inválido, o email não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithEmailOnlySpace() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("Nere", "Cardoso", "   ", "123456",
                "123456");

        assertTrue(message.equals("Inválido, o email não pode ser vazio."));
    }

    @Test
    public void testRegisterUserWithMaxMoreOneLenghtEmail() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("Cardoso", "Nere", "aaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaa.aaaa", "123456",
                "123456");

        assertTrue(message.equals("Inválido, o email deve ter no máximo 150 caractéres"));
    }

    @Test
    public void testRegisterUserWithEmailPattern() throws JSONException, UserException {
        RegisterUserController registerUserController = RegisterUserController.getInstance(context);

        String message = registerUserController.registerUser("Nere", "Cardoso", "aaaaa", "123456",
                "123456");

        assertTrue(message.equals("Ops, esse e-mail é inválido."));
    }
}
