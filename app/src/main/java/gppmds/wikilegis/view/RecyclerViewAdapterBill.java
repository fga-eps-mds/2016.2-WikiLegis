package gppmds.wikilegis.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    Bundle bundle = new Bundle();
                    bundle.putInt("segmentId", Integer.parseInt(view.getTag(R.id.idSegment).toString()));
                    bundle.putInt("billId", Integer.parseInt(view.getTag(R.id.idBill).toString()));

                    ViewSegmentFragment viewSegmentFragment = new ViewSegmentFragment();
                    viewSegmentFragment.setArguments(bundle);

                    activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_content,
                            viewSegmentFragment).commit();
                }
            });
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
        personViewHolder.cardView.setTag(R.id.idSegment, segments.get(i).getId());
        personViewHolder.cardView.setTag(R.id.idBill, segments.get(i).getBill());
        personViewHolder.segment.setText(segmentsString);

        settingClickableOfSegment(personViewHolder, i);
    }

    public void settingClickableOfSegment(final BillViewHolder personViewHolder, final int i) {
        //Types of segments that can NOT be clickable
        final int TITULO = 2;
        final int ITEM = 6;
        final int CAPITULO = 7;
        final int LIVRO = 8;
        final int SECAO = 9;
        final int SUBSECAO = 10;

        if (segments.get(i).getType() >= ITEM && segments.get(i).getType() <= SUBSECAO
                || segments.get(i).getType() == TITULO) {
            personViewHolder.cardView.setClickable(false);
        }
        else {
            personViewHolder.cardView.setClickable(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

