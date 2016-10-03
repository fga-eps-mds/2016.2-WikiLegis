package gppmds.wikilegis.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gppmds.wikilegis.R;

/**
 * Created by izabela on 02/10/16.
 */
public class TabsAdapter extends FragmentPagerAdapter {


    public TabsAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return Main2Activity.PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount(){
        // Show 3 total pages.
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
}
