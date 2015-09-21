package com.unist.npc.queuing;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int Tab_icons[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Context context;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, int m_tabIcons[], int mNumbOfTabsumb, Context mContext) {
        super(fm);

        this.Tab_icons = m_tabIcons;
        this.NumbOfTabs = mNumbOfTabsumb;
        context = mContext;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:{
                Tab1_restaurants tab1Restaurants = new Tab1_restaurants();
                return tab1Restaurants;
            }
            case 1:{
                Tab2_reservation_info tab2Reservationinfo = new Tab2_reservation_info();
                return tab2Reservationinfo;
            }
            case 2:{
                Tab3_favorites tab3_favorites = new Tab3_favorites();
                return tab3_favorites;
            }
            default:{
                return null;
            }
        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
      //  Drawable img = ContextCompat.getDrawable(context,Tab_icons[position])
        Drawable image = context.getResources().getDrawable(Tab_icons[position]);
        image.setBounds(0, 0, 36, 36);
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
