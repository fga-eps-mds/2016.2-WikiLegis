package gppmds.wikilegis.controller;

import android.content.Context;

/**
 * Created by marcelo on 10/6/16.
 */

public class LoginController {

    private static LoginController instance = null;
    private final Context context;

    private LoginController(final Context contextParameter) {
        this.context = contextParameter;
    }

    public static LoginController getInstance(final Context context) {
        if (instance == null) {
            instance = new LoginController(context);
        } else {
			/* ! Nothing To Do. */
        }
        return instance;
    }

    public String confirmLogin(final String email, final String password) {

        /*
            Ou vai fazer um get dos emails e senha e comparar aqui mesmo.
                Ou só vai nos retornar ok ou não ok
        */
        //Requisição retornar que existe o email e senha
        if(false) {
            return "SUCESS";
        }
        //Requisição retornar que os dados não condizem
        else {
            return "FAIL";
        }
    }

}
