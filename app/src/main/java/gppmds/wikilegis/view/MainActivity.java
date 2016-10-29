package gppmds.wikilegis.view;

import android.support.design.widget.FloatingActionButton;

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
import android.view.View;
import android.widget.EditText;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.LoginController;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs = null;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        settingView();

    }

    private void settingView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = (FloatingActionButton)findViewById
                (R.id.floatingButton);
        floatingActionButton.setVisibility(View.INVISIBLE);

        // Create the adapter that will return a fragment for each of the two tabs
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(tabsAdapter);

        tabs = (TabLayout) this.findViewById(R.id.tabs);
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
        showDialogNetworkSettings(MainActivity.this, "Download de dados", new String[]{"Confirmar"},
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        //Do your functionality here
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                        SharedPreferences session = PreferenceManager.
                                getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = session.edit();

                        switch(selectedPosition){

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

    @Override
    public void onBackPressed(){
        final CreateSuggestProposal createSuggestProposal = (CreateSuggestProposal)
                getSupportFragmentManager().findFragmentByTag("SUGGEST_PROPOSAL");

        if(createSuggestProposal != null){
            if(createSuggestProposal.isVisible()){

                EditText proposalSuggestionEditText = (EditText) createSuggestProposal.getView()
                        .findViewById(R.id.suggestionProposalEditText);
                String suggestionTyped = proposalSuggestionEditText.getText().toString();

                if(!suggestionTyped.isEmpty()){

                    final AlertDialog.Builder alertDialogProposalBuilder = new AlertDialog.Builder
                            (createSuggestProposal.getContext());

                    alertDialogProposalBuilder.setMessage("Você tem certeza que deseja descartar sua " +
                            "sugestão?");

                    alertDialogProposalBuilder.setPositiveButton("Sim", new DialogInterface
                            .OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                            getSupportFragmentManager().beginTransaction().remove(createSuggestProposal)
                                    .commit();
                            floatingActionButton.setVisibility(View.VISIBLE);
                        }
                    });

                    alertDialogProposalBuilder.setNegativeButton("Não", new DialogInterface
                            .OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                        }
                    });

                    alertDialogProposalBuilder.show();
                }
                else{
                    super.onBackPressed();
                    floatingActionButton.setVisibility(View.VISIBLE);

                }
            }
        }
        else{
            super.onBackPressed();
        }
    }
}
