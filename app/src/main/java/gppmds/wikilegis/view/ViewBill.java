package gppmds.wikilegis.view;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;
import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.model.Segment;


public class ViewBill extends Fragment {

    public static List<Segment> listSegment;
    public static SegmentController segmentController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int id;
        id=getArguments().getInt("id");

        View view = inflater.inflate(R.layout.fragment_view_bill, container, false);
        RecyclerView recycler_view = (RecyclerView) view.findViewById(R.id.recycler_viewBill);
        recycler_view.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recycler_view.setLayoutManager(linearLayoutManager);

        segmentController = SegmentController.getInstance(getContext());

        listSegment = segmentController.getAllSegments();

        RecyclerViewAdapterBill adapter = new RecyclerViewAdapterBill(listSegment, getContext());
        recycler_view.setAdapter(adapter);

        return view;
    }

}
