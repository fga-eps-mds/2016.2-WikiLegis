package gppmds.wikilegis.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BillViewHolder> {


    public static  List<Bill> bills;
    private Integer imageThemeId;
    public static class BillViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private CardView cardView;
        private TextView billTitle;
        private TextView billDescription;
        private TextView billNumberProposal;
        private ImageView themePhoto;

        BillViewHolder(final View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            billTitle = (TextView) itemView.findViewById(R.id.bill_title);
            billDescription = (TextView) itemView.findViewById(R.id.bill_description);
            billNumberProposal = (TextView) itemView.findViewById(R.id.bill_number_proposal);
            themePhoto = (ImageView) itemView.findViewById(R.id.theme_photo);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            AppCompatActivity activity = (AppCompatActivity) itemView.getContext();

            Bundle bundle = new Bundle();
            bundle.putInt("id", Integer.parseInt(view.getTag().toString()));

            ViewBillFragment viewBillFragment = new ViewBillFragment();
            viewBillFragment.setArguments(bundle);

            activity.getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.main_content, viewBillFragment).commit();
        }
    }
    RecyclerViewAdapter(final List<Bill> bills) {
        this.bills = bills;
   }

    private void setImageThemeId(final Bill bill) {
        switch (bill.getTheme()) {
            case "meio-ambiente": imageThemeId = R.drawable.meio_ambiente;
                break;
            case "direito-e-justica": imageThemeId = R.drawable.direito_e_justica;
                break;
            case "trabalho": imageThemeId = R.drawable.trabalho;
                break;
            case "direitos-humanos": imageThemeId = R.drawable.direitos_humanos;
                break;
            case "economia": imageThemeId = R.drawable.economia;
                break;
            case "consumidor": imageThemeId = R.drawable.consumidor;
                break;
            default: imageThemeId = 0;
        }
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    @Override
    public BillViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_bill, viewGroup, false);
        BillViewHolder billViewHolder = new BillViewHolder(v);
        v.setTag(i);
        return billViewHolder;
    }

    @Override
    public void onBindViewHolder(final BillViewHolder personViewHolder, final int i) {
        personViewHolder.billTitle.setText(bills.get(i).getTitle());
        personViewHolder.billDescription.setText(bills.get(i).getDescription());
        personViewHolder.billNumberProposal.setText(Integer.toString(bills.get(i).
                getNumberOfPrposals()));
        setImageThemeId(bills.get(i));
        personViewHolder.themePhoto.setImageResource(imageThemeId);
        personViewHolder.cardView.setTag(bills.get(i).getId());

    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public List<Bill> getData() {
        return bills;
    }
}
