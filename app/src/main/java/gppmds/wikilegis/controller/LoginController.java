package gppmds.wikilegis.controller;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.dao.PostRequest;

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
        PostRequest postRequest = new PostRequest(context,"http://wikilegis-staging.labhackercd.net/accounts/api-token-auth/");


        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("username", email);
        builder.appendQueryParameter("password", password);
        String authentication = builder.build().getEncodedQuery();

        Log.v("TOKEN", authentication);
        String token = null;
        try {
            token = postRequest.execute(authentication,"application/x-www-form-urlencoded").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.v("Token", token);
        Log.v("Status",postRequest.getResponse()+"");

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
