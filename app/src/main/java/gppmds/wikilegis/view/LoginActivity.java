package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import gppmds.wikilegis.R;
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        LoginFragment loginFragment = new LoginFragment();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_user);

        openFragment(loginFragment);
    }

    private void openFragment(final Fragment fragmentToBeOpen) {

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content_panel, fragmentToBeOpen);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
