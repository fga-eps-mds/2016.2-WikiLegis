package gppmds.wikilegis.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;
import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.FilteringController;
import gppmds.wikilegis.model.Bill;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private BillController billController;
    private FilteringController filteringController;

    private  List<Bill> billListInitial;
    private  List<Bill> billListRelevantsAndClosed;
    private  List<Bill> billListRelevantsAndOpened;
    private  List<Bill> billListRecentsAndClosed;
    private  List<Bill> billListRecentsAndOpened;

    private RecyclerViewAdapter adapter;
    private Switch switchRelevantsOrRecents;
    private Switch switchOpenOrClosed;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);

        settingView();
        settingListenersOnView();
        initBillList();
        initRecycleView();
        initListsInOrder();
    }

    private void initBillList() {
        billController = new BillController(this);
        billListInitial = billController.getAllBills();
        filteringController = FilteringController.getInstance(getBaseContext());
        billListInitial = filteringController.filteringForNumberOfProposals(billListInitial);
        billListInitial = filteringController.filterigForStatusPublished(billListInitial);
    }

    private void initRecycleView() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(billListInitial);
        recyclerView.setAdapter(adapter);
    }

    private void settingListenersOnView() {
        switchOpenOrClosed.setOnCheckedChangeListener(this);
        switchRelevantsOrRecents.setOnCheckedChangeListener(this);
    }

    private void settingView() {
        toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        switchRelevantsOrRecents = (Switch) findViewById(R.id.switchRelevanteRecente);
        switchOpenOrClosed = (Switch) findViewById(R.id.switchAbertoFechado);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deslogged, menu);

        return true;
    }

    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // User chose the "Search" card_bill, show field of search...
                return true;

            case R.id.action_profile:
                // User choose first icon.
                return true;

            case R.id.action_exit_account:
                //Deslog user
                return true;

            case R.id.action_filtering:
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void initListsInOrder() {
        billListRecentsAndClosed = new ArrayList<>();
        billListRecentsAndOpened = new ArrayList<>();
        billListRelevantsAndClosed = new ArrayList<>();
        billListRelevantsAndOpened = new ArrayList<>();

        billListInitial = billController.getAllBills();

        billListRelevantsAndClosed = filteringController.filteringForNumberOfProposals(billListInitial);
        billListRelevantsAndClosed = filteringController.filterigForStatusClosed(billListRelevantsAndClosed);

        billListRelevantsAndOpened = filteringController.filteringForNumberOfProposals(billListInitial);
        billListRelevantsAndOpened = filteringController.filterigForStatusPublished(billListRelevantsAndOpened);

        billListRecentsAndOpened = filteringController.filteringForDate(billListInitial);
        billListRecentsAndOpened = filteringController.filterigForStatusPublished(billListRecentsAndOpened);

        billListRecentsAndClosed = filteringController.filteringForDate(billListInitial);
        billListRecentsAndClosed = filteringController.filterigForStatusClosed(billListRecentsAndClosed);

    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        if (buttonView.getId() == switchOpenOrClosed.getId() && switchOpenOrClosed.isChecked()) {
            if (switchRelevantsOrRecents.isChecked()) {
                adapter.getData().clear();
                adapter.getData().addAll(billListRecentsAndClosed);
                adapter.notifyDataSetChanged();
            } else {
                adapter.getData().clear();
                adapter.getData().addAll(billListRelevantsAndClosed);
                adapter.notifyDataSetChanged();
            }
        } else if (buttonView.getId() == switchOpenOrClosed.getId()
                && !switchOpenOrClosed.isChecked()) {
            if (switchRelevantsOrRecents.isChecked()) {
                adapter.getData().clear();
                adapter.getData().addAll(billListRecentsAndOpened);
                adapter.notifyDataSetChanged();
            } else {
                adapter.getData().clear();
                adapter.getData().addAll(billListRelevantsAndOpened);
                adapter.notifyDataSetChanged();
            }
        } else if (buttonView.getId() == switchRelevantsOrRecents.getId()
                && switchRelevantsOrRecents.isChecked()) {
            if (switchOpenOrClosed.isChecked()) {
                adapter.getData().clear();
                adapter.getData().addAll(billListRecentsAndClosed);
                adapter.notifyDataSetChanged();
            } else {
                adapter.getData().clear();
                adapter.getData().addAll(billListRecentsAndOpened);
                adapter.notifyDataSetChanged();
            }
        } else if (buttonView.getId() == switchRelevantsOrRecents.getId()
                && !switchRelevantsOrRecents.isChecked()) {
            if (switchOpenOrClosed.isChecked()) {
                adapter.getData().clear();
                adapter.getData().addAll(billListRelevantsAndClosed);
                adapter.notifyDataSetChanged();
            } else {
                adapter.getData().clear();
                adapter.getData().addAll(billListRelevantsAndOpened);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
