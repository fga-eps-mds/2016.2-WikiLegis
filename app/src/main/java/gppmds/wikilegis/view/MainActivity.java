package gppmds.wikilegis.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.LoginController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        settingView();

    }

    private void settingView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the two tabs
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(tabsAdapter);

        TabLayout tabs = (TabLayout) this.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(getApplicationContext());

        boolean isLogged = session.getBoolean("IsLoggedIn", false);

        if (isLogged) {
            getMenuInflater().inflate(R.menu.menu_logged, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_deslogged, menu);
        }

        return true;
    }

    public boolean onOptionsItemSelected(final MenuItem item) {

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        switch(item.getItemId()) {
            case R.id.action_login:
                startActivity(intent);
                break;
            case R.id.action_logout:
                startActivity(intent);

                SharedPreferences session = PreferenceManager.
                        getDefaultSharedPreferences(this);

                LoginController loginController =
                        LoginController.getInstance(this);
                loginController.createSessionIsNotLogged(session);
                break;
            case R.id.action_config_deslogged:
                actionDialogSettings();
                break;
            case R.id.action_config_logged:
                actionDialogSettings();
                break;
        }
        return true;
    }

    private void actionDialogSettings() {
        showDialogSettings(MainActivity.this, "Download de dados", new String[] { "Confirmar" },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do your functionality here
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                    }
                });
    }

    public void showDialogSettings(Context context, String title, String[] btnText,
                                   DialogInterface.OnClickListener listener) {

        final CharSequence[] items = { "Apenas wifi", "Wifi e dados", "Nunca" };

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                }
            };
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                    }
                });
        builder.setPositiveButton(btnText[0], listener);
        if (btnText.length != 1) {
            builder.setNegativeButton(btnText[1], listener);
        }
        builder.show();
    }

}
