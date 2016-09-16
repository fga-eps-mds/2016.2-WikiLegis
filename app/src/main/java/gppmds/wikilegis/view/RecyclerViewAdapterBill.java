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

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.controller.SegmentController;
import gppmds.wikilegis.dao.SegmentDAO;
import gppmds.wikilegis.model.Bill;
import gppmds.wikilegis.model.Segment;

/**
 * Created by thiago on 9/1/16.
 */
public class RecyclerViewAdapterBill extends RecyclerView.Adapter<RecyclerViewAdapterBill.BillViewHolder> {

    SegmentController segmentController;

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView segment;

        BillViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.frameCardViewSegment);
            segment = (TextView) itemView.findViewById(R.id.textViewSegment);
        }
    }
    private List<Segment> segments = segmentController.getAllSegments();

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
        personViewHolder.segment.setText(segmentsString);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

