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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.DataDownloadController;
import gppmds.wikilegis.controller.LoginController;

import gppmds.wikilegis.exception.BillException;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BillController billController = BillController.getInstance(getApplicationContext());
        DataDownloadController dataCenter = DataDownloadController.getInstance(getBaseContext());

        if(dataCenter.connectionType() < 2) {
            try {
                DataDownloadController.getAllBills();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (BillException e) {
                e.printStackTrace();
            }
        } else {
            try {
                billController.DownloadBills();
            } catch (BillException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (SegmentException e) {
                e.printStackTrace();
            }
        }
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
                actionDialogNetworkSettings();
                break;
            case R.id.action_config_logged:
                actionDialogNetworkSettings();
                break;
        }
        return true;
    }

    private void actionDialogNetworkSettings() {
        showDialogNetworkSettings(MainActivity.this, "Download de dados", new String[] { "Confirmar" },
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do your functionality here
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();

                        SharedPreferences session = PreferenceManager.
                                getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = session.edit();

                        switch (selectedPosition) {

                            case 0:
                                editor.putInt(MainActivity.this.getResources()
                                        .getString(R.string.network_settings), 0);
                                break;
                            case 1:
                                editor.putInt(MainActivity.this.getResources()
                                        .getString(R.string.network_settings), 1);
                                break;
                            case 2:
                                editor.putInt(MainActivity.this.getResources()
                                        .getString(R.string.network_settings), 2);
                                break;
                            default:
                                //Nothing to do
                        }
                        editor.commit();
                    }
                });
    }

    public void showDialogNetworkSettings(Context context, String title, String[] btnText,
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
        SharedPreferences session = PreferenceManager.
                getDefaultSharedPreferences(MainActivity.this);
        int networkPreference = session.getInt(MainActivity.this.getResources()
                .getString(R.string.network_settings), 0);

        Log.d("networkPrefe", networkPreference+"");

        builder.setSingleChoiceItems(items, networkPreference,
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
