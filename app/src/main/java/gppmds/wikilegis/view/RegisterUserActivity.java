package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.RegisterUserController;
import gppmds.wikilegis.dao.JSONHelper;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.exception.SegmentTypesException;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentTypes;

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_user);

        RegisterUserFragment registerUser = new RegisterUserFragment();
        RegisterUserController controller = RegisterUserController.getInstance(getApplicationContext());

        //O que que isso tá fazendo aqui mais uma requisição de?
        //controller.getUrlApi("http://wikilegis.labhackercd.net/api/bills/");
        FilteringFragment filteringFragment = new FilteringFragment();

        LoginFragment loginFragment = new LoginFragment();

        openFragment(filteringFragment);

    }
    private void openFragment(Fragment fragmentToBeOpen){

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content_panel, fragmentToBeOpen);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
