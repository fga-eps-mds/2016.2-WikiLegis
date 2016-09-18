package gppmds.wikilegis.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gppmds.wikilegis.R;
import gppmds.wikilegis.model.Bill;

/**
 * Created by shammyz on 9/11/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BillViewHolder>{


    public static  List<Bill> bills;
    private Integer imageThemeId;

    public static class BillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cardView;
        TextView billTitle;
        TextView billDescription;
        ImageView themePhoto;
//        List<Bill> bills;

        BillViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            billTitle = (TextView) itemView.findViewById(R.id.bill_title);
            billDescription = (TextView) itemView.findViewById(R.id.bill_description);
            themePhoto = (ImageView) itemView.findViewById(R.id.theme_photo);
            cardView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
            if(view==itemView) {
                ViewBill viewBill = new ViewBill();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                        viewBill).commit();

            }else{
                int idBill = bills.get(getAdapterPosition()).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id",idBill);
                ViewBill viewBill = new ViewBill();
                viewBill.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                        viewBill).commit();


            }
        }
    }
    RecyclerViewAdapter(List < Bill > bills) {
        this.bills = bills;
   }
    //public List<Bill> getBillOnViewHolder(){return bills;}

    private void setImageThemeId(Bill bill){
        switch (bill.getTheme()){
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
    public BillViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_bill, viewGroup, false);
        BillViewHolder billViewHolder = new BillViewHolder(v);
        v.setTag(i);
        return billViewHolder;
    }

    @Override
    public void onBindViewHolder(BillViewHolder personViewHolder, int i) {
        bills = MainActivity.filtringForNumberOfProposals(bills);
        personViewHolder.billTitle.setText(bills.get(i).getTitle());
        personViewHolder.billDescription.setText(bills.get(i).getDescription());
        setImageThemeId(bills.get(i));
        personViewHolder.themePhoto.setImageResource(imageThemeId);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public List<Bill> getData(){
        return bills;
    }
}
