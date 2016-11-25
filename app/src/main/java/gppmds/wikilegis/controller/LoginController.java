package gppmds.wikilegis.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.dao.api.PostRequest;
import gppmds.wikilegis.exception.UserException;
import gppmds.wikilegis.model.User;

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
        PostRequest postRequest = new PostRequest(context,
                "http://wikilegis-staging.labhackercd.net/accounts/api-token-auth/");

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("username", email);
        builder.appendQueryParameter("password", password);
        String authentication = builder.build().getEncodedQuery();



        try {
            Log.d("LoginController", "confirmLogin ");

            User userLogin = new User(email, password);

            authentication = createUserAuthentication(userLogin);

            String userInformation = userInformationRequest(postRequest, authentication);

            setUserAsSharedPreferences(userInformation);

            return returnOfLogin(postRequest);

        } catch (UserException exception) {
            String exceptionMessage = exception.getMessage();
            return exceptionMessage;
        }
    }

    private String userInformationRequest(PostRequest postRequest, String authentication) {
        String userInformation = null;

        Log.d("LoginController", "userInformationRequest ");

        try {
            userInformation = postRequest.execute(authentication,
                    "application/x-www-form-urlencoded").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return userInformation;
    }

    private String createUserAuthentication(User userLogin) {
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("username", userLogin.getEmail());
        builder.appendQueryParameter("password", userLogin.getPassword());

        Log.d("LoginController", "createUserAuthentication ");

        return builder.build().getEncodedQuery();
    }

    private String returnOfLogin(PostRequest postRequest) {
        //Reponse is 200 if authentication is correct.
        if(postRequest.getResponse() == 200) {
            return "SUCESS";
        }
        //Reponse is 400 if authentication is incorrect.
        else {
            return "FAIL";
        }
    }

    private void setUserAsSharedPreferences(String userInformation) {

        JSONObject userJson = null;
        String token = null;
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(context);

        Log.d("LoginController", "setUserAsSharedPreferences ");

        try {
            if (userInformation != null) {
                userJson = new JSONObject(userInformation);
                token = userJson.getString("token");
                JSONObject user = userJson.getJSONObject("user");
                parserUserInformation(user, token, session);
            } else {
                Log.i("Info", "UserInformation is not valid");

                createSessionIsNotLogged(session);
            }

        } catch (JSONException e) {
            Log.e("Error", "JSONException");
            e.printStackTrace();
        }
    }

    private void parserUserInformation(JSONObject user, String token, SharedPreferences session)
            throws JSONException {
        Integer userId = user.getInt("id");
        String firstName = user.getString("first_name");
        String lastName = user.getString("last_name");
        String email = user.getString("email");

        createLoginSession(userId, email, token, firstName, lastName, session);
    }

    public void createLoginSession(final Integer idUser,
                                   final String email,
                                   final String token,
                                   final String firstName,
                                   final String lastName,
                                   SharedPreferences session) {

        SharedPreferences.Editor editor = session.edit();

        editor.putBoolean(context.getResources().getString(R.string.is_logged_in), true);
        editor.putInt(context.getResources().getString(R.string.id), idUser);
        editor.putString(context.getResources().getString(R.string.email), email);
        editor.putString(context.getResources().getString(R.string.token), token);
        editor.putString(context.getResources().getString(R.string.first_name), firstName);
        editor.putString(context.getResources().getString(R.string.last_name), lastName);
        editor.commit();
    }

    public void createSessionIsNotLogged(SharedPreferences session) {
        SharedPreferences.Editor editor = session.edit();

        editor.putBoolean(context.getResources().getString(R.string.is_logged_in), false);
        editor.putString(context.getResources().getString(R.string.token), null);
        editor.commit();
    }
}
