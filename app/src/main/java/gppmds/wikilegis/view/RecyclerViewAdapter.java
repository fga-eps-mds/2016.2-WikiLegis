package gppmds.wikilegis.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.model.Bill;

/**
 * Created by shammyz on 9/11/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BillViewHolder> {

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView billTitle;
        TextView billDescription;
        ImageView themePhoto;

        BillViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            billTitle = (TextView)itemView.findViewById(R.id.bill_title);
            billDescription = (TextView)itemView.findViewById(R.id.bill_description);
            themePhoto = (ImageView)itemView.findViewById(R.id.theme_photo);
        }
    }
    List<Bill> bills;

    RecyclerViewAdapter(List<Bill> bills){
        this.bills = bills;
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        BillViewHolder billViewHolder = new BillViewHolder(v);
        return billViewHolder;
    }

    @Override
    public void onBindViewHolder(BillViewHolder personViewHolder, int i) {
        personViewHolder.billTitle.setText(bills.get(i).getTitle());
        personViewHolder.billDescription.setText(bills.get(i).getDescription());
       // personViewHolder.themePhoto.setImageResource(bills.get(i).getTheme());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
