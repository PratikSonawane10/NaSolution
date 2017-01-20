package com.nasolution.com.nasolution;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nasolution.com.nasolution.Adapter.HomeListAdapter;
import com.nasolution.com.nasolution.InternetConnectivity.NetworkChangeReceiver;
import com.nasolution.com.nasolution.Model.HomeList;

import java.util.ArrayList;
import java.util.List;


public class Home extends BaseActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    RecyclerView.Adapter reviewAdapter;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog storeListDialogBox;
    public ArrayList<HomeList> homeListArray;
    public CoordinatorLayout homeListCoordinatorLayout;
    HomeListAdapter homeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.home);

        final String[] titlename = new String[]{"My Task","Project","Company","Architect","Task"};
        final int[] background = {R.drawable.home_menu_bg,R.drawable.home_menu_bg,R.drawable.companybg,R.drawable.home_menu_bg,R.drawable.home_menu_bg};


        homeListArray = new ArrayList<HomeList>();
        for (int i = 0; i < titlename.length; i++) {
            HomeList homeList = new HomeList();
            homeList.setTittle(titlename[i]);
            homeList.setThumbnail(background[i]);
            homeListArray.add(homeList);
        }

        recyclerView = (RecyclerView) findViewById(R.id.homeListRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        homeListAdapter = new HomeListAdapter(homeListArray);
        recyclerView.setAdapter(homeListAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = Home.this.getPackageManager();
        ComponentName component = new ComponentName(Home.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Home.this.getPackageManager();
        ComponentName component = new ComponentName(Home.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}