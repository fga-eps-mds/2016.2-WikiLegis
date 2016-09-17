package gppmds.wikilegis.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.controller.SegmentsOfBillController;
import gppmds.wikilegis.dao.SegmentDAO;
import gppmds.wikilegis.exception.SegmentException;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;
import gppmds.wikilegis.model.SegmentsOfBill;

/**
 * Created by thiago on 9/1/16.
 */
public class RecyclerViewAdapterBill extends RecyclerView.Adapter<RecyclerViewAdapterBill.BillViewHolder> {

    SegmentController segmentController;
    private Context context;

    public RecyclerViewAdapterBill(Context context) {
        this.context = context;
    }


    public static class BillViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView segment;

        BillViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.frameCardViewSegment);
            segment = (TextView) itemView.findViewById(R.id.textViewSegment);
        }
    }

    public static List<Segment> segments = new ArrayList<Segment>();

    public static void initiSegm() {
        List<SegmentsOfBill> segmentsOfBillList;

        segmentsOfBillList = SegmentsOfBillController.getAllSegmentsOfBill(38);

        for(int i=0; i<segmentsOfBillList.size(); i++) {
            try {
                Segment segmentAux = SegmentController.getSegmentById(segmentsOfBillList.get(i).getIdSegment());
                segments.add(segmentAux);

            } catch (SegmentException e) {
                e.printStackTrace();
            }
        }
        for(int i=0; i<segments.size(); i++)
            Log.d("IdSegment:", String.valueOf(segments.get(i).getId()));

    }

    RecyclerViewAdapterBill(List<Segment> segments, Context context){
        segmentController = SegmentController.getInstance(context);
        this.segments = segments;
    }


    @Override
    public int getItemCount() {
        return segments.size();
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_bill, viewGroup, false);
        BillViewHolder billViewHolder = new BillViewHolder(v);
        return billViewHolder;
    }

    @Override
    public void onBindViewHolder(BillViewHolder personViewHolder, int i) {
        String segmentsString = segments.get(i).getContent();
        Log.d("Aqui", segmentsString);
        personViewHolder.segment.setText(segmentsString);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

