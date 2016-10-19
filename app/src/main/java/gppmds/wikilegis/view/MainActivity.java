package gppmds.wikilegis.view;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;
import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.model.Bill;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabs = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        settingView();

    }

    private void settingView() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById
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
        getMenuInflater().inflate(R.menu.menu_deslogged, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        final String STATE_VISIBILITY = "state-visibility";

        savedInstanceState.putBoolean(STATE_VISIBILITY, true);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Boolean tabsVisibility = savedInstanceState.getBoolean("state-visibility");
        tabs.setVisibility(tabsVisibility ? View.VISIBLE : View.GONE);

    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        return true;
    }
}
