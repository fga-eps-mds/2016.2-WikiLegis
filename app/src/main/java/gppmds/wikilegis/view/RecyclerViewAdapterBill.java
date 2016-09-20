package gppmds.wikilegis.view;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.model.Segment;

public class RecyclerViewAdapterBill extends RecyclerView.Adapter<RecyclerViewAdapterBill.BillViewHolder> {

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView segment;

        BillViewHolder(final View itemView) {

            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.frameCardViewSegment);
            segment = (TextView) itemView.findViewById(R.id.textViewSegment);
        }
    }

    private static List<Segment> segments = new ArrayList<Segment>();

    RecyclerViewAdapterBill(final List<Segment> segments, final Context context) {
        this.segments = segments;
    }

    @Override
    public int getItemCount() {
        return segments.size();
    }

    @Override
    public BillViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_bill,
                viewGroup, false);
        BillViewHolder billViewHolder = new BillViewHolder(view);
        return billViewHolder;
    }

    @Override
    public void onBindViewHolder(final BillViewHolder personViewHolder, final int i) {
        String segmentsString = segments.get(i).getContent();
        personViewHolder.segment.setText(segmentsString);
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

