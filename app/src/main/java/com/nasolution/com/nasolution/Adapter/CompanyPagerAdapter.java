package com.nasolution.com.nasolution.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nasolution.com.nasolution.AddCompany;
import com.nasolution.com.nasolution.DeleteCompany;
import com.nasolution.com.nasolution.UpdateCompany;

public class CompanyPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public CompanyPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AddCompany tab1 = new AddCompany();
                return tab1;
            case 1:
                UpdateCompany tab2 = new UpdateCompany();
                return tab2;
            case 2:
                DeleteCompany tab3 = new DeleteCompany();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}