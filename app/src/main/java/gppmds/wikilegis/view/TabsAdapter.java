package gppmds.wikilegis.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

import gppmds.wikilegis.R;

/**
 * Created by izabela on 02/10/16.
 */
public class TabsAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener{


    public TabsAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                return OpenBillsListFragment.newInstance();
            case 1:
                return ClosedBillsListFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount(){
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "Abertos";
            case 1:
                return "Fechados";
        }
        return null;
    }


    @Override
    public void onTabChanged(String tabId){
        if(tabId.equals("ABERTOS")){
            Log.d("a", "aberts");
        } else if(tabId.equals("FECHADOS")) {
            Log.d("b", "fecas");
        }
    }}
