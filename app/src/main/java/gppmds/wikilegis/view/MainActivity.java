package gppmds.wikilegis.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.BillComparatorDate;
import gppmds.wikilegis.controller.BillComparatorProposals;
import gppmds.wikilegis.controller.BillController;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    public static List<Segment> listSegment;
    public static BillController billController;
    public static SegmentController segmentController;
    public static  List<Bill> billListInitial;
    public static  List<Bill> billListRelevantsAndClosed;
    public static  List<Bill> billListRelevantsAndOpened;
    public static  List<Bill> billListRecentsAndClosed;
    public static  List<Bill> billListRecentsAndOpened;
    private RecyclerViewAdapter adapter;

    private Switch switchRelevantes;
    private Switch switchAbertos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);


        switchRelevantes = (Switch)findViewById(R.id.switchRelevanteRecente);
        switchAbertos = (Switch)findViewById(R.id.switchAbertoFechado);

        switchAbertos.setOnCheckedChangeListener(this);
        switchRelevantes.setOnCheckedChangeListener(this);

        RecyclerView recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recycler_view.setLayoutManager(linearLayoutManager);

        segmentController = SegmentController.getInstance(getBaseContext());
        billController = new BillController(this);

        listSegment = segmentController.getAllSegments();

        billListInitial= billController.getAllBills();

        initListsInOrdenation();

        billListInitial= filteringForNumberOfProposals(billListInitial);
        billListInitial= filterigForStatusPublished(billListInitial);

        adapter = new RecyclerViewAdapter(billListInitial);
        recycler_view.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_deslogged, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
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

    public List<Bill> filterigForStatusClosed(List<Bill> list){
        List<Bill> billListWithStatusClosed= new ArrayList<Bill>();


        for(int index = 0 ; index<list.size();index++){
            if(list.get(index).getStatus().equals("closed")){
                billListWithStatusClosed.add(list.get(index));
            }
        }
        return billListWithStatusClosed;
    }

    public List<Bill> filterigForStatusPublished(List<Bill> list){
        List<Bill> billListWithStatusPublished= new ArrayList<Bill>();


        for(int index = 0 ; index<list.size();index++){
            if(list.get(index).getStatus().equals("published")){
                billListWithStatusPublished.add(list.get(index));
            }
        }
        return billListWithStatusPublished;
    }

    public static List<Bill> filteringForNumberOfProposals(List<Bill> billList) {
        List<Bill> billListAux = new ArrayList<>();

        BillComparatorProposals billComparatorDProposals = new BillComparatorProposals();
        Collections.sort(billList, billComparatorDProposals);

        for(int i=0; i<billList.size(); i++) {
            billListAux.add(billList.get(i));
        }

        return billListAux;

    }

    public List<Bill> filteringForDate(List<Bill> billList){
        List<Bill> billListAux = new ArrayList<>();

        BillComparatorDate comparator = new BillComparatorDate();
        Collections.sort(billList,comparator);

        for(int i=0; i<billList.size(); i++) {
            billListAux.add(billList.get(i));
        }

        return billListAux;
    }

    public void initListsInOrdenation() {
        billListRecentsAndClosed = new ArrayList<>();
        billListRecentsAndOpened = new ArrayList<>();
        billListRelevantsAndClosed = new ArrayList<>();
        billListRelevantsAndOpened = new ArrayList<>();

        billListRelevantsAndClosed = filteringForNumberOfProposals(billListInitial);
        billListRelevantsAndClosed = filterigForStatusClosed(billListRelevantsAndClosed);

        billListRelevantsAndOpened = filteringForNumberOfProposals(billListInitial);
        billListRelevantsAndOpened = filterigForStatusPublished(billListRelevantsAndOpened);

        billListRecentsAndOpened = filteringForDate(billListInitial);
        billListRecentsAndOpened = filterigForStatusPublished(billListRecentsAndOpened);

        billListRecentsAndClosed = filteringForDate(billListInitial);
        billListRecentsAndClosed = filterigForStatusClosed(billListRecentsAndClosed);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == switchAbertos.getId() && switchAbertos.isChecked()) {
            if (switchRelevantes.isChecked()) { //recentes e encerrados
                adapter.getData().clear();
                adapter.getData().addAll(billListRecentsAndClosed);
                adapter.notifyDataSetChanged();
            } else { //relevantes e encerrados
                adapter.getData().clear();
                adapter.getData().addAll(billListRelevantsAndClosed);
                adapter.notifyDataSetChanged();
            }
        } else if(buttonView.getId() == switchAbertos.getId() && !switchAbertos.isChecked()){
            if (switchRelevantes.isChecked()) { //recentes e abertos
                adapter.getData().clear();
                adapter.getData().addAll(billListRecentsAndOpened);
                adapter.notifyDataSetChanged();
            } else { //relevantes e encerrados
                adapter.getData().clear();
                adapter.getData().addAll(billListRelevantsAndOpened);
                adapter.notifyDataSetChanged();
            }
        }
    }
}