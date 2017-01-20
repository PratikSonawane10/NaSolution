package com.nasolution.com.nasolution.Adapter;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nasolution.com.nasolution.AddCompany;
import com.nasolution.com.nasolution.AddProject;
import com.nasolution.com.nasolution.AddTask;
import com.nasolution.com.nasolution.Architecture;
import com.nasolution.com.nasolution.Model.HomeList;
import com.nasolution.com.nasolution.MyTask;
import com.nasolution.com.nasolution.R;

import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {

    private static ArrayList<HomeList> dataSet;
    public static CoordinatorLayout homeListCoordinatorLayout;


    public HomeListAdapter(ArrayList<HomeList> homeListArray) {
        dataSet = homeListArray;
       // this.homeListCoordinatorLayout = homeListCoordinatorLayout;
    }

    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_menu_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(HomeListAdapter.ViewHolder viewHolder, int i) {
        HomeList homeList = dataSet.get(i);
        viewHolder.cv.setTag(i);
        viewHolder.tittle.setText(homeList.getTittle());
        viewHolder.background.setBackgroundResource(homeList.getThumbnail());
        viewHolder.home = homeList;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView tittle;
        public TextView description;
        public RelativeLayout background;
        public HomeList home;
        private AlertDialog alertDialog;
        public CardView cv;

        public int state = 0;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view_home);
            tittle = (TextView) itemView.findViewById(R.id.titletxt);
            background = (RelativeLayout) itemView.findViewById(R.id.relativelayouthome);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            int position = (int) v.getTag();

            if (position == 1) {
                Intent gotolistofproject = new Intent(v.getContext(), AddProject.class);
                v.getContext().startActivity(gotolistofproject);
            }
            else if (position == 2) {
                Intent gotoShopProduct = new Intent(v.getContext(), AddCompany.class);
                v.getContext().startActivity(gotoShopProduct);
            }
            else if (position == 3) {
                Intent gotoCampaign = new Intent(v.getContext(), Architecture.class);
                v.getContext().startActivity(gotoCampaign);
            }
            else if (position == 4) {
                Intent gotoPetServices = new Intent(v.getContext(), AddTask.class);
                v.getContext().startActivity(gotoPetServices);
            } else if (position == 0) {
                Intent gotoPetServices = new Intent(v.getContext(), MyTask.class);
                v.getContext().startActivity(gotoPetServices);
            }
        }
    }
}

